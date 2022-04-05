import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;

import java.util.List;

public class SqsConsumer {

    private final static String urlQueue = "http://localhost:4566/000000000000/minha-primeira-queue";

    public static void main(String[] args) throws InterruptedException {

        AmazonSQSClientBuilder standard = AmazonSQSClientBuilder.standard();
        standard.setCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("123", "123")));
        standard.setEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-1"));
        AmazonSQS sqs = standard.build();

        System.out.println("Iniciando o consumo...");
        while (true) {
            List<Message> messages = sqs.receiveMessage(urlQueue).getMessages();
            messages.forEach(m -> {
                System.out.println(m);
                sqs.deleteMessage(urlQueue, m.getReceiptHandle());
            });
            Thread.sleep(10000);
        }

    }
}
