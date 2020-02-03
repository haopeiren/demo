package hpr.test.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author haopeiren
 * @since 2020/2/3
 */
public class CountInterceptor implements ProducerInterceptor<String, String>
{
    private AtomicInteger succeedCount = new AtomicInteger(0);

    private AtomicInteger failedCount = new AtomicInteger(0);

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord)
    {
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e)
    {
        if (e == null)
        {
            System.out.println("send message succeed. count : " + succeedCount.incrementAndGet());
        }
        else
        {
            System.out.println("send message failed. count : " + failedCount.incrementAndGet());
        }
    }

    @Override
    public void close()
    {

    }

    @Override
    public void configure(Map<String, ?> map)
    {

    }
}
