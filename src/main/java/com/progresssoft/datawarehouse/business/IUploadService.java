package com.progresssoft.datawarehouse.business;

import java.util.List;
import java.util.Map;

import com.progresssoft.datawarehouse.entity.InputRow;

/**
 * @author Raghavendra
 *
 */
public interface IUploadService {

	void saveValidData(List<InputRow> dealDetails);
	
	void saveInValidData(List<InputRow> dealDetails);
	
	boolean checkFileExist(String fileName);
	
	Map<String, String> getSummary(String fileName);

}
