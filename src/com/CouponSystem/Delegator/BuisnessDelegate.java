package com.CouponSystem.Delegator;

import javax.naming.*;
import javax.rmi.PortableRemoteObject;

import java.util.Collection;
import java.util.Hashtable;

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
		

    }
	
	
	public synchronized Collection<Income> viewIncomeByCompany(long companyId)
	{
		IncomeService stub=(IncomeService)PortableRemoteObject.narrow(getReference(), IncomeService.class);
		
		return stub.viewIncomeByCompany(companyId);
	}
	
	public synchronized Collection<Income> viewAllIncomes()
	{
	
		IncomeService stub=(IncomeService)PortableRemoteObject.narrow(getReference(), IncomeService.class);
		
		return stub.viewAllIncomes();
		
	//	System.out.println(incomeProcess.viewAllIncomes()); 
	}
	
	public synchronized  Collection<Income> viewIncomeByCustomer(long customerId)
	{
		IncomeService stub=(IncomeService)PortableRemoteObject.narrow(getReference(), IncomeService.class);
		
		return stub.viewIncomeByCustomer(customerId);
	}

	private static Object getReference(){
		Hashtable<String,String> h=new Hashtable<String,String>();
		h.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		
		try {
	        // Create a look up string name
			String appName = "";
			String moduleName = "CouponSystemWebTier";
			String distname = "";
			String beanName = IncomeServiceBean.class.getSimpleName();
			String interfaceName = IncomeService.class.getName();
	        String name = "ejb:" + appName + "/" + moduleName + "/" + 
	        		distname    + "/" + beanName + "!" + interfaceName;
	        
	        Object ref = new InitialContext(h).lookup(name);
	        return ref;
			//return new InitialContext(h);
		} catch (NamingException e) {
			System.out.println("Cannot generate InitialContext");
			e.printStackTrace();
		}
		return null;
	}
}
