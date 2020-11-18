package JRedisTest;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;

public class RedisInvokeTest {
	
	private Jedis jedis = null;

	@Before
	public void setUp() {
		jedis = new Jedis("localhost");
	}

    @Test
    public void normalTest() {

        long begin = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            System.out.println(i + "---" + jedis.set("n_" + i, "n_" + i));
        }

        long end = System.currentTimeMillis();

        System.out.println("normal Set:" + (end - begin));

        jedis.disconnect();
    }


    @Test
    public void transTest() {

        long begin = System.currentTimeMillis();
        Transaction transaction = jedis.multi();
        for (int i = 0; i < 1000; i++) {
            transaction.set("t_" + i, "t_" + i);
        }
        List<Object> result = transaction.exec();

        long end = System.currentTimeMillis();

        System.out.println("Transaction Set:" + (end - begin));
        jedis.disconnect();
        System.out.println(result);
    }

  
    @Test
    public void PipelinedTest() {

        long begin = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        for (int i = 0; i < 1000; i++) {
            pipeline.set("p_" + i, "p_" + i);
        }
        List<Object> result = pipeline.syncAndReturnAll();

        long end = System.currentTimeMillis();

        System.out.println("pipeline Set:" + (end - begin));
        jedis.disconnect();
        System.out.println(result);
    }


    @Test
    public void shardNormalTest(){
        List<JedisShardInfo> shards = Arrays.asList(
                new JedisShardInfo("localhost",6379)
        );

        ShardedJedis sharding = new ShardedJedis(shards);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String result = sharding.set("shardNormal " + i, "sn" + i);
        }

        long end = System.currentTimeMillis();
        System.out.println("shardNormal SET: " + (end - start));

        sharding.disconnect();
    }


    @Test
    public void shardPipelinedPoolTest(){
        List<JedisShardInfo> shards = Arrays.asList(
                new JedisShardInfo("localhost",6379));

        ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(),shards);

        ShardedJedis one = pool.getResource();
        ShardedJedisPipeline pipeline = one.pipelined();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            pipeline.set("shardPipelinedPool" + i, "n" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        
        
        pool.close();
        System.out.println("shardPipelinedPool SET: " + (end - start));
    }
	
}
