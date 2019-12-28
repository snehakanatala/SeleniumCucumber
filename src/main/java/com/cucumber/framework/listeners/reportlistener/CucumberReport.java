package com.cucumber.framework.listeners.reportlistener;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

/**
 * 
 * @author kanatala
 *
 */
public class CucumberReport implements ISuiteListener {
	
	public void onStart(ISuite suite) {
		
	}

	public void onFinish(ISuite suite) {
		try {
			
			File jsonfile = new File("target/");
			File reportOutputDirectory = new File("target/test-classes/reports/cucumberreports/");
			
			String[] fileNames = jsonfile.list(new FilenameFilter() {
				
				public boolean accept(File dir, String name) {
					if(name.endsWith(".json"))
						return true;
					return false;
				}
			});
			
			for (int i = 0; i < fileNames.length; i++) {
				fileNames[i] = jsonfile.getAbsolutePath() + "/" + fileNames[i];
			}
			
			List<String> jsonFiles = Arrays.asList(fileNames);

			Configuration configuration = new Configuration(reportOutputDirectory, suite.getName());
			configuration.setStatusFlags(true, true, true);

			ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
			reportBuilder.generateReports();
			System.out.println("Report Generated : " + configuration.getReportDirectory());

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
