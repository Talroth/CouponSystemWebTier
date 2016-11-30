package com.CouponSystem.Delegator;

import java.util.Collection;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.*;

import com.CouponSystem.Beans.Income;

public class BuisnessDelegate {
	
	public synchronized void storeIncome(Income income)
	{
		InitialContext ctx = getInitialContext();
		QueueConnectionFactory qconFactory = (QueueConnectionFactory) ctx.lookup("HelloWorldQueueMDB");
		Queue messageQueue = (Queue) ctx.lookup("HelloWorldQueueMDB");
		QueueConnection  qCon = qconFactory.createQueueConnection();
		QueueSession session = qCon.createQueueSession(
				   false, /* not a transacted session */
				   Session.AUTO_ACKNOWLEDGE 
				  );
		 
		 QueueSender sender = session.createSender(messageQueue);
		 sender.send((Message) income);
	}
	
	
	public synchronized void viewIncomeByCompany(long companyId)
	{

	}
	
	public synchronized void viewAllIncomes()
	{
		
	}
	
	public synchronized  void viewIncomeByCustomer(long customerId)
	{
		
	}

}
