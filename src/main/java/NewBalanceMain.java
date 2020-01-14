import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewBalanceMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties());
        String value = "310135,58864789570,500";
        Callback callback =  (data, ex ) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println(data.topic() + " ::: partition " + data.partition() + "/ offset " + data.offset() + "/ time " + data.timestamp());
        };
        String email = "guichafy@gmail.com";

        ProducerRecord<String, String> notification = new ProducerRecord<String, String>("BANK_NEW_BALANCE",value, value);
        producer.send(notification, callback).get();
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("BANK_NEW_EMAIL",email, email);
        producer.send(record, callback).get();

    }

    private static Properties properties() {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
    }
}
