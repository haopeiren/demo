package hpr.test.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author haopeiren
 * @since 2020/2/3
 */
@Component
public class KafkaProducer
{
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String key, String value)
    {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        kafkaTemplate.send(record);
    }
}
