package com.progresssoft.datawarehouse.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progresssoft.datawarehouse.business.IUploadService;
import com.progresssoft.datawarehouse.dao.IUploadDAO;
import com.progresssoft.datawarehouse.entity.AccumulativeDeal;
import com.progresssoft.datawarehouse.entity.InValidDeal;
import com.progresssoft.datawarehouse.entity.InputRow;
import com.progresssoft.datawarehouse.entity.ValidDeal;




/**
 * Service class for file upload 
 *
 * @author Raghavendra
 */

@Service
public class UploadService implements IUploadService {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadService.class);
	
	@Autowired
	private IUploadDAO dao;

	
	
	@Override
	public Map<String, String> getSummary(String fileName) {
		return dao.getSummary(fileName);
	}
	

	/**
	 *
	 * @param dealDetails List of deal details
	 * 
	 */
	@Override
	public void saveValidData(List<InputRow> dealDetails) {
		logger.info("saveValidData()  -- Start"+ dealDetails.size());
		List<ValidDeal> validDeals = new ArrayList<>();
		Map<String, Integer> accumulativeValues = new HashMap<String,Integer>(); 
		for(InputRow deal:dealDetails){
			ValidDeal target = new ValidDeal();
			if(accumulativeValues.containsKey(deal.getFromCurrency())){
				int value = Integer.parseInt(String.valueOf((accumulativeValues.get(deal.getFromCurrency()))));
				accumulativeValues.put(deal.getFromCurrency(), ++value);
			}
			else{
				accumulativeValues.put(deal.getFromCurrency(), 1);
			}
			BeanUtils.copyProperties(deal, target);
			validDeals.add(target);
		}
		List<AccumulativeDeal> accumulativeDeals = new ArrayList<>();
		for (Object key : accumulativeValues.keySet()) {
			AccumulativeDeal accumulativeDeal = new AccumulativeDeal();
			accumulativeDeal.setCount(new BigInteger(String.valueOf(accumulativeValues.get(key))));
			accumulativeDeal.setOrderingCurrency(key.toString());
			accumulativeDeals.add(accumulativeDeal);
		   // System.out.println("Key = " + key + " - " + accumulativeValues.get(key));
		}
		
		dao.saveAccumulativeDeals(accumulativeDeals);
		dao.saveDeals(validDeals);
		logger.info("saveValidData()  -- End");

	}

	/**
	 * @param dealDetails List of deal details
	 * 
	 */
	@Override
	public void saveInValidData(List<InputRow> dealDetails) {

		logger.info("saveInValidData() -- Start "+ dealDetails.size());
		List<InValidDeal> inValidDeals = new ArrayList<>();
		
		for(InputRow deal:dealDetails){
			InValidDeal target = new InValidDeal();
			BeanUtils.copyProperties(deal, target);
			inValidDeals.add(target);
		}
		dao.saveDeals(inValidDeals);
		logger.info("saveInValidData() -- End ");

	}

	/**
	 * Method to check if file already uploaded
	 *
	 * @param fileName 
	 * @return true or false
	 */
	@Override
	public boolean checkFileExist(String fileName) {
		logger.info("Stepped into the checkFileExist() method file name: "+ fileName);
		
		return dao.fileExists(fileName);
	}

}
