package hpr;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SentinelServersConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

/**
 * @author haopeiren
 * @since 2020/1/30
 */
@SpringBootApplication
public class JedisTest implements CommandLineRunner
{
    public static void main(String[] args)
    {
        SpringApplication.run(JedisTest.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        String[] node = new String[]{"redis://127.0.0.1:26379", "redis://127.0.0.1:6379", "redis://127.0.0.1:6380"};
        Config config = new Config();
        SentinelServersConfig ssc = config.useSentinelServers();
        ssc.addSentinelAddress(node)
                .setMasterName("mymaster")
                .setReadMode(ReadMode.SLAVE)
        .setDatabase(0)
        .setClientName("clientName")
        .setConnectTimeout(1000)
        .setFailedSlaveCheckInterval(1)
        .setFailedSlaveReconnectionInterval(1)
        .setIdleConnectionTimeout(1000)
        .setMasterConnectionMinimumIdleSize(10)
                .setMasterConnectionPoolSize(10)
                .setSlaveConnectionMinimumIdleSize(10)
                .setTimeout(1000)
        .setSlaveConnectionPoolSize(10);
        ssc.getSentinelAddresses().forEach(e -> System.out.println(e));
        RedissonClient client = Redisson.create(config);
        client.getKeys().getKeys().forEach(System.out::println);
        Redisson r;
    }

    //single
//    @Override
//    public void run(String... args) throws Exception
//    {
//        String[] node = new String[]{"redis://127.0.0.1:6379", "redis://127.0.0.1:6380"};
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress("redis://127.0.0.1:6379");
//        RedissonClient client = Redisson.create(config);
//        client.getKeys().getKeys().forEach(System.out::println);
//    }
}
