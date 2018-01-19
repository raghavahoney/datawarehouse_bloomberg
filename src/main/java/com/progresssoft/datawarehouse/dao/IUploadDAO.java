package com.progresssoft.datawarehouse.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.progresssoft.datawarehouse.entity.AccumulativeDeal;
import com.progresssoft.datawarehouse.entity.DealModel;


public interface IUploadDAO {
	
	    public boolean fileExists(String fileName);
	    
	    public <T extends DealModel> Collection<T> saveDeals(Collection<T> entities);
	    
	    public void saveAccumulativeDeals(List<AccumulativeDeal> accumulativeDeals);
	    
	    public Map<String, String> getSummary(String fileName);

}
