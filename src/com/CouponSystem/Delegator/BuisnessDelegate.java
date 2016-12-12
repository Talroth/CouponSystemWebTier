package com.CouponSystem.Delegator;

import javax.naming.*;
import javax.rmi.PortableRemoteObject;

import java.util.Hashtable;

import javax.ejb.EJB;
import javax.jms.JMSException;
import com.CouponSystem.Beans.Income;
import com.CouponSystem.EJB.IncomeService;
import com.CouponSystem.EJB.IncomeServiceBean;

public class BuisnessDelegate {
	
	//@EJB(lookup="java:module/IncomeServiceBean!com.CouponSystem.EJB.IncomeService")
	//@EJB(name="IncomeBean")
	//private IncomeService incomeProcess;
	
	public synchronized void storeIncome(Income income)
	{
		JmsManager manager = new JmsManager();
		
		try {
			manager.init(JmsManager.getInitialContext(), JmsManager.JMS_QUEUE_JNDI);
			
			manager.send(income, 1);
			
			manager.close();
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
//        try {
//            // Create a ConnectionFactory
//            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
//
//            // Create a Connection
//            Connection connection = connectionFactory.createConnection();
//            connection.start();
//
//            // Create a Session
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//            // Create the destination (Topic or Queue)
//            Destination destination = session.createQueue("TEST.FOO");
//
//            // Create a MessageProducer from the Session to the Topic or Queue
//            MessageProducer producer = session.createProducer(destination);
//            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//
//            // Create a messages
//            String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
//            TextMessage message = session.createTextMessage(text);
//
//            // Tell the producer to send the message
//            System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
//            producer.send(message);
//
//            // Clean up
//            session.close();
//            connection.close();
//        }
//        catch (Exception e) {
//            System.out.println("Caught: " + e);
//            e.printStackTrace();
//        }
    }
	
//		try 
//		{
//			System.out.println("Store item in Delegator");
//			//Context ctx = new InitialContext();
//			
//			InitialContext ctx = getInitialContext();
//			
//			QueueConnectionFactory qconFactory = (QueueConnectionFactory) ctx.lookup("java:/jms/queue/ExpiryQueue");
//			Queue messageQueue = (Queue) ctx.lookup("java:/jms/queue/ExpiryQueue");
//
//			QueueConnection qCon = qconFactory.createQueueConnection();
//			QueueSession session = qCon.createQueueSession(
//					   false, /* not a transacted session */
//					   Session.AUTO_ACKNOWLEDGE 
//					  );
//
//			QueueSender sender = session.createSender(messageQueue);
//			sender.send((Message) income);
//		} 
//		catch (JMSException | NamingException e) 
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//	}
	
//	  public  InitialContext getInitialContext(){
//			Hashtable<String,String> h=new Hashtable<String,String>();
//			h.put("org.jboss.naming.remote.client.InitialContextFactory","org.jboss.naming.remote.client.InitialContextFactory");
//			h.put("java.naming.provider.url","localhost");
//			try {
//				return new InitialContext(h);
//			} catch (NamingException e) {
//				System.out.println("Cannot generate InitialContext");
//				e.printStackTrace();
//			}
//			return null;
//	   }
	
	
	public synchronized void viewIncomeByCompany(long companyId)
	{

	}
	
	public synchronized void viewAllIncomes()
	{
		InitialContext ctx=getInitialContext();
		Object ref = null;
		try {
	        // Create a look up string name
			String appName = "";
			String moduleName = "CouponSystemWebTier";
			String distname = "";
			String beanName = IncomeServiceBean.class.getSimpleName();
			String interfaceName = IncomeService.class.getName();
			
	        String name = "ejb:" + appName + "/" + moduleName + "/" + 
	        		distname    + "/" + beanName + "!" + interfaceName;
			ref = ctx.lookup(name);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		IncomeService stub=(IncomeService)PortableRemoteObject.narrow(ref, IncomeService.class);
		

		stub.viewAllIncomes();
		
	//	System.out.println(incomeProcess.viewAllIncomes()); 
	}
	
	public synchronized  void viewIncomeByCustomer(long customerId)
	{
		
	}

	public static InitialContext getInitialContext(){
		Hashtable<String,String> h=new Hashtable<String,String>();
		h.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		
		try {
			return new InitialContext(h);
		} catch (NamingException e) {
			System.out.println("Cannot generate InitialContext");
			e.printStackTrace();
		}
		return null;
	}
}
