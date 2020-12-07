package com.projects.testcases;

import java.text.ParseException;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.projects.base.BaseTest;
import com.projects.reports.Extentreports;

import dataprovider.TestDataProvider;

public class EditandsaveComputer extends BaseTest{

	@BeforeMethod
	public void init_report() {
		reports = Extentreports.getReports();
		test = reports.createTest("EditandsaveComputer");
	}
	@Test(priority=1, dataProviderClass = TestDataProvider.class,dataProvider = "getData")
	public void EditComputerDetails(String computername, String intro_dt, String discontnued_dt, String companyname) throws ParseException{
		reportInfo("EditComputerDetails started");
		type("search_xpath",computername);
		click("filterbtn_xpath");
		
		VerifyRecordIntable("table_xpath",computername,intro_dt,discontnued_dt,companyname);
		//click(computername);
		driver.findElement(By.linkText(computername)).click();
		verifyTitle("Editpage");	
	}
	
	@Test(priority=2, dataProviderClass = TestDataProvider.class,dataProvider = "getData")
	public void EditdataAndSave(String nameedit,String companyname){
		reportInfo("EditdataAndSave started");
		type("name_xpath",nameedit);
		select("companyname_name",companyname);
		
		click("savebtn_xpath");
		verifymessage("msg_xpath",prop.getProperty("addmessage")+" "+nameedit+" has been updated");
	}
	
	@Test(priority=3)
	public void verify(){
		reportInfo("EditdataAndSave started");
		String title_msg = verifyTitle("mainpage");
		String rec_after = getrecord(title_msg);	
		int records =Integer.parseInt(rec_after);
		if(records== Integer.parseInt(records_before)) {
			reportPass("1 record has been updated. Total Records :"+records);
		}
		
	}

}
