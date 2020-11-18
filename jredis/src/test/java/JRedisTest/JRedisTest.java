package JRedisTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JRedisTest {

	private Jedis jedis = null;

	@Before
	public void setUp() {
		jedis = new Jedis("localhost");
	}
	
	@Test
    public void TestJedisOperateString(){
		
		jedis.set("name", "chenssy");
		assertEquals("chenssy", jedis.get("name"));
		
		jedis.append("name","_cmblogs.com");
		assertEquals("chenssy_cmblogs.com", jedis.get("name"));
		
		jedis.del("name");
		assertEquals(null,jedis.get("name"));
		
		jedis.mset("name","chenssy","blog","cmsblogs");
		assertEquals("cmsblogs", jedis.get("blog"));
		
	}
	
	@Test
	public void TestJedisOperateMap() {
		Map<String, String> user = new HashMap<String, String>();
		user.put("name", "chenssy");
		user.put("sex", "boy");
		user.put("github", "chenssy89");
		user.put("QQ", "122448894");
		
		jedis.hmset("user" ,user);
		
		List<String> list = jedis.hmget("user","name","sex","github","QQ");
		assertEquals(4, list.size());
		
        jedis.hdel("user","QQ");
        assertEquals(null, jedis.hmget("user","QQ"));
        System.out.println("user len:" + jedis.hlen("user"));
        System.out.println("jedis exists:" + jedis.exists("user"));
        System.out.println("user key list" + jedis.hkeys("user"));
        System.out.println("user value list"+jedis.hvals("user"));
		
		
	}
	
    @Test
    public void TestJedisOperateList(){
        jedis.del("list");

        jedis.lpush("list","chenssy");
        jedis.lpush("list","boy");
        jedis.lpush("list","chenss89");
        jedis.lpush("list","122448894");
        
        System.out.println(jedis.lrange("list",0,-1));
        System.out.println(jedis.lpop("list"));
        System.out.println(jedis.lrange("list",0,-1));
        
       assertNotNull(jedis.lpop("list"));
    }

    @Test
    public void TestJedisOperateSet(){
        jedis.sadd("set","chenssy");
        jedis.sadd("set","boy");
        jedis.sadd("set","chenssy89");
        jedis.sadd("set","122448894");
        jedis.sadd("set","chenssy");

        System.out.println(jedis.smembers("set"));
        System.out.println(jedis.sismember("set", "boy"));
        System.out.println(jedis.srandmember("set"));
        System.out.println(jedis.scard("set"));
        
        assertNotNull(jedis.scard("set"));
    }

    @Test
    public void TestJedisOperateSort(){
        jedis.del("sort");

        jedis.rpush("sort","8");
        jedis.rpush("sort","7");
        jedis.rpush("sort","9");
        jedis.rpush("sort","6");
        jedis.rpush("sort","4");
        jedis.rpush("sort","5");

        System.out.println(jedis.lrange("sort",0,-1));
        System.out.println(jedis.sort("sort"));
        System.out.println(jedis.lrange("sort",0,-1));
        
        assertNotNull(jedis.sort("sort"));
    }


    
}
