package network.platon.contracts.wasm;

import com.platon.rlp.datatypes.Uint64;
import java.math.BigInteger;
import java.util.Arrays;
import org.web3j.abi.WasmFunctionEncoder;
import org.web3j.abi.datatypes.WasmFunction;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.WasmContract;
import org.web3j.tx.gas.GasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://github.com/PlatONnetwork/client-sdk-java/releases">platon-web3j command line tools</a>,
 * or the org.web3j.codegen.WasmFunctionWrapperGenerator in the 
 * <a href="https://github.com/PlatONnetwork/client-sdk-java/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with platon-web3j version 0.9.1.2-SNAPSHOT.
 */
public class RecursionCall extends WasmContract {
    private static String BINARY_0 = "0x0061736d01000000014d0e60017f0060017f017f60000060027f7f0060037f7f7f0060037f7f7f017f60047f7f7f7f0060027f7e0060027f7f017f60017f017e60037f7e7f006000017f60047f7f7f7f017f60017e017f02a9010703656e760c706c61746f6e5f70616e6963000203656e7617706c61746f6e5f6765745f696e7075745f6c656e677468000b03656e7610706c61746f6e5f6765745f696e707574000003656e760d706c61746f6e5f72657475726e000303656e7617706c61746f6e5f6765745f73746174655f6c656e677468000803656e7610706c61746f6e5f6765745f7374617465000c03656e7610706c61746f6e5f7365745f73746174650006033332020508020101040202000202000005010101010603040100010001030303070d040a000205090901000101030700000300020405017001030305030100020615037f0141d08a040b7f0041d08a040b7f0041ce0a0b075406066d656d6f72790200115f5f7761736d5f63616c6c5f63746f727300070b5f5f686561705f6261736503010a5f5f646174615f656e6403020f5f5f66756e63735f6f6e5f65786974001206696e766f6b65002a0908010041010b0214290a9334321c00100a41a40a420037020041ac0a410036020010114101101310380ba20a010d7f2002410f6a210f410020026b21072002410e6a210a410120026b210e2002410d6a210d410220026b210c0340200020056a2103200120056a220441037145200220054672450440200320042d00003a0000200f417f6a210f200741016a2107200a417f6a210a200e41016a210e200d417f6a210d200c41016a210c200541016a21050c010b0b200220056b210602400240024002402003410371220b044020064120490d03200b4101460d01200b4102460d02200b4103470d032003200120056a280200220a3a0000200041016a210b200220056b417f6a210c200521030340200c4113494504402003200b6a2208200120036a220941046a2802002206411874200a41087672360200200841046a200941086a2802002204411874200641087672360200200841086a2009410c6a28020022064118742004410876723602002008410c6a200941106a280200220a411874200641087672360200200341106a2103200c41706a210c0c010b0b2002417f6a2007416d2007416d4b1b200f6a4170716b20056b2106200120036a41016a2104200020036a41016a21030c030b2006210403402004411049450440200020056a2203200120056a2202290200370200200341086a200241086a290200370200200541106a2105200441706a21040c010b0b027f2006410871450440200120056a2104200020056a0c010b200020056a2202200120056a2201290200370200200141086a2104200241086a0b21052006410471044020052004280200360200200441046a2104200541046a21050b20064102710440200520042f00003b0000200441026a2104200541026a21050b2006410171450d03200520042d00003a000020000f0b2003200120056a2206280200220a3a0000200341016a200641016a2f00003b0000200041036a210b200220056b417d6a210720052103034020074111494504402003200b6a2208200120036a220941046a2802002206410874200a41187672360200200841046a200941086a2802002204410874200641187672360200200841086a2009410c6a28020022064108742004411876723602002008410c6a200941106a280200220a410874200641187672360200200341106a2103200741706a21070c010b0b2002417d6a200c416f200c416f4b1b200d6a4170716b20056b2106200120036a41036a2104200020036a41036a21030c010b2003200120056a2206280200220d3a0000200341016a200641016a2d00003a0000200041026a210b200220056b417e6a210720052103034020074112494504402003200b6a2208200120036a220941046a2802002206411074200d41107672360200200841046a200941086a2802002204411074200641107672360200200841086a2009410c6a28020022064110742004411076723602002008410c6a200941106a280200220d411074200641107672360200200341106a2103200741706a21070c010b0b2002417e6a200e416e200e416e4b1b200a6a4170716b20056b2106200120036a41026a2104200020036a41026a21030b20064110710440200320042d00003a00002003200428000136000120032004290005370005200320042f000d3b000d200320042d000f3a000f200441106a2104200341106a21030b2006410871044020032004290000370000200441086a2104200341086a21030b2006410471044020032004280000360000200441046a2104200341046a21030b20064102710440200320042f00003b0000200441026a2104200341026a21030b2006410171450d00200320042d00003a00000b20000be10201027f02402001450d00200041003a0000200020016a2202417f6a41003a000020014103490d00200041003a0002200041003a00012002417d6a41003a00002002417e6a41003a000020014107490d00200041003a00032002417c6a41003a000020014109490d002000410020006b41037122036a220241003602002002200120036b417c7122036a2201417c6a410036020020034109490d002002410036020820024100360204200141786a4100360200200141746a410036020020034119490d002002410036021820024100360214200241003602102002410036020c200141706a41003602002001416c6a4100360200200141686a4100360200200141646a41003602002003200241047141187222036b2101200220036a2102034020014120490d0120024200370300200241186a4200370300200241106a4200370300200241086a4200370300200241206a2102200141606a21010c000b000b20000b3501017f230041106b220041d08a0436020c418408200028020c41076a41787122003602004180082000360200418c083f003602000b9f0101047f230041106b220224002002200036020c027f02400240024020000440418c08200041086a22014110762200418c082802006a2203360200418408200141840828020022016a41076a4178712204360200200341107420044d0d0120000d020c030b41000c030b418c08200341016a360200200041016a21000b200040000d0010000b20012002410c6a410410081a200141086a0b200241106a24000b2f01027f2000410120001b2100034002402000100b22010d004190082802002202450d0020021102000c010b0b20010bbf0301057f024020002001460d00027f02400240200120006b20026b410020024101746b4b044020002001734103712103200020014f0d012003450d0220000c030b20002001200210081a0f0b024020030d002001417f6a21040340200020026a220341037104402002450d052003417f6a200220046a2d00003a00002002417f6a21020c010b0b2000417c6a21042001417c6a2103034020024104490d01200220046a200220036a2802003602002002417c6a21020c000b000b2001417f6a210103402002450d03200020026a417f6a200120026a2d00003a00002002417f6a21020c000b000b200241046a21052002417f73210303400240200120046a2106200020046a2207410371450d0020022004460d03200720062d00003a00002005417f6a2105200341016a2103200441016a21040c010b0b200220046b2101410021000340200141044f0440200020076a200020066a280200360200200041046a21002001417c6a21010c010b0b200020066a210120022003417c2003417c4b1b20056a417c716b20046b2102200020076a0b210003402002450d01200020012d00003a00002002417f6a2102200041016a2100200141016a21010c000b000b0b0a0041940841013602000b0a0041940841003602000b130020002d0000410171044020002802081a0b0b2301017f03402000410c470440200041a40a6a4100360200200041046a21000c010b0b0b7601037f100e41980828020021000340200004400340419c08419c082802002201417f6a22023602002001410148450440200020024102746a22004184016a280200200041046a280200100f110000100e41980828020021000c010b0b419c084120360200419808200028020022003602000c010b0b0b940101027f100e419808280200220145044041980841a00836020041a00821010b0240419c0828020022024120460440418402100b22010440200141840210091a0b2001450d0120014198082802003602004198082001360200419c084100360200410021020b419c08200241016a360200200120024102746a22014184016a4100360200200141046a2000360200100f0f0b100f0b070041a40a10100b780020004200370210200042ffffffff0f3702082000200129020037020002402002410871450d002000101620012802044f0d002002410471450440200042003702000c010b10000b024002402002411071450d002000101620012802044d0d0020024104710d01200042003702000b20000f0b100020000b290002402000280204044020002802002c0000417f4c0d0141010f0b41000f0b20001017200010186a0b240002402000280204450d0020002802002c0000417f4c0d0041000f0b2000101d41016a0b8a0301047f02400240200028020404402000101e4101210220002802002c00002201417f4c0d010c020b41000f0b200141ff0171220241b7014d0440200241807f6a0f0b02400240200141ff0171220141bf014d04400240200041046a22042802002201200241c97e6a22034d047f100020042802000520010b4102490d0020002802002d00010d0010000b200341054f044010000b20002802002d000145044010000b410021024100210103402001200346450440200028020020016a41016a2d00002002410874722102200141016a21010c010b0b200241384f0d010c020b200141f7014d0440200241c07e6a0f0b0240200041046a22042802002201200241897e6a22034d047f100020042802000520010b4102490d0020002802002d00010d0010000b200341054f044010000b20002802002d000145044010000b410021024100210103402001200346450440200028020020016a41016a2d00002002410874722102200141016a21010c010b0b20024138490d010b200241ff7d490d010b100020020f0b20020b3902017f017e230041306b2201240020012000290200220237031020012002370308200141186a200141086a411410151016200141306a24000b5e01027f2000027f027f2001280200220504404100200220036a200128020422014b2001200249720d011a410020012003490d021a200220056a2104200120026b20032003417f461b0c020b41000b210441000b360204200020043602000b2101017f20011018220220012802044b044010000b20002001200110172002101a0b900302097f017e230041406a220324002001280208220520024b0440200341386a2001101b200320032903383703182001200341186a101936020c200341306a2001101b410021052001027f410020032802302206450d001a410020032802342208200128020c2207490d001a200820072007417f461b210420060b360210200141146a2004360200200141086a41003602000b200141106a2109200141146a21072001410c6a2106200141086a210803400240200520024f0d002007280200450d00200341306a2001101b41002105027f2003280230220a044041002003280234220b20062802002204490d011a200b20046b21052004200a6a0c010b41000b210420072005360200200920043602002003200536022c2003200436022820032003290328370310200341306a20094100200341106a1019101a20092003290330220c37020020062006280200200c422088a76a3602002008200828020041016a22053602000c010b0b20032009290200220c3703202003200c3703082000200341086a411410151a200341406b24000b4101017f02402000280204450d0020002802002d0000220041bf014d0440200041b801490d01200041c97e6a0f0b200041f801490d00200041897e6a21010b20010b4401017f200028020445044010000b0240200028020022012d0000418101470d00200041046a28020041014d047f100020002802000520010b2c00014100480d0010000b0b9f0101037f0240200028020404402000101e200028020022022c000022014100480d0120014100470f0b41000f0b027f4101200141807f460d001a200141ff0171220341b7014d0440200041046a28020041014d047f100020002802000520020b2d00014100470f0b4100200341bf014b0d001a200041046a280200200141ff017141ca7e6a22014d047f100020002802000520020b20016a2d00004100470b0b940201097f02402000410c6a2107200041106a2104200041046a21060340200428020022012007280200460d01200141786a28020041014904401000200428020021010b200141786a2202200228020041016b220336020020030d01200420023602002000410120062802002001417c6a28020022026b22011021220341016a20014138491b2205200628020022086a10222005200220002802006a22096a2009200820026b100d0240200141374d0440200028020020026a200141406a3a00000c010b200341f7016a220541ff014d0440200028020020026a20053a00002000280200200220036a6a210203402001450d02200220013a0000200141087621012002417f6a21020c000b000b10000b0c000b000b0b1e01017f03402000044020004108762100200141016a21010c010b0b20010b0f00200020011023200020013602040b3901017f200028020820014904402001100b22022000280200200028020410081a20002802001a200041086a2001360200200020023602000b0b3a01017f200028020441016a220220002802084b04402000200210230b200028020020002802046a20013a00002000200028020441016a3602040b810101037f02400240200150450440200142ff00560d0120002001a741ff017110240c020b200041800110240c010b024020011026220241374d0440200020024180017341ff017110240c010b20021021220341b7016a22044180024f044010000b2000200441ff0171102420002002200310270b20002001200210280b200010200b3202017f017e034020002002845045044020024238862000420888842100200141016a2101200242088821020c010b0b20010b3d002000200028020420026a1022200028020020002802046a417f6a2100034020010440200020013a0000200141087621012000417f6a21000c010b0b0b5101017e2000200028020420026a1022200028020020002802046a417f6a21000340200120038450450440200020013c0000200342388620014208888421012000417f6a2100200342088821030c010b0b0b070041b00a10100b920202037f027e230041d0006b22012400100710012200100b22021002200141386a200120022000102b22004100101c02400240200141386a102c2203500d0041bc0a102d2003510440200141386a102e102f0c020b41c10a102d2003510440200141386a20004101101c200141386a102c2104200141386a102e22002903102103200041106a21020340200320045404402002200342017c22033703000c010b0b2000102f0c020b41c60a102d2003520d002001200141186a102e22022903102203370330200141386a10302200200141306a10311032200020031033200028020c200041106a28020047044010000b200028020020002802041003200010342002102f0c010b10000b200141d0006a24000b3401017f230041106b220324002003200236020c200320013602082003200329030837030020002003411c1015200341106a24000ba30202057f017e2000101e024002402000101f450d002000280204450d0020002802002d000041c001490d010b10000b200010182204200028020422014b04401000200041046a28020021010b2000280200210502400240027f027f024020010440410020052c00002200417f4a0d031a200041ff0171220241bf014b0d014100200041ff017141b801490d021a200241c97e6a0c020b410120050d021a410021000c030b4100200041ff017141f801490d001a200241897e6a0b41016a0b2102410021002001200449200220046a20014b720d0020012002490d01200220056a2103200120026b20042004417f461b22004109490d0110000c010b0b0340200004402000417f6a210020033100002006420886842106200341016a21030c010b0b20060b3901027e42a5c688a1c89ca7f94b210103402000300000220250450440200041016a2100200142b383808080207e20028521010c010b0b20010bf90101087f230041406a22012400200042e9feb2fee9efcf848c7f37030820004200370300200141286a1030220320002903081033200328020c200341106a28020047044010000b0240200328020022062003280204220710042202044020014100360220200142003703182002417f4c0d01200141206a2002100c20021009220520026a22083602002001200836021c2001200536021820062007200520021005417f470440200041106a20012001280218220441016a200128021c2004417f736a102b102c370300200221040b200141186a10350b200310342004450440200041106a20002903003703000b200141406b240020000f0b000be402010a7f230041406a22022400200241286a10302204200041086a10311032200420002903081033200428020c200441106a28020047044010000b200428020421062004280200200241106a10302101200041106a2208103121094101100c220041fe013a0000200220003602002002200041016a220336020820022003360204200128020c200141106a280200470440100020022802042103200228020021000b200320006b2203200128020422056a220a20012802084b047f2001200a1036200141046a2802000520050b20012802006a2000200310081a200141046a2200200028020020036a3602002001200228020420096a20022802006b103220012008290300103302402001410c6a2203280200200141106a220528020047044010002001280200210020032802002005280200460d0110000c010b200128020021000b20062000200141046a2802001006200210352001103420041034200241406b24000b29002000410036020820004200370200200041001036200041146a41003602002000420037020c20000b7502017f027e41012101200029030022034280015a047f41002101034020022003845045044020024238862003420888842103200141016a2101200242088821020c010b0b024020014138490d002001210003402000450d01200141016a2101200041087621000c000b000b200141016a0541010b0b1300200028020820014904402000200110360b0b08002000200110250b1c01017f200028020c22010440200041106a20013602000b200010370b1501017f200028020022010440200020013602040b0b3601017f200028020820014904402001100b200028020020002802041008210220001037200041086a2001360200200020023602000b0b080020002802001a0b3801017f41b00a420037020041b80a410036020041742100034020000440200041bc0a6a4100360200200041046a21000c010b0b410210130b0b18010041bc0a0b11696e69740063616c6c006765745f73756d";

    public static String BINARY = BINARY_0;

    public static final String FUNC_CALL = "call";

    public static final String FUNC_GET_SUM = "get_sum";

    protected RecursionCall(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    protected RecursionCall(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<RecursionCall> deploy(Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
        String encodedConstructor = WasmFunctionEncoder.encodeConstructor(BINARY, Arrays.asList());
        return deployRemoteCall(RecursionCall.class, web3j, credentials, contractGasProvider, encodedConstructor);
    }

    public static RemoteCall<RecursionCall> deploy(Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
        String encodedConstructor = WasmFunctionEncoder.encodeConstructor(BINARY, Arrays.asList());
        return deployRemoteCall(RecursionCall.class, web3j, transactionManager, contractGasProvider, encodedConstructor);
    }

    public static RemoteCall<RecursionCall> deploy(Web3j web3j, Credentials credentials, GasProvider contractGasProvider, BigInteger initialVonValue) {
        String encodedConstructor = WasmFunctionEncoder.encodeConstructor(BINARY, Arrays.asList());
        return deployRemoteCall(RecursionCall.class, web3j, credentials, contractGasProvider, encodedConstructor, initialVonValue);
    }

    public static RemoteCall<RecursionCall> deploy(Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider, BigInteger initialVonValue) {
        String encodedConstructor = WasmFunctionEncoder.encodeConstructor(BINARY, Arrays.asList());
        return deployRemoteCall(RecursionCall.class, web3j, transactionManager, contractGasProvider, encodedConstructor, initialVonValue);
    }

    public RemoteCall<TransactionReceipt> call(Uint64 n) {
        final WasmFunction function = new WasmFunction(FUNC_CALL, Arrays.asList(n), Void.class);
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> call(Uint64 n, BigInteger vonValue) {
        final WasmFunction function = new WasmFunction(FUNC_CALL, Arrays.asList(n), Void.class);
        return executeRemoteCallTransaction(function, vonValue);
    }

    public RemoteCall<Uint64> get_sum() {
        final WasmFunction function = new WasmFunction(FUNC_GET_SUM, Arrays.asList(), Uint64.class);
        return executeRemoteCall(function, Uint64.class);
    }

    public static RecursionCall load(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
        return new RecursionCall(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static RecursionCall load(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
        return new RecursionCall(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}