package hpr.test.kafka;

import hpr.test.kafka.producer.KafkaProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haopeiren
 * @since 2020/2/3
 */
@SpringBootApplication
@RestController
public class Application
{
    @Autowired
    private KafkaProducer producer;

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/send")
    public void send()
    {
        producer.send("firstTopic", "name", "zhangsan");
    }

    @KafkaListener(topics = "firstTopic")
    public void consume(ConsumerRecord<String, String> record)
    {
        System.out.println(record.topic());
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.offset());
        System.out.println(record.partition());
        System.out.println(record.timestamp());
        System.out.println("--------------");
    }

}
