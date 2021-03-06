import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class ConsumerBalance {


    public static void main(String[] args) {
                   KafkaConsumer<String, String> consumer =   new KafkaConsumer<String,String>(properties());
            consumer.subscribe(Collections.singletonList("BANK_NEW_BALANCE"));
            while(true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                if (!records.isEmpty()) {
                    System.out.println("Found " + records.count());
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println("---------------------");
                        System.out.println("Processing new balance");
                        System.out.println(record.key());
                        System.out.println(record.value());
                        System.out.println(record.partition());
                        System.out.println(record.offset());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Status: Balance Processed");
                        System.out.println("---------------------");
                    }
                    continue;
                }

        }
    }

    private static Properties properties() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, ConsumerBalance.class.getSimpleName());
        return props;
    }
}
