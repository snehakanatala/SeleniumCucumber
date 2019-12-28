package com.cucumber.framework.configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author kanatala
 *
 * loads login credentials and configuration details from properties file
 */
public class PropertiesReader {

	private Properties properties;
	private String propertyFilePath = "src\\main\\resources\\properties\\myStore.properties"; 
	private BufferedReader reader;
	
	public Properties loadProperties() {
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		return properties;
	}
}
