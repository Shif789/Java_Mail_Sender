package in.ineuron.service;

import java.util.Arrays;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service(value = "service")
public class PurchaseOrderImpl implements IPurchaseOrder {

	@Autowired
	private JavaMailSender mailSender;
	
	@Value(value = "${spring.mail.username}")
	private String fromEmail;

	@Override
	public String purchase(String[] items, double[] prices, String[] toEmails) throws Exception {
		double amount = 0;
		for (double price : prices) {
			amount += price;
		}
		String msg = Arrays.toString(items) + " with prices " + Arrays.toString(prices)
				+ " are booked with bill-amount: " + amount;

		String mailStatus = sendMail(msg, toEmails);
		
		return msg+" ------> "+mailStatus;
	}

	private String sendMail(String msg, String[] toEmails) throws MessagingException {
		System.out.println("JavaMailSender Implementation class: "+mailSender.getClass().getName());
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		
		mimeMessageHelper.setFrom(fromEmail);
		mimeMessageHelper.setCc(toEmails);
		mimeMessageHelper.setSubject("Open to know it");
		mimeMessageHelper.setSentDate(new Date());
		mimeMessageHelper.setText(msg);
		mimeMessageHelper.addAttachment("amazon.png", new ClassPathResource("amazon.png"));
//		mimeMessageHelper.addAttachment("amazon1.jpeg", new ClassPathResource("amazon1.jpeg"));
//		mimeMessageHelper.addAttachment("sunny.jpeg", new ClassPathResource("sunny.jpeg"));
//		mimeMessageHelper.addAttachment("mia.jpeg", new ClassPathResource("mia.jpeg"));
		
		mailSender.send(mimeMessage);
		
		
		return "mail-sent";
	}
}
