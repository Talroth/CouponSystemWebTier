package com.CouponSystem.Delegator;

import java.util.Properties;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.CouponSystem.Beans.Income;

public class JmsManager {

	  public final static String JMS_CONNECTION_FACTORY_JNDI="jms/RemoteConnectionFactory";
	  public final static String JMS_QUEUE_JNDI="jms/queue/TestQ";
	  public final static String JMS_USERNAME="jmsuser";       //  The role for this user is "guest" in ApplicationRealm
	  public final static String JMS_PASSWORD="jmsuser";  
	  public final static String WILDFLY_REMOTING_URL="http-remoting://localhost:8080";
	 
	  private QueueConnectionFactory qconFactory;
	  private QueueConnection qcon;
	  private QueueSession qsession;
	  private QueueSender qsender;
	  private Queue queue;
	  private TextMessage msg;
	  
	  public void init(Context ctx, String queueName) throws NamingException, JMSException {
		    qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_CONNECTION_FACTORY_JNDI);
		     
		    //  If you won't pass jms credential here then you will get 
		    // [javax.jms.JMSSecurityException: HQ119031: Unable to validate user: null]    
		    qcon = qconFactory.createQueueConnection(JmsManager.JMS_USERNAME, JmsManager.JMS_PASSWORD);   
		     
		    qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		    queue = (Queue) ctx.lookup(queueName);
		    qsender = qsession.createSender(queue);
		    msg = qsession.createTextMessage();
		    qcon.start();
		  }
	  

	  public void send(Income message,int counter) throws JMSException {
	//    msg.setText(message);
	//    msg.setIntProperty("counter", counter);
		ObjectMessage msg = qsession.createObjectMessage();
		msg.setObject(message);
	    qsender.send(msg);
	  }
	 
	  public void close() throws JMSException {
	    qsender.close();
	    qsession.close();
	    qcon.close();
	  }
	  
	  public static InitialContext getInitialContext() throws NamingException {
		     InitialContext context=null;
		     try {
		           Properties props = new Properties();
		           props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		           props.put(Context.PROVIDER_URL, WILDFLY_REMOTING_URL);   // NOTICE: "http-remoting" and port "8080"
		           props.put(Context.SECURITY_PRINCIPAL, JMS_USERNAME);
		           props.put(Context.SECURITY_CREDENTIALS, JMS_PASSWORD);
		        //   props.put("jboss.naming.client.ejb.context", true);
		           context = new InitialContext(props); 
		           System.out.println("\n\tGot initial Context: "+context);     
		      } catch (Exception e) {
		           e.printStackTrace();
		      }
		    return context;
		  }
}
