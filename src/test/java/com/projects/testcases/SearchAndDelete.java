package com.projects.testcases;

import java.text.ParseException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.projects.base.BaseTest;
import com.projects.reports.Extentreports;

import dataprovider.TestDataProvider;

public class SearchAndDelete extends BaseTest{
	//WebDriverWait wait = new WebDriverWait(driver, 10);
	@BeforeMethod
	public void init_report() {
		reports = Extentreports.getReports();
		test = reports.createTest("SearchAndDelete");
	}
	@Test(priority=2,dataProviderClass = TestDataProvider.class,dataProvider = "getData")
	public void searchandopen(String searchdata,String introduced_dt,String discontnued_dt,String companyname ) throws ParseException{
		reportInfo("searchandopen started");
		type("search_xpath",searchdata);
		click("filterbtn_xpath");
		String pageheader = verifyTitle("Noitem");
		System.out.println(pageheader);
		if(pageheader.equals("No computers found")) {
			reportFailure("No computers found");
		
				//recheck-click on itemlink
			//click(searchdata);
		}else {
			VerifyRecordIntable("table_xpath",searchdata,introduced_dt,discontnued_dt,companyname);
		
		//wait.until((ExpectedConditions.presenceOfElementLocated(By.linkText(searchdata))));
		try {
			driver.findElement(By.linkText(searchdata)).click();
		} catch (WebDriverException e) {
			//throw new AssertionError("element not found" ,e);
			System.out.println("element not found exception :"+e);
		}
		
		verifyTitle("Editpage");	
		}
	}
	
	@Test(priority=3,dependsOnMethods = {"searchandopen"})
	public void deleteandverifymsg(){
		reportInfo("deleteandverifymsg started");
		
		if(!isElementPresent("delbtn_xpath"))
			reportFailure("delete button not present");
		else
			click("delbtn_xpath");
			verifymessage("msg_xpath",prop.getProperty("addmessage")+" has been deleted");
	}
	
	@Test(priority=4,dependsOnMethods = {"searchandopen","deleteandverifymsg"})
	public void verify(){
		reportInfo("verify started");
		String title_msg = verifyTitle("mainpage");
		String rec_after = getrecord(title_msg);
		int records =Integer.parseInt(rec_after);
		if(records== Integer.parseInt(records_before)-1) {
			reportPass("1 record has been deleted. Total Records :"+records);
		}
	}
		
	}
