package com.progresssoft.datawarehouse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.progresssoft.datawarehouse.controller.FXController;
import com.progresssoft.datawarehouse.entity.InputRow;
import com.progresssoft.datawarehouse.impl.UploadService;



public class FileUploadControllerTest extends Mockito{

    private final Logger logger = LoggerFactory.getLogger(FileUploadControllerTest.class);

    @Autowired
    private WebApplicationContext webApplicationContext;
    
	@Mock
	UploadService service;
	
	@Mock
	MessageSource message;
	
	@InjectMocks
	FXController fileUploadController;
	
	@Spy
	List<InputRow> validDeals = new ArrayList<InputRow>();
	
	@Spy
	List<InputRow> inValidDeals = new ArrayList<InputRow>();

	@Spy
	ModelMap model;
	
	@Mock
	BindingResult result;
	
	String fileName = "test.csv";
	
	File file;
	
    HttpServletRequest request = mock(HttpServletRequest.class);       
    HttpServletResponse response = mock(HttpServletResponse.class);   
	
	@BeforeClass
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		readCSVFile(getFileList());
	}
	
	
	@Test
	public void newFileUpload() throws FileNotFoundException, IOException{
		String METHOD_NAME = "newStorage";
		logger.info(METHOD_NAME+ " started..");
		
        MockMultipartFile multipartFile = new MockMultipartFile(fileName, 
				org.apache.commons.io.IOUtils.toByteArray(new FileInputStream(file)));

		
		 MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	        try {
				mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload")
				                .file(multipartFile))
				            .andExpect(MockMvcResultMatchers.status().is(200))
				            		.andReturn();
			} catch (Exception e) {
				e.printStackTrace();
			}		
	                    		
		Assert.assertNotNull(model.get("documents"));
	}

	
	public List<String[]> getFileList() {
		
		ClassLoader classLoader = getClass().getClassLoader();
		file = new File(classLoader.getResource(fileName).getFile());
		System.out.println(file.getAbsolutePath());
		return fileUploadController.readFile(file, fileName);
		
	}
	
	public void readCSVFile(List<String[]> lines){
		for(String[] line:lines) {
        	InputRow csvRecord = fileUploadController.mapData(line);
        	csvRecord.setFileName(fileName);
        	if(fileUploadController.validRow(csvRecord)) {
        		validDeals.add(csvRecord);
        	}
        	else {
        		inValidDeals.add(csvRecord);
        	}
        
    }
	}
}
