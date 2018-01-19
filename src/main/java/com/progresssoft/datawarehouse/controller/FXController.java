package com.progresssoft.datawarehouse.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.progresssoft.datawarehouse.entity.InputRow;
import com.progresssoft.datawarehouse.impl.UploadService;

/**
 * Controller class for Upload
 */

@Controller
public class FXController {
	
    private final Logger logger = LoggerFactory.getLogger(FXController.class);
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	UploadService service ;
	
	@Autowired
    private MessageSource messageSource;
	
    @GetMapping("/*")
	public String redirect() {
    	//default Mapping
		logger.info("Redirecting request to fileUpload");
		return "Upload";
	}
    
    
    
    @PostMapping(value="/getSummary")
    public String getSummary(
    	ModelMap model,
    	@RequestParam("fileName") String fileName,
    	HttpServletRequest request) {
    	String METHOD_NAME = "getSummary";
    	
    	Map<String, String> results = service.getSummary(fileName);
    	
    	Map<String, String> messages = new HashMap<String, String>();

    	
        messages.put("success", "Valid Deals: " +results.get("Valid_Count")+" Invalid Deals: "+results.get("Invalid_Count"));

         model.put("messages", messages);
         
    	return "Upload";
    }
    
    
    @PostMapping(value = "/upload")
    public String uploadFile(
            ModelMap model,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
    	
    	String METHOD_NAME = "uploadFile";
    
    	Map<String, String> messages = new HashMap<String, String>();
    	logger.info(METHOD_NAME + "started");
    	
    	//Validations for Empty and existing file
        if (file.isEmpty()) {
        	messages.put("alert-danger", messageSource.getMessage("empty",null, Locale.getDefault()));
            model.put("messages", messages);
            logger.info("Empty file uploaded");
            return "Upload";
        }
        else if(service.checkFileExist(file.getOriginalFilename())){
        	messages.put("alert-danger", messageSource.getMessage("exists",null, Locale.getDefault()));
            model.put("messages", messages);
        	logger.info("File already uploaded");
        	return "Upload";
        }
        
        //Copy file to server path
        String fileName = file.getOriginalFilename();
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        File dir = new File(rootPath + File.separator + "uploadedfile");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File serverFile = new File(dir.getAbsolutePath() + File.separator + (fileName));
        try {
            try (InputStream is = file.getInputStream();
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
                int i;
                while ((i = is.read()) != -1) {
                    stream.write(i);
                }
                stream.flush();
            }
        } catch (IOException ex) {
            messages.put("alert-danger", messageSource.getMessage("failure",new Object [] {ex}, Locale.getDefault()) );
            model.put("messages", messages);
            logger.error(METHOD_NAME + " crashed ", ex);
            return "Upload";
            
        }
        
     
        try {
               	logger.info("File Processing Start");
            final StopWatch stopWatch = new StopWatch();
            stopWatch.start();

        	 List<InputRow> validDeals = new ArrayList<>();
             List<InputRow> inValidDeals = new ArrayList<>();
             
            for(String[] line:readFile(serverFile, fileName)) {
            	InputRow InputRow = mapData(line);
                	InputRow.setFileName(fileName);
                	
                	if(validRow(InputRow)) {
                		validDeals.add(InputRow);
                	}
                	else {
                		inValidDeals.add(InputRow);
                	}
                	
            }
           
            
            logger.info(Double.valueOf(stopWatch.getTime()) / 1000+"Time taken to process csv records");

            if(validDeals.size() > 0)
            	service.saveValidData(validDeals);
            if(inValidDeals.size() > 0)
            	service.saveInValidData(inValidDeals);
            
            logger.info(Double.valueOf(stopWatch.getTime()) / 1000+"Time taken to insert csv records");

            stopWatch.stop();
            final double totaltime = Double.valueOf(stopWatch.getTime()) / 1000;

            logger.info("Summary: No.of Valid Deals - {}, No.of Invalid Deals - {}, Total Time - {}s", validDeals.size(), inValidDeals.size(), totaltime);
            messages.put("info", messageSource.getMessage("summary",new Object [] {validDeals.size(), inValidDeals.size(), totaltime}, Locale.getDefault()) );


        } catch (Exception e) {
        	logger.error(METHOD_NAME + " crashed ", e);
        } 
     
        messages.put("success", messageSource.getMessage("success",new Object [] {file.getOriginalFilename()}, Locale.getDefault()) );

        model.put("messages", messages);
        return "Upload";
    }
    

    /**
     * Validate input data
     * @param line input row
     */
    public boolean validRow(InputRow InputRow) {
    	
    	if(InputRow.getId().length() == 0) {return false;}
    	if(!(InputRow.getToCurrency().matches("[a-zA-Z]+"))) {return false;}
    	if(!(InputRow.getFromCurrency().matches("[a-zA-Z]+"))) {return false;}
    	try {
    		formatter.parse(InputRow.getDealDate());
    	    BigInteger.valueOf(Long.parseLong(InputRow.getAmount()));
    	}
    	catch (ParseException e) {
    		return false;
		}
    	catch (NumberFormatException e) {
    		return false;
		}
		return true;
	}

	/**
     * Read Row Data 
     * @param line The line of CSV file.
     */
    public InputRow mapData(String[] line){
    	InputRow dealModel = new InputRow();
    	dealModel.setId(line[0]);
    	dealModel.setToCurrency(line[1]);
    	dealModel.setFromCurrency(line[2]);
    	dealModel.setDealDate(line[3]);
    	dealModel.setAmount(line[4]);
    	return dealModel;
    }
    
    public List<String[]> readFile(File serverFile, String fileName){
    	List<String[]> lines = null;
    	try {
            	FileReader fileReader = new FileReader(serverFile);
                CSVReader reader = new CSVReader(fileReader, ',');
                lines = reader.readAll();
        } catch (IOException e) {
        	logger.error("File Error", e);
        } 
    	return lines;
    }
    

}