package snmaddula.random.poc.app.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Service
public class QueueService {

	@RabbitListener(bindings = @QueueBinding(value = @Queue(), exchange = @Exchange(value = "messages", type = ExchangeTypes.FANOUT)))
	public void consumerMessage(byte[] data) {
		String consumedMessage = new String(data);
		System.out.println(" [x] Consumed  '" + consumedMessage + "'");
	}

	public void produceMessage(String message) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.exchangeDeclare("messages", "fanout");

			channel.basicPublish("messages", "", null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");

			channel.close();
			connection.close();
		} catch (IOException io) {
			System.out.println("IOException");
			io.printStackTrace();
		} catch (TimeoutException toe) {
			System.out.println("TimeoutException : " + toe.getMessage());
			toe.printStackTrace();
		}
	}
}
