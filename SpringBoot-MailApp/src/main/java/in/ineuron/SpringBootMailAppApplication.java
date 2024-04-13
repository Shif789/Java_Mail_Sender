package in.ineuron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import in.ineuron.service.IPurchaseOrder;

@SpringBootApplication
public class SpringBootMailAppApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SpringBootMailAppApplication.class, args);
		
		IPurchaseOrder purchaseOrder = applicationContext.getBean("service", IPurchaseOrder.class);
		
		String[] items=new String[] {"Fossil-Chronography","USPOLO-Tshirt","Nike-Shoes"};
		//String[] items=new String[] {"Sunny-Leone","Jayden-James","Mia-Melon"};
		double[] prices=new double[] {24000.50,12000.00,15000.50};
		String[] emails=new String[] {"Ryan.hasan1997@gmail.com","Walidchowdhury.1997@gmail.com"};
		try {
			String purchaseStatus = purchaseOrder.purchase(items, prices, emails);
			System.out.println(purchaseStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		((ConfigurableApplicationContext) applicationContext).close();
	}

}
