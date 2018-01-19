package com.progresssoft.datawarehouse.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.progresssoft.datawarehouse.entity.AccumulativeDeal;
import com.progresssoft.datawarehouse.entity.DealModel;



/**
 * DAO for save deals 
 *
 * @author Raghavendra
 */
@Transactional
@Repository
public class UploadDAO implements IUploadDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadDAO.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${hibernate.jdbc.batch_size}")
	private int batchSize;

	/**
	 * This method check the file name exist in Database 
	 *
	 * @param fileName 
	 * @return true or false
	 */
	@Override
	public boolean fileExists(String fileName) {
		Session session = sessionFactory.openSession();
		return ((Long)session.createQuery("select count(*) from ValidDeal where fileName=:fileName").setParameter("fileName", fileName).uniqueResult()).intValue() > 0 ? true : false;
	}

	
	
	/* (non-Javadoc)
	 * @see com.progresssoft.datawarehouse.dao.IUploadDAO#getSummary(java.lang.String)
	 */
	@Override
	public Map<String, String> getSummary(String fileName) {
		Session session = sessionFactory.openSession();
		Map<String, String> returnMap = new HashMap<String,String>();
		int validDealsCount = ((Long)session.createQuery("select count(*) from ValidDeal where fileName=:fileName").setParameter("fileName", fileName).uniqueResult()).intValue();
		int invalidDealsCount = ((Long)session.createQuery("select count(*) from InValidDeal where fileName=:fileName").setParameter("fileName", fileName).uniqueResult()).intValue();

		
		returnMap.put("Valid_Count", String.valueOf(validDealsCount));
		returnMap.put("Invalid_Count", String.valueOf(invalidDealsCount));
		return returnMap;
	}
	/**
	 * Method to save all deals 
	 *
	 */
	@Override
	public <T extends DealModel> Collection<T> saveDeals(Collection<T> entities) {
		logger.info( "saveDeals() - Start");
		final List<T> savedEntities = new ArrayList<T>(entities.size());
		  
		  Session session = sessionFactory.openSession();
		   Transaction tx = session.beginTransaction();
		    
		  int i = 0;
		  for (T t : entities) {
			  session.save(t);
		    i++;
		    if (i % batchSize == 0) {
		    	 session.flush(); 
		         session.clear(); 
		    }
		  }

		   tx.commit();
		   session.close();
			logger.info( "saveDeals() - End");
		  return savedEntities;
	}

	@Override
	public void saveAccumulativeDeals(List<AccumulativeDeal> accumulativeDeals) {
		 Session session = sessionFactory.openSession();
		    Transaction tx = session.beginTransaction();
		    Iterator<AccumulativeDeal> it = accumulativeDeals.iterator();
		    int i = 0;
		    while(it.hasNext()){ 
		        i++;
		        AccumulativeDeal accumulativeDeal = (AccumulativeDeal)it.next();
		        List<AccumulativeDeal> deals = session.createQuery("from AccumulativeDeal where orderingCurrency=:orderingCurrency")
		        							.setParameter("orderingCurrency", accumulativeDeal.getOrderingCurrency()).list();
		        if(deals.size() > 0){
		        	AccumulativeDeal deal = deals.get(0);
		        	deal.setCount(deal.getCount().add(accumulativeDeal.getCount()));
		        	session.saveOrUpdate(deal);
		        }
		        else{
		        	session.persist(accumulativeDeal);
		        }
		        if (i % batchSize == 0) { session.flush(); session.clear(); }
		    }
		    tx.commit();
			   session.close();		
	}

	
	
}