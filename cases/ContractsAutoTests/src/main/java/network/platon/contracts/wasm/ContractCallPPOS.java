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
public class ContractCallPPOS extends WasmContract {
    private static String BINARY_0 = "0x0061736d0100000001721260027f7f0060017f0060017f017f60000060037f7f7f0060037f7f7f017f60027f7f017f60047f7f7f7f0060027f7e006000017f60017f017e60077f7f7f7f7f7f7f0060057f7f7f7e7e0060037f7e7f0060047f7f7f7f017f60077f7f7f7f7f7f7f017f60017e017f60047f7f7e7e017e02b0010703656e760c706c61746f6e5f70616e6963000303656e760b706c61746f6e5f63616c6c000f03656e761d706c61746f6e5f6765745f63616c6c5f6f75747075745f6c656e677468000903656e7616706c61746f6e5f6765745f63616c6c5f6f7574707574000103656e7617706c61746f6e5f6765745f696e7075745f6c656e677468000903656e7610706c61746f6e5f6765745f696e707574000103656e760d706c61746f6e5f72657475726e0000034d4c030505030202050303060404010b040700010103010105020202020700040201020400000200000000040008100d01060001110e010200080c00020600030a0a0200020100010100000100000405017001030305030100020615037f0141808b040b7f0041808b040b7f0041fe0a0b075406066d656d6f72790200115f5f7761736d5f63616c6c5f63746f727300070b5f5f686561705f6261736503010a5f5f646174615f656e6403020f5f5f66756e63735f6f6e5f65786974001a06696e766f6b6500440908010041010b021c350ade544c1600100a41a40a10194101101b41b00a10194102101b0ba20a010d7f2002410f6a210f410020026b21072002410e6a210a410120026b210e2002410d6a210d410220026b210c0340200020056a2103200120056a220441037145200220054672450440200320042d00003a0000200f417f6a210f200741016a2107200a417f6a210a200e41016a210e200d417f6a210d200c41016a210c200541016a21050c010b0b200220056b210602400240024002402003410371220b044020064120490d03200b4101460d01200b4102460d02200b4103470d032003200120056a280200220a3a0000200041016a210b200220056b417f6a210c200521030340200c4113494504402003200b6a2208200120036a220941046a2802002206411874200a41087672360200200841046a200941086a2802002204411874200641087672360200200841086a2009410c6a28020022064118742004410876723602002008410c6a200941106a280200220a411874200641087672360200200341106a2103200c41706a210c0c010b0b2002417f6a2007416d2007416d4b1b200f6a4170716b20056b2106200120036a41016a2104200020036a41016a21030c030b2006210403402004411049450440200020056a2203200120056a2202290200370200200341086a200241086a290200370200200541106a2105200441706a21040c010b0b027f2006410871450440200120056a2104200020056a0c010b200020056a2202200120056a2201290200370200200141086a2104200241086a0b21052006410471044020052004280200360200200441046a2104200541046a21050b20064102710440200520042f00003b0000200441026a2104200541026a21050b2006410171450d03200520042d00003a000020000f0b2003200120056a2206280200220a3a0000200341016a200641016a2f00003b0000200041036a210b200220056b417d6a210720052103034020074111494504402003200b6a2208200120036a220941046a2802002206410874200a41187672360200200841046a200941086a2802002204410874200641187672360200200841086a2009410c6a28020022064108742004411876723602002008410c6a200941106a280200220a410874200641187672360200200341106a2103200741706a21070c010b0b2002417d6a200c416f200c416f4b1b200d6a4170716b20056b2106200120036a41036a2104200020036a41036a21030c010b2003200120056a2206280200220d3a0000200341016a200641016a2d00003a0000200041026a210b200220056b417e6a210720052103034020074112494504402003200b6a2208200120036a220941046a2802002206411074200d41107672360200200841046a200941086a2802002204411074200641107672360200200841086a2009410c6a28020022064110742004411076723602002008410c6a200941106a280200220d411074200641107672360200200341106a2103200741706a21070c010b0b2002417e6a200e416e200e416e4b1b200a6a4170716b20056b2106200120036a41026a2104200020036a41026a21030b20064110710440200320042d00003a00002003200428000136000120032004290005370005200320042f000d3b000d200320042d000f3a000f200441106a2104200341106a21030b2006410871044020032004290000370000200441086a2104200341086a21030b2006410471044020032004280000360000200441046a2104200341046a21030b20064102710440200320042f00003b0000200441026a2104200341026a21030b2006410171450d00200320042d00003a00000b20000bfc0202027f017e02402002450d00200020013a0000200020026a2203417f6a20013a000020024103490d00200020013a0002200020013a00012003417d6a20013a00002003417e6a20013a000020024107490d00200020013a00032003417c6a20013a000020024109490d002000410020006b41037122046a2203200141ff017141818284086c22013602002003200220046b417c7122046a2202417c6a200136020020044109490d002003200136020820032001360204200241786a2001360200200241746a200136020020044119490d002003200136021820032001360214200320013602102003200136020c200241706a20013602002002416c6a2001360200200241686a2001360200200241646a20013602002001ad220542208620058421052004200341047141187222016b2102200120036a2101034020024120490d0120012005370300200141186a2005370300200141106a2005370300200141086a2005370300200141206a2101200241606a21020c000b000b20000b3501017f230041106b220041808b0436020c418408200028020c41076a41787122003602004180082000360200418c083f003602000b9f0101047f230041106b220224002002200036020c027f02400240024020000440418c08200041086a22014110762200418c082802006a2203360200418408200141840828020022016a41076a4178712204360200200341107420044d0d0120000d020c030b41000c030b418c08200341016a360200200041016a21000b200040000d0010000b20012002410c6a410410081a200141086a0b200241106a24000b2f01027f2000410120001b2100034002402000100b22010d004190082802002202450d0020021103000c010b0b20010bc10301067f024020002001460d00027f02400240200120006b20026b410020024101746b4b044020002001734103712103200020014f0d012003450d0220000c030b20002001200210080f0b024020030d002001417f6a21030340200020026a220441037104402002450d052004417f6a200220036a2d00003a00002002417f6a21020c010b0b2000417c6a21032001417c6a2104034020024104490d01200220036a200220046a2802003602002002417c6a21020c000b000b2001417f6a210103402002450d03200020026a417f6a200120026a2d00003a00002002417f6a21020c000b000b200241046a21062002417f73210503400240200120046a2107200020046a2208410371450d0020022004460d03200820072d00003a00002006417f6a2106200541016a2105200441016a21040c010b0b200220046b21014100210303402001410449450440200320086a200320076a280200360200200341046a21032001417c6a21010c010b0b200320076a210120022005417c2005417c4b1b20066a417c716b20046b2102200320086a0b210303402002450d01200320012d00003a00002002417f6a2102200341016a2103200141016a21010c000b000b20000b0a0041940841013602000b0a0041940841003602000b4d01017f20004200370200200041086a2202410036020020012d0000410171450440200020012902003702002002200141086a28020036020020000f0b200020012802082001280204101120000b6401027f2002417049044002402002410a4d0440200020024101743a0000200041016a21030c010b200241106a4170712204100c21032000200236020420002004410172360200200020033602080b2003200120021012200220036a41003a00000f0b000b13002002047f20002001200210080520000b1a0b130020002d0000410171044020002802081a0b0bb70101027f416e20016b20024f0440027f200041016a20002d0000410171450d001a20002802080b2108027f416f200141e6ffffff074b0d001a410b20014101742207200120026a220220022007491b2202410b490d001a200241106a4170710b2207100c21022005044020022006200510120b200320046b220322060440200220056a200420086a200610120b200020023602082000200320056a220136020420002007410172360200200120026a41003a00000f0b000b13002002047f200020012002100d0520000b1a0be30201057f027f20002d00002205410171220445044020054101760c010b20002802040b220641004f04402006200120062001491b2101410a2105200404402000280200417e71417f6a21050b200120066b20056a200349044020002005200320066a20016b20056b200620012003200210140f0b2004047f200028020805200041016a0b21040240024020012003460440200321010c010b200620016b2207450d00200120034b04402004200220031015200320046a200120046a200710150c020b0240200420066a20024d200420024f720d00200120046a20024b04402004200220011015200320016b200220036a2102200121084100210121030c010b2002200320016b6a21020b200420086a220520036a200120056a200710150b200420086a2002200310150b200320016b20066a2101024020002d0000410171450440200020014101743a00000c010b200020013602040b200120046a41003a00000f0b000b6d01027f2001417049044002402001410a4d0440200020014101743a0000200041016a21020c010b200141106a4170712203100c21022000200136020420002003410172360200200020023602080b2001047f20024130200110090520020b1a200120026a41003a00000f0b000b2301017f03402001410c46450440200020016a4100360200200141046a21010c010b0b0b170020004200370200200041086a4100360200200010180b7601037f100e41980828020021000340200004400340419c08419c082802002201417f6a22023602002001410148450440200020024102746a22004184016a280200200041046a280200100f110100100e41980828020021000c010b0b419c084120360200419808200028020022003602000c010b0b0b960101027f100e419808280200220145044041980841a00836020041a00821010b0240419c0828020022024120460440418402100b220104402001410041840210091a0b2001450d0120014198082802003602004198082001360200419c084100360200410021020b419c08200241016a360200200120024102746a22014184016a4100360200200141046a2000360200100f0f0b100f0b070041a40a10130b780020004200370210200042ffffffff0f3702082000200129020037020002402002410871450d002000101e20012802044f0d002002410471450440200042003702000c010b10000b024002402002411071450d002000101e20012802044d0d0020024104710d01200042003702000b20000f0b100020000b290002402000280204044020002802002c0000417f4c0d0141010f0b41000f0b2000101f200010206a0b240002402000280204450d0020002802002c0000417f4c0d0041000f0b2000102541016a0b8a0301047f0240024020002802040440200010264101210220002802002c00002201417f4c0d010c020b41000f0b200141ff0171220241b7014d0440200241807f6a0f0b02400240200141ff0171220141bf014d04400240200041046a22042802002201200241c97e6a22034d047f100020042802000520010b4102490d0020002802002d00010d0010000b200341054f044010000b20002802002d000145044010000b410021024100210103402001200346450440200028020020016a41016a2d00002002410874722102200141016a21010c010b0b200241384f0d010c020b200141f7014d0440200241c07e6a0f0b0240200041046a22042802002201200241897e6a22034d047f100020042802000520010b4102490d0020002802002d00010d0010000b200341054f044010000b20002802002d000145044010000b410021024100210103402001200346450440200028020020016a41016a2d00002002410874722102200141016a21010c010b0b20024138490d010b200241ff7d490d010b100020020f0b20020b3902017f017e230041306b2201240020012000290200220237031020012002370308200141186a200141086a4114101d101e200141306a24000b5e01027f2000027f027f2001280200220504404100200220036a200128020422014b2001200249720d011a410020012003490d021a200220056a2104200120026b20032003417f461b0c020b41000b210441000b360204200020043602000b2101017f20011020220220012802044b044010000b200020012001101f200210220b900302097f017e230041406a220324002001280208220520024b0440200341386a20011023200320032903383703182001200341186a102136020c200341306a20011023410021052001027f410020032802302206450d001a410020032802342208200128020c2207490d001a200820072007417f461b210420060b360210200141146a2004360200200141086a41003602000b200141106a2109200141146a21072001410c6a2106200141086a210803400240200520024f0d002007280200450d00200341306a2001102341002105027f2003280230220a044041002003280234220b20062802002204490d011a200b20046b21052004200a6a0c010b41000b210420072005360200200920043602002003200536022c2003200436022820032003290328370310200341306a20094100200341106a1021102220092003290330220c37020020062006280200200c422088a76a3602002008200828020041016a22053602000c010b0b20032009290200220c3703202003200c3703082000200341086a4114101d1a200341406b24000b4101017f02402000280204450d0020002802002d0000220041bf014d0440200041b801490d01200041c97e6a0f0b200041f801490d00200041897e6a21010b20010b4401017f200028020445044010000b0240200028020022012d0000418101470d00200041046a28020041014d047f100020002802000520010b2c00014100480d0010000b0b9f0101037f02402000280204044020001026200028020022022c000022014100480d0120014100470f0b41000f0b027f4101200141807f460d001a200141ff0171220341b7014d0440200041046a28020041014d047f100020002802000520020b2d00014100470f0b4100200341bf014b0d001a200041046a280200200141ff017141ca7e6a22014d047f100020002802000520020b20016a2d00004100470b0b2c002000200220016b2202102a200028020020002802046a2001200210081a2000200028020420026a3602040b9e0201077f02402001450d002000410c6a2107200041106a2105200041046a21060340200528020022022007280200460d01200241786a28020020014904401000200528020021020b200241786a2203200328020020016b220136020020010d01200520033602002000410120062802002002417c6a28020022016b2202102b220341016a20024138491b2204200628020022086a102c2004200120002802006a22046a2004200820016b100d1a0240200241374d0440200028020020016a200241406a3a00000c010b200341f7016a220441ff014d0440200028020020016a20043a00002000280200200120036a6a210103402002450d02200120023a0000200241087621022001417f6a21010c000b000b10000b410121010c000b000b0b1b00200028020420016a220120002802084b044020002001102d0b0b1e01017f03402000044020004108762100200141016a21010c010b0b20010b0f0020002001102d200020013602040b3901017f200028020820014904402001100b22022000280200200028020410081a20002802001a200041086a2001360200200020023602000b0b250020004101102a200028020020002802046a20013a00002000200028020441016a3602040b2b01027f2001102b220241b7016a22034180024e044010000b2000200341ff0171102e20002001200210300b3d002000200028020420026a102c200028020020002802046a417f6a2100034020010440200020013a0000200141087621012000417f6a21000c010b0b0ba00101037f230041106b2202240020012802002103024002400240024020012802042201410146044020032c000022044100480d012000200441ff0171102e0c040b200141374b0d010b200020014180017341ff0171102e0c010b20002001102f0b2002200136020c2002200336020820022002290308370300200020022802002201200120022802046a10282000410010290b200041011029200241106a24000b830101037f02400240200150450440200142ff00560d0120002001a741ff0171102e0c020b2000418001102e0c010b024020011033220241374d0440200020024180017341ff0171102e0c010b2002102b220341b7016a22044180024f044010000b2000200441ff0171102e20002002200310300b20002001200210340b2000410110290b3202017f017e034020002002845045044020024238862000420888842100200141016a2101200242088821020c010b0b20010b5101017e2000200028020420026a102c200028020020002802046a417f6a21000340200120038450450440200020013c0000200342388620014208888421012000417f6a2100200342088821030c010b0b0b070041b00a10130b810101037f230041106b220224002002200110374100210103402001411446450440200020016a41003a0000200141016a21010c010b0b2002280204200228020022036b21044100210103402001200446200141134b72450440200020016a200120036a2d00003a0000200141016a21010c010b0b20021038200241106a240020000bf30301077f230041306b22022400027f41002001280204220620012d000022034101762207200341017122031b22044102490d001a41002001280208200141016a20031b22082d00004130470d001a20082d000141f800464101740b21052002410036021020024200370308200441016a20056b41017622040440200241286a200241106a36020020022004100c22033602082002200336020c20024200370320200242003703182002200320046a360210200241186a103b20012d000022034101762107200141046a2802002106200341017121030b027f02402006200720031b41017104402001280208200141016a20031b20056a2c0000103c2203417f460d01200220033a0018200241086a200241186a103d200541017221050b200141016a2103200141046a2106200141086a2107024003402005200628020020012d00002204410176200441017122041b4904402007280200200320041b20056a22042c0000103c2208417f46200441016a2c0000103c2204417f46720d022002200420084104746a3a0018200541026a2105200241086a200241186a103d0c010b0b200020022802083602002000200229020c3702042002420037020c200241086a0c020b20004200370200200041086a0c010b20004200370200200041086a0b4100360200200241086a1038200241306a24000b1501017f200028020022010440200020013602040b0b5001017f230041406a220424002004200337033020042002370338200441206a20011037200441086a20001036200441206a200441386a200441306a103a200441206a1038200441406b2400410173ad0b6601017f230041206b22042400200441106a2002290300103e20042003290300103e200020012802002200200128020420006b20042802102200200428021420006b20042802002200200428020420006b100120041038200441106a1038200441206a2400450b3801037f2000280208210120002802042102200041086a210303402001200247044020032001417f6a22013602000c010b0b20002802001a0b4901017f415021010240200041506a41ff0171410a4f044041a97f21012000419f7f6a41ff017141064f0d010b200020016a0f0b200041496a417f200041bf7f6a41ff01714106491b0bcd0101047f230041206b220324000240200028020422022000280208490440200220012d00003a0000200041046a2200200028020041016a3602000c010b2000200241016a20002802006b10422104200341186a200041086a3602004100210220034100360214200041046a28020020002802006b2105200404402004100c21020b20032002360208200341146a200220046a360200200220056a220220012d00003a00002003200236020c2003200241016a3602102000200341086a1043200341086a103b0b200341206a24000b6302017f017e20012103034020035045044020034208882103200241016a21020c010b0b20004100360208200042003702002000200210402000280204417f6a21020340200150450440200220013c00002002417f6a2102200142088821010c010b0b0b820301057f230041d0006b220524002005200437034020052003370348200541306a200210370240027f0240200541186a20011036200541306a200541c8006a200541406b103a04402005410036021020054200370308200541086a1002104020052802081003200528020c210920052802082101200541186a10412107200041086a4100360200200042003702002000200728020420052d0018220241017620024101711b2202200920016b4101746a101720052d001822064101710d01200741016a210820064101760c020b200010411a0c020b20072802082108200741046a2802000b210620002002200820061016200041016a2108200041086a21060340200120094704402006280200200820002d00004101711b20026a20012d000041047641ed0a6a2d00003a00002006280200200820002d00004101711b20026a41016a20012d0000410f7141ed0a6a2d00003a0000200141016a2101200241026a21020c010b0b20071013200541086a10380b200541306a1038200541d0006a24000bab0201057f230041206b220324000240024020002802042202200028020022056b22042001490440200028020820026b200120046b4f0d012000200110422105200341186a200041086a36020020034100360214200041046a28020020002802006b210641002102200504402005100c21020b20032002360208200341146a200220056a3602002003200220066a22023602102003200236020c200420016b2101200341106a21040340200241003a00002004200428020041016a2202360200200141016a22010d000b2000200341086a1043200341086a103b0c020b200420014d0d01200041046a200120056a3602000c010b200420016b2101200041046a21000340200241003a00002000200028020041016a2202360200200141016a22010d000b0b200341206a24000b1e0020004200370200200041086a4100360200200041bc0a4100101120000b39002001417f4a0440200028020820002802006b220041feffffff034d047f20012000410174220020002001491b0541ffffffff070b0f0b000b940101037f200120012802042000280204200028020022046b22026b2203360204200241004a044020032004200210081a200141046a28020021030b2000280200210220002003360200200141046a22032002360200200041046a220228020021042002200128020836020020012004360208200028020821022000200128020c3602082001200236020c200120032802003602000be30602097f037e230041e0016b22002400100710042201100b22021005200020013602342000200236023020002000290330370308200041306a200041106a200041086a411c101d220141001024024002400240024002400240200041306a10452209500d0041bd0a10462009510d0541c20a10462009510440200041306a10472001200041306a104820004188016a200041306a10102104200041f8006a2000413c6a10102105200029035021092000290348210a200041b0016a2004101022012005200a2009103921092001101320004198016a1049210241002101200041d8016a4100360200200041d0016a4200370300200041c8016a4200370300200042003703c0014101210320094280015a04402009210a0340200a200b8450450440200b423886200a42088884210a200141016a2101200b420888210b0c010b0b200141384f047f2001102b20016a0520010b41016a21030b200020033602c001200041c0016a410472104a20022003104b200220091032200228020c200241106a28020047044010000b2002280200200228020410062002104c2005101320041013104d0c060b41d70a10462009520d00200041306a104721072001200041306a1048200041e8006a200041306a10102105200041d8006a2000413c6a10102106200029035021092000290348210a20004188016a200041f8006a2005101022012006200a2009103f2001101320004198016a10492102200041d8016a4100360200200041d0016a4200370300200041c8016a4200370300200042003703c00141012101200041b0016a20004188016a1010220428020420002d00b0012203410176200341017122081b2203450d0420034101470d012004280208200441016a20081b2c0000417f4a0d040c020b10000c040b200341374b0d010b200341016a21010c010b2003102b20036a41016a21010b200020013602c00120041013200041c0016a410472104a20022001104b2000200041c0016a20004188016a10102201280208200141016a20002d00c001220341017122041b3602b00120002001280204200341017620041b3602b401200020002903b00137030020022000103120011013200228020c200241106a28020047044010000b2002280200200228020410062002104c20004188016a101320061013200510132007104d0b200041e0016a24000b850102027f017e230041106b22012400200010260240024020001027450d002000280204450d0020002802002d000041c001490d010b10000b200141086a2000104e200128020c220041094f044010000b200128020821020340200004402000417f6a210020023100002003420886842103200241016a21020c010b0b200141106a240020030b3901027e42a5c688a1c89ca7f94b210103402000300000220250450440200041016a2100200142b383808080207e20028521010c010b0b20010b2000200010192000410c6a1019200041206a42003703002000420037031820000b4d01017f230041106b220224002002410136020c2002200036020020022002410c6a36020420022001105120022001410c6a10512002200141186a10522002200141206a1052200241106a24000b2900200041003602082000420037020020004100104f200041146a41003602002000420037020c20000bdc0201067f200028020422012000280210220241087641fcffff07716a2103027f2001200028020822054704402001200028021420026a220441087641fcffff07716a280200200441ff07714102746a2106200041146a21042003280200200241ff07714102746a0c010b200041146a210441000b2102034020022006470440200241046a220220032802006b418020470d0120032802042102200341046a21030c010b0b20044100360200200041086a21020340200520016b410275220341034f044020012802001a200041046a2201200128020041046a2201360200200228020021050c010b0b0240200041106a027f2003410147044020034102470d024180080c010b4180040b3602000b03402001200547044020012802001a200141046a21010c010b0b200041086a22032802002101200041046a280200210203402001200247044020032001417c6a22013602000c010b0b20002802001a0b13002000280208200149044020002001104f0b0b1c01017f200028020c22010440200041106a20013602000b200010500b0d002000410c6a1013200010130be60101047f200110202204200128020422024b04401000200141046a28020021020b200128020021052000027f024002400240027f0240200204404100210120052c00002203417f4c0d012005450d030c040b41000c010b200341ff0171220141bf014d04404100200341ff017141b801490d011a200141c97e6a0c010b4100200341ff017141f801490d001a200141897e6a0b41016a210120050d010b410021030c010b410021032002200149200120046a20024b720d00410020022004490d011a200120056a2103200220016b20042004417f461b0c010b41000b360204200020033602000b3601017f200028020820014904402001100b200028020020002802041008210220001050200041086a2001360200200020023602000b0b080020002802001a0bae0301067f230041306b220224002002200028020020002802042802001024024002400240024002402002280204450d0020022802002d000041c0014f0d00200241286a2002104e200210202103200228022822050440200228022c220420034f0d020b41002105200241206a410036020020024200370318410021030c020b200241186a10190c030b200241206a410036020020024200370318200420032003417f461b22034170490440200320056a21062003410a4d0d01200341106a4170712207100c21042002200336021c20022007410172360218200220043602200c020b000b200220034101743a0018200241186a41017221040b034020052006470440200420052d00003a0000200441016a2104200541016a21050c010b0b200441003a00000b024020012d0000410171450440200141003b01000c010b200128020841003a00002001410036020420012d0000410171450d00200141086a2802001a200141003602000b20012002290318370200200141086a200241206a280200360200200241186a1018200241186a1013200041046a2802002200200028020041016a360200200241306a24000b4601017f230041206b22022400200241086a2000280200200028020428020010242001200241086a104537030020002802042200200028020041016a360200200241206a24000b0b47010041bd0a0b40696e69740063726f73735f63616c6c5f70706f735f73656e640063726f73735f63616c6c5f70706f735f71756572790030313233343536373839616263646566";

    public static String BINARY = BINARY_0;

    public static final String FUNC_CROSS_CALL_PPOS_QUERY = "cross_call_ppos_query";

    public static final String FUNC_CROSS_CALL_PPOS_SEND = "cross_call_ppos_send";

    protected ContractCallPPOS(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    protected ContractCallPPOS(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> cross_call_ppos_query(String target_addr, String in, Uint64 value, Uint64 gas) {
        final WasmFunction function = new WasmFunction(FUNC_CROSS_CALL_PPOS_QUERY, Arrays.asList(target_addr,in,value,gas), String.class);
        return executeRemoteCall(function, String.class);
    }

    public RemoteCall<TransactionReceipt> cross_call_ppos_send(String target_addr, String in, Uint64 value, Uint64 gas) {
        final WasmFunction function = new WasmFunction(FUNC_CROSS_CALL_PPOS_SEND, Arrays.asList(target_addr,in,value,gas), Void.class);
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> cross_call_ppos_send(String target_addr, String in, Uint64 value, Uint64 gas, BigInteger vonValue) {
        final WasmFunction function = new WasmFunction(FUNC_CROSS_CALL_PPOS_SEND, Arrays.asList(target_addr,in,value,gas), Void.class);
        return executeRemoteCallTransaction(function, vonValue);
    }

    public static RemoteCall<ContractCallPPOS> deploy(Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
        String encodedConstructor = WasmFunctionEncoder.encodeConstructor(BINARY, Arrays.asList());
        return deployRemoteCall(ContractCallPPOS.class, web3j, credentials, contractGasProvider, encodedConstructor);
    }

    public static RemoteCall<ContractCallPPOS> deploy(Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
        String encodedConstructor = WasmFunctionEncoder.encodeConstructor(BINARY, Arrays.asList());
        return deployRemoteCall(ContractCallPPOS.class, web3j, transactionManager, contractGasProvider, encodedConstructor);
    }

    public static RemoteCall<ContractCallPPOS> deploy(Web3j web3j, Credentials credentials, GasProvider contractGasProvider, BigInteger initialVonValue) {
        String encodedConstructor = WasmFunctionEncoder.encodeConstructor(BINARY, Arrays.asList());
        return deployRemoteCall(ContractCallPPOS.class, web3j, credentials, contractGasProvider, encodedConstructor, initialVonValue);
    }

    public static RemoteCall<ContractCallPPOS> deploy(Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider, BigInteger initialVonValue) {
        String encodedConstructor = WasmFunctionEncoder.encodeConstructor(BINARY, Arrays.asList());
        return deployRemoteCall(ContractCallPPOS.class, web3j, transactionManager, contractGasProvider, encodedConstructor, initialVonValue);
    }

    public static ContractCallPPOS load(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
        return new ContractCallPPOS(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ContractCallPPOS load(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
        return new ContractCallPPOS(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}