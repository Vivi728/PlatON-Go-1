package core

import (
	"bytes"
	"crypto/ecdsa"
	"encoding/hex"
	"fmt"
	"sync"

	"github.com/PlatONnetwork/PlatON-Go/common"
	cvm "github.com/PlatONnetwork/PlatON-Go/common/vm"
	"github.com/PlatONnetwork/PlatON-Go/core/cbfttypes"
	"github.com/PlatONnetwork/PlatON-Go/core/snapshotdb"
	"github.com/PlatONnetwork/PlatON-Go/core/vm"
	"github.com/PlatONnetwork/PlatON-Go/crypto"
	"github.com/PlatONnetwork/PlatON-Go/p2p/discover"
	"github.com/PlatONnetwork/PlatON-Go/x/staking"
	"github.com/PlatONnetwork/PlatON-Go/x/xutil"

	"github.com/PlatONnetwork/PlatON-Go/core/types"
	"github.com/PlatONnetwork/PlatON-Go/event"
	"github.com/PlatONnetwork/PlatON-Go/log"
	"github.com/PlatONnetwork/PlatON-Go/x/plugin"
	"github.com/PlatONnetwork/PlatON-Go/x/xcom"
)

type BlockChainReactor struct {
	vh            *xcom.VrfHandler
	eventMux      *event.TypeMux
	bftResultSub  *event.TypeMuxSubscription
	basePluginMap map[int]plugin.BasePlugin // xxPlugin container
	beginRule     []int                     // Order rules for xxPlugins called in BeginBlocker
	endRule       []int                     // Order rules for xxPlugins called in EndBlocker
	validatorMode string                    // mode: static, inner, ppos
	exitCh        chan struct{}             // Used to receive an exit signal
	exitOnce      sync.Once
}

var (
	bcrOnce sync.Once
	bcr     *BlockChainReactor
)

func NewBlockChainReactor(pri *ecdsa.PrivateKey, mux *event.TypeMux) *BlockChainReactor {
	bcrOnce.Do(func() {
		log.Info("Init BlockChainReactor ...")
		bcr = &BlockChainReactor{
			eventMux:      mux,
			basePluginMap: make(map[int]plugin.BasePlugin, 0),
			exitCh:        make(chan struct{}),
		}
	})
	return bcr
}

func (bcr *BlockChainReactor) Start(mode string) {
	bcr.setValidatorMode(mode)
	if bcr.validatorMode == common.PPOS_VALIDATOR_MODE {
		// Subscribe events for confirmed blocks
		bcr.bftResultSub = bcr.eventMux.Subscribe(cbfttypes.CbftResult{})

		// start the loop rutine
		go bcr.loop()
	}
}

func (bcr *BlockChainReactor) Close() {
	bcr.exitOnce.Do(func() {
		close(bcr.exitCh)
		log.Info("blockchain_reactor is closed")
	})
}

// Getting the global bcr single instance
func GetReactorInstance() *BlockChainReactor {
	return bcr
}

func (bcr *BlockChainReactor) loop() {

	for {
		select {
		case obj := <-bcr.bftResultSub.Chan():
			if obj == nil {
				log.Error("blockchain_reactor receive nil bftResultEvent maybe channel is closed")
				continue
			}
			cbftResult, ok := obj.Data.(cbfttypes.CbftResult)
			if !ok {
				log.Error("blockchain_reactor receive bft result type error")
				continue
			}
			block := cbftResult.Block
			// Short circuit when receiving empty result.
			if block == nil {
				log.Error("blockchain_reactor receive Cbft result error, block is nil")
				continue
			}

			/**
			notify P2P module the nodeId of the next round validator
			*/
			if plugin, ok := bcr.basePluginMap[xcom.StakingRule]; ok {
				if err := plugin.Confirmed(block); nil != err {
					log.Error("Failed to call Staking Confirmed", "blockNumber", block.Number(), "blockHash", block.Hash().Hex(), "err", err.Error())
				}

			}

			// Slashing
			if plugin, ok := bcr.basePluginMap[xcom.SlashingRule]; ok {
				if err := plugin.Confirmed(block); nil != err {
					log.Error("Failed to call Slashing Confirmed", "blockNumber", block.Number(), "blockHash", block.Hash().Hex(), "err", err.Error())
				}
			}

			log.Debug("Call snapshotdb commit on blockchain_reactor", "blockNumber", block.Number(), "blockHash", block.Hash())
			if err := snapshotdb.Instance().Commit(block.Hash()); nil != err {
				log.Error("Failed to call snapshotdb commit on blockchain_reactor", "blockNumber", block.Number(), "blockHash", block.Hash(), "err", err)
				continue
			}
		// stop this routine
		case <-bcr.exitCh:
			return
		}
	}

}

func (bcr *BlockChainReactor) RegisterPlugin(pluginRule int, plugin plugin.BasePlugin) {
	bcr.basePluginMap[pluginRule] = plugin
}

func (bcr *BlockChainReactor) SetPluginEventMux() {
	plugin.StakingInstance().SetEventMux(bcr.eventMux)
}

func (bcr *BlockChainReactor) setValidatorMode(mode string) {
	bcr.validatorMode = mode
}

func (bcr *BlockChainReactor) SetVRF_handler(vher *xcom.VrfHandler) {
	bcr.vh = vher
}

func (bcr *BlockChainReactor) SetPrivateKey(privateKey *ecdsa.PrivateKey) {
	//if bcr.validatorMode == common.PPOS_VALIDATOR_MODE {
	if nil != bcr.vh {
		bcr.vh.SetPrivateKey(privateKey)
	}
}

func (bcr *BlockChainReactor) SetBeginRule(rule []int) {
	bcr.beginRule = rule
}
func (bcr *BlockChainReactor) SetEndRule(rule []int) {
	bcr.endRule = rule
}

func (bcr *BlockChainReactor) SetWorkerCoinBase(header *types.Header, nodeId discover.NodeID /*privateKey *ecdsa.PrivateKey*/) {

	/**
	this things about ppos
	*/
	if bcr.validatorMode != common.PPOS_VALIDATOR_MODE {
		return
	}

	//nodeId := discover.PubkeyID(&privateKey.PublicKey)
	addr, _ := xutil.NodeId2Addr(nodeId)

	log.Info("Call SetWorkerCoinBase on blockchain_reactor", "blockNumber", header.Number,
		"nodeId", nodeId.String(), "addr", addr.Hex())

	if plu, ok := bcr.basePluginMap[xcom.StakingRule]; ok {
		stake := plu.(*plugin.StakingPlugin)
		can, err := stake.GetCandidateInfo(common.ZeroHash, addr)
		if nil != err {
			log.Error("Failed to SetWorkerCoinBase: Query candidate info is failed", "err", err)
			return
		}
		header.Coinbase = can.BenefitAddress
		log.Info("SetWorkerCoinBase Successfully", "coinbase", header.Coinbase.Hex())
	}

}

// Called before every block has not executed all txs
func (bcr *BlockChainReactor) BeginBlocker(header *types.Header, state xcom.StateDB) error {

	// todo test
	root := state.IntermediateRoot(true)
	log.Debug("BeginBlock StateDB root", "blockHash", header.Hash().Hex(), "blockNumber",
		header.Number.Uint64(), "root", root.Hex(), "pointer", fmt.Sprintf("%p", state))

	// todo test
	pposHash := state.GetState(cvm.StakingContractAddr, staking.GetPPOSHASHKey())
	log.Info("Query ppos hash", "blockHash", header.Hash().Hex(), "blockNumber",
		header.Number.Uint64(), "pposHash", hex.EncodeToString(pposHash))

	/**
	this things about ppos
	*/
	if bcr.validatorMode != common.PPOS_VALIDATOR_MODE {
		return nil
	}

	blockHash := common.ZeroHash

	// store the sign in  header.Extra[32:97]
	if xutil.IsWorker(header.Extra) {
		// Generate vrf proof
		if value, err := bcr.vh.GenerateNonce(header.Number, header.ParentHash); nil != err {
			return err
		} else {
			header.Nonce = types.EncodeNonce(value)
		}
	} else {
		blockHash = header.Hash()
		// Verify vrf proof
		sign := header.Extra[32:97]
		pk, err := crypto.SigToPub(header.SealHash().Bytes(), sign)
		if nil != err {
			return err
		}
		if err := bcr.vh.VerifyVrf(pk, header.Number, header.ParentHash, blockHash, header.Nonce.Bytes()); nil != err {
			return err
		}
	}

	// TODO test
	pposHash = snapshotdb.Instance().GetLastKVHash(blockHash)
	log.Debug("BeginBlock pposHash, Before beginBlock", "blockNumber", header.Number.Uint64(),
		"blockHash", blockHash.Hex(), "pposHash", hex.EncodeToString(pposHash))

	log.Debug("Call snapshotDB newBlock on blockchain_reactor", "blockNumber", header.Number.Uint64(),
		"hash", hex.EncodeToString(blockHash.Bytes()), "parentHash", hex.EncodeToString(header.ParentHash.Bytes()))
	if err := snapshotdb.Instance().NewBlock(header.Number, header.ParentHash, blockHash); nil != err {
		log.Error("Failed to call snapshotDB newBlock on blockchain_reactor", "blockNumber",
			header.Number.Uint64(), "hash", hex.EncodeToString(blockHash.Bytes()), "parentHash",
			hex.EncodeToString(header.ParentHash.Bytes()), "err", err)
		return err
	}

	for _, pluginRule := range bcr.beginRule {
		if plugin, ok := bcr.basePluginMap[pluginRule]; ok {
			if err := plugin.BeginBlock(blockHash, header, state); nil != err {
				return err
			}
		}
	}

	// TODO test
	pposHash = snapshotdb.Instance().GetLastKVHash(blockHash)
	log.Debug("BeginBlock pposHash, After beginBlock", "blockNumber", header.Number.Uint64(),
		"blockHash", blockHash.Hex(), "pposHash", hex.EncodeToString(pposHash))

	// todo test
	root = state.IntermediateRoot(true)
	log.Debug("BeginBlock StateDB root, end", "blockHash", header.Hash().Hex(), "blockNumber",
		header.Number.Uint64(), "root", root.Hex(), "pointer", fmt.Sprintf("%p", state))

	return nil
}

// Called after every block had executed all txs
func (bcr *BlockChainReactor) EndBlocker(header *types.Header, state xcom.StateDB) error {

	/**
	this things about ppos
	*/
	if bcr.validatorMode != common.PPOS_VALIDATOR_MODE {
		return nil
	}

	blockHash := common.ZeroHash

	if !xutil.IsWorker(header.Extra) {
		blockHash = header.Hash()
	}

	// todo test
	root := state.IntermediateRoot(true)
	log.Debug("EndBlock StateDB root", "blockHash", blockHash.Hex(), "blockNumber",
		header.Number.Uint64(), "root", root.Hex(), "pointer", fmt.Sprintf("%p", state))

	// TODO test
	pposHash := snapshotdb.Instance().GetLastKVHash(blockHash)
	log.Debug("EndBlock pposHash, Before Store VRF Seed", "blockNumber", header.Number.Uint64(),
		"blockHash", blockHash.Hex(), "pposHash", hex.EncodeToString(pposHash))

	// Store the previous vrf random number
	if err := bcr.vh.Storage(header.Number, header.ParentHash, blockHash, header.Nonce.Bytes()); nil != err {
		log.Error("blockchain_reactor Storage proof failed", "blockNumber", header.Number.Uint64(),
			"blockHash", hex.EncodeToString(blockHash.Bytes()), "err", err)
		return err
	}

	// TODO test
	pposHash = snapshotdb.Instance().GetLastKVHash(blockHash)
	log.Debug("EndBlock pposHash, After Store VRF Seed", "blockNumber", header.Number.Uint64(),
		"blockHash", blockHash.Hex(), "pposHash", hex.EncodeToString(pposHash))

	for _, pluginRule := range bcr.endRule {
		if plugin, ok := bcr.basePluginMap[pluginRule]; ok {
			if err := plugin.EndBlock(blockHash, header, state); nil != err {
				return err
			}
		}
	}

	// todo test
	root = state.IntermediateRoot(true)
	log.Debug("EndBlock StateDB root, After EndBlock by plugin", "blockHash", blockHash.Hex(),
		"blockNumber", header.Number.Uint64(), "root", root.Hex(), "pointer", fmt.Sprintf("%p", state))

	// storage the ppos k-v Hash
	pposHash = snapshotdb.Instance().GetLastKVHash(blockHash)

	// TODO test
	log.Debug("EndBlock pposHash, Before Store pposHash", "blockNumber", header.Number.Uint64(),
		"blockHash", blockHash.Hex(), "pposHash", hex.EncodeToString(pposHash))

	if len(pposHash) != 0 && !bytes.Equal(pposHash, make([]byte, len(pposHash))) {
		// store hash about ppos
		state.SetState(cvm.StakingContractAddr, staking.GetPPOSHASHKey(), pposHash)
		log.Info("Store ppos hash", "blockHash", blockHash.Hex(), "blockNumber", header.Number.Uint64(),
			"pposHash", hex.EncodeToString(pposHash))
	}

	// todo test
	root = state.IntermediateRoot(true)
	log.Debug("EndBlock StateDB root, end", "blockHash", blockHash.Hex(), "blockNumber",
		header.Number.Uint64(), "root", root.Hex(), "pointer", fmt.Sprintf("%p", state))

	return nil
}

func (bcr *BlockChainReactor) Verify_tx(tx *types.Transaction, to common.Address) error {

	if _, ok := vm.PlatONPrecompiledContracts[to]; !ok {
		return nil
	}

	input := tx.Data()

	var contract vm.PlatONPrecompiledContract
	switch to {
	case cvm.StakingContractAddr:
		c := vm.PlatONPrecompiledContracts[cvm.StakingContractAddr]
		contract = c.(vm.PlatONPrecompiledContract)
	case cvm.RestrictingContractAddr:
		c := vm.PlatONPrecompiledContracts[cvm.RestrictingContractAddr]
		contract = c.(vm.PlatONPrecompiledContract)
	case cvm.GovContractAddr:
		c := vm.PlatONPrecompiledContracts[cvm.GovContractAddr]
		contract = c.(vm.PlatONPrecompiledContract)
	case cvm.SlashingContractAddr:
		c := vm.PlatONPrecompiledContracts[cvm.SlashingContractAddr]
		contract = c.(vm.PlatONPrecompiledContract)
	}
	if _, _, err := plugin.Verify_tx_data(input, contract.FnSigns()); nil != err {
		return err
	}
	return nil
}

func (bcr *BlockChainReactor) Sign(msg interface{}) error {
	return nil
}

func (bcr *BlockChainReactor) VerifySign(msg interface{}) error {
	return nil
}

func (bcr *BlockChainReactor) VerifyHeader(header *types.Header) error {

	// TODO header.Extra

	return nil
}

func (bcr *BlockChainReactor) GetLastNumber(blockNumber uint64) uint64 {
	return plugin.StakingInstance().GetLastNumber(blockNumber)
}

func (bcr *BlockChainReactor) GetValidator(blockNumber uint64) (*cbfttypes.Validators, error) {
	return plugin.StakingInstance().GetValidator(blockNumber)
}

func (bcr *BlockChainReactor) IsCandidateNode(nodeID discover.NodeID) bool {
	return plugin.StakingInstance().IsCandidateNode(nodeID)
}

func (bcr *BlockChainReactor) Flush(header *types.Header) error {
	log.Debug("Call snapshotdb flush on blockchain_reactor", "blockNumber", header.Number.Uint64(), "hash", hex.EncodeToString(header.Hash().Bytes()))
	if err := snapshotdb.Instance().Flush(header.Hash(), header.Number); nil != err {
		log.Error("Failed to call snapshotdb flush on blockchain_reactor", "blockNumber", header.Number.Uint64(), "hash", hex.EncodeToString(header.Hash().Bytes()), "err", err)
		return err
	}
	return nil
}
