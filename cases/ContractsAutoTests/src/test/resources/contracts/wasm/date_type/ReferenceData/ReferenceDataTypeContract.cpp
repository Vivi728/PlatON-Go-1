// Author: zjsunzone
// Desc: 针对引用类型的相关测试
#include <platon/platon.hpp>
#include <string>
using namespace std;
using namespace platon;

// 声明存储类型的映射 key.
extern char const maddress[] = "map_address";
extern char const mu256[] = "map_u256";
extern char const mh256[] = "map_h256";

CONTRACT ReferenceDataTypeContract: public platon::Contract{

	private:
       platon::StorageType<maddress, std::map<std::string, Address>> tmaddress;
       platon::StorageType<mu256, std::map<std::string, u256>> tmu256;
       platon::StorageType<mh256, std::map<std::string, h256>> tmh256;

    public:
        ACTION void init(){}
		
		// 针对address类型的map进行设置
		ACTION void setAddressMap(const std::string& key, const std::string& addr)
		{
			tmaddress.self()[key] = Address(addr);
			//tmaddress.self()[key] = "compile error"; // expect: compile error.
			//tmaddress.self()[key] = 111;			   // expect: compile error.
		}
		
		// get value from map of address type.
		CONST std::string getAddrFromMap(const std::string& key) 		
		{
			return tmaddress.self()[key].toString();
		}
		
		// get the length of map.
		CONST uint8_t sizeOfAddrMap()
		{
			return tmaddress.self().size();
		}
		
	public:
		// 针对u256类型的map进行设置
		ACTION void setU256Map(const std::string& key, uint64_t value)
		{
			tmu256.self()[key] = u256(value);
			//tmu256.self()[key] = "compile error"; // expect: compile error.
			//tmu256.self()[key] = 111;			   // expect: compile error.
		}
		
		// get value from map of address type.
		CONST std::string getU256FromMap(const std::string& key) 		
		{
			return to_string(tmu256.self()[key]);
		}
		
		// get the length of map.
		CONST uint8_t sizeOfU256Map()
		{
			return tmu256.self().size();
		}

	public:
		// 针对h256类型的map进行设置
		ACTION void setH256Map(const std::string& key, const std::string& value)
		{
			tmh256.self()[key] = h256(value);
			//tmh256.self()[key] = "compile error"; // expect: compile error.
			//tmh256.self()[key] = 111;			   // expect: compile error.
		}
		
		// get value from map of h256 type.
		CONST std::string getH256FromMap(const std::string& key) 		
		{
			return tmh256.self()[key].toString();
		}
		
		// get the length of map.
		CONST uint8_t sizeOfH256Map()
		{
			return tmh256.self().size();
		}
        
};

PLATON_DISPATCH(ReferenceDataTypeContract, (init)
(setAddressMap)(getAddrFromMap)(sizeOfAddrMap)
(setU256Map)(getU256FromMap)(sizeOfU256Map)
(setH256Map)(getH256FromMap)(sizeOfH256Map))

