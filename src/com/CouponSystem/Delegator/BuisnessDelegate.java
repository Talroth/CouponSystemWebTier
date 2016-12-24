package com.CouponSystem.Delegator;

import javax.naming.*;
import javax.rmi.PortableRemoteObject;

import java.util.Collection;
import java.util.Hashtable;

import javax.jms.JMSException;
import com.CouponSystem.Beans.Income;
import com.CouponSystem.EJB.IncomeService;
import com.CouponSystem.EJB.IncomeServiceBean;
import com.CouponSystem.FacadeException.FacadeException;



public class BuisnessDelegate {
	
	private static IncomeService stub=(IncomeService)PortableRemoteObject.narrow(getReference(), IncomeService.class);
	private static BuisnessDelegate me = new BuisnessDelegate();
	
	private BuisnessDelegate()
	{
		
	}
		
	public static BuisnessDelegate getIncomeService()
	{
		return me;
	}
	
	public synchronized void storeIncome(Income income) 
	{
		JmsManager manager = new JmsManager();
		
		try 
		{
			manager.init();
			
			manager.send(income);
			
			manager.close();
			
		} 
		catch (NamingException | JMSException e) 
		{
			// No interaction with client - some log system should use here
		} 
		

    }
	
	
	public synchronized Collection<Income> viewIncomeByCompany(long companyId) throws FacadeException
	{
		//IncomeService stub=(IncomeService)PortableRemoteObject.narrow(getReference(), IncomeService.class);
		try
		{
			return stub.viewIncomeByCompany(companyId);
		}
		catch (Exception e)
		{
			throw new FacadeException("Fail to generate incomes list");
		}
	}
	
	public synchronized Collection<Income> viewAllIncomes() throws FacadeException
	{
	
		//IncomeService stub=(IncomeService)PortableRemoteObject.narrow(getReference(), IncomeService.class);
				
		try
		{
			return stub.viewAllIncomes();
		}
		catch (Exception e)
		{
			throw new FacadeException("Fail to generate incomes list");
		}
		
		
	//	System.out.println(incomeProcess.viewAllIncomes()); 
	}
	
	public synchronized  Collection<Income> viewIncomeByCustomer(long customerId) throws FacadeException
	{
		//IncomeService stub=(IncomeService)PortableRemoteObject.narrow(getReference(), IncomeService.class);
				
		try
		{
			return stub.viewIncomeByCustomer(customerId);
		}
		catch (Exception e)
		{
			throw new FacadeException("Fail to generate incomes list");
		}
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

		} catch (NamingException e) {
			System.out.println("Cannot generate InitialContext");
			e.printStackTrace();
		}
		return null;
	}
}
