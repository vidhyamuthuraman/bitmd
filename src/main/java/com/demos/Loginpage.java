package com.demos;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Loginpage {
	
            //DATABASE CONNECTION ESTABLISHMENT DECLARATION 
	public static String dri="com.mysql.cj.jdbc.Driver";
	public static String url="jdbc:mysql://a2nlmysql19plsk.secureserver.net:3306/bitdatadump";
	public static String dbusername="bitstream";
	public static String dbpassword="Trebo@123";
	
public static void main(String args[]) throws InterruptedException, IOException
{
	         
	WebDriver driver=new ChromeDriver();
	driver.get("https://bit-mtd.site/index.html#/home");
	driver.manage().window().maximize();
	Thread.sleep(1000);
	driver.findElement(By.xpath("//a[@class='plr20 bdblue ptb5 radius2']")).click();
	driver.findElement(By.xpath("//input[@placeholder='Please enter your email']")).sendKeys("Sarapra.poy@gmail.com");
	driver.findElement(By.xpath("//input[@placeholder='Please input a password']")).sendKeys("Pwdspp123");
	driver.findElement(By.xpath("//div[@class='mt40 bgblue white tc h48 lh48 radius2 pointer']")).click();
	Thread.sleep(1000);
	driver.findElement(By.linkText("Contracts")).click();
	Thread.sleep(3000);
	driver.findElement(By.xpath("//input[@class='search_box']")).sendKeys("QCH");
	Thread.sleep(3000);

	String symbol="QCH";
	String sign="positive";
	driver.findElement(By.xpath("//*[contains(text(),\""+symbol+"\")]")).click();
	Thread.sleep(3000);
	List<WebElement> leverage=driver.findElements(By.xpath("//input[@placeholder='Adjust Leverage']"));
	Thread.sleep(3000);
	List<WebElement> l1=driver.findElements(By.xpath("//button[@class='button-quantity']"));
	if(sign.equals("positive"))
	{
		WebElement pos=leverage.get(0);
		pos.click();
		Thread.sleep(10000);
		List<WebElement>dropdownlever=driver.findElements(By.xpath("//span[text()='25']"));
		System.out.println(dropdownlever.get(0));
		WebElement levervalue=dropdownlever.get(1);
		levervalue.click();
		WebElement pospercentage=l1.get(3);
		pospercentage.click();
		driver.findElement(By.xpath("//div[@class='bggreen tc ht30 lh30 white radius2']")).click();
		
	}
	else
	{
		WebElement neg=leverage.get(1);
		neg.click();
		WebElement negpercentage=l1.get(7);
		negpercentage.click();
		
	}
}
}


	
		//driver.switchTo().frame(driver.findElement(By.xpath("//div [@class='el-select-dropdown__wrap el-scrollbar__wrap el-scrollbar__wrap--hidden-default'][5]")));
	/*WebElement element = new WebDriverWait(driver, Duration.ofSeconds(30))
            .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='25']")));

     element.click();*/
	

   
	
	
	
	
	
	//OPENING WEBPAGE
	/*WebDriver driver=new ChromeDriver();
	driver.get("https://bit-mtd.site/index.html#/home");
	Thread.sleep(1000);
	
	            //ACCESS EXCEL AND RETREIVE USSERNAME AND PASSWORD FROM EXCEL
	File f=new File("C:\\Users\\ctvla\\OneDrive\\Documents\\bit-mtd\\bit-mtd-login.xlsx");
	FileInputStream fis=new FileInputStream(f);
	XSSFWorkbook workbook=new XSSFWorkbook(fis);
	XSSFSheet sheet=workbook.getSheet("sheet1");
	XSSFCell username,password;
	int totalrows=sheet.getPhysicalNumberOfRows();
	Row rowcell=sheet.getRow(0);
	int totalcolumns=rowcell.getPhysicalNumberOfCells();
//INPUT USERNAME AND PASSWORD  AND LOGIN INTO GIVEN WEBSITE
	
	for(int i=0;i<=totalrows;i++)
	{
	username=sheet.getRow(i).getCell(0);
	password=sheet.getRow(i).getCell(1);	
	driver.findElement(By.xpath("//a[@class='plr20 bdblue ptb5 radius2']")).click();
	driver.findElement(By.xpath("//input[@placeholder='Please enter your email']")).sendKeys(username.toString());
	driver.findElement(By.xpath("//input[@placeholder='Please input a password']")).sendKeys(password.toString());
	driver.findElement(By.xpath("//div[@class='mt40 bgblue white tc h48 lh48 radius2 pointer']")).click();
	Thread.sleep(1000);

	//CLICK CONTRACT 
driver.findElement(By.xpath("//a[@class='mr25'][2]")).click();
//driver.findElement(By.xpath("//input[@class='search_box']")).sendKeys("BTC");
//driver.findElement(By.xpath("//input[@class='search_box']")).click();
	
	//ESTABLISHING CONNECTION MYSQL DATABASE

Connection conn=null;

try
{
	Class.forName(dri);
	conn=DriverManager.getConnection(url, dbusername, dbpassword);
	Statement st=conn.createStatement();
	System.out.println("connection established");
	//String query="SELECT from_value_api_hit_timestamp,to_value_api_hit_timestamp,base_currency,quote_currency,current_close,past_1min_close,delta,delta_percentage,sign FROM crypto_data_test_delta_v1 order by id desc\r\n";
  String query="SELECT \r\n"
  		+ "  from_value_api_hit_timestamp,\r\n"
  		+ "  to_value_api_hit_timestamp,\r\n"
  		+ "  base_currency,\r\n"
  		+ "  quote_currency,\r\n"
  		+ "  current_close,\r\n"
  		+ "  past_1min_close,\r\n"
  		+ "  delta,\r\n"
  		+ "  delta_percentage,\r\n"
  		+ "  sign,\r\n"
  		+ "  TIMESTAMPDIFF(SECOND, from_value_api_hit_timestamp, to_value_api_hit_timestamp) AS time_difference \r\n"
  		+ "FROM \r\n"
  		+ "  crypto_data_test_delta_v1 \r\n"
  		+ "WHERE \r\n"
  		+ "  TIMESTAMPDIFF(SECOND, from_value_api_hit_timestamp, to_value_api_hit_timestamp) < 120 \r\n"
  		+ "  order by id desc limit 5";		
 // System.out.println(query);
	
  ResultSet rs=st.executeQuery(query);
	
	while(rs.next()){  
		
		System.out.println(rs.getString(1)+ " " +rs.getString(2) + " "+rs.getString(3)+ " "+rs.getString(4)+ " "+rs.getString(5)+ " "+rs.getString(6)+ " "+rs.getString(7)+ " "+rs.getString(8)+ " "+rs.getString(9));
	}
	conn.close();	
}
catch(SQLException e)
{e.printStackTrace();
	//System.out.println("error");
	
}
catch(Exception e)
{
	e.printStackTrace();
}


		//positive
		//int timer=45;
		/* 	
     	List<WebElement>posthreshold=driver.findElements(By.xpath("//span[@class='green']"));
     	if(posthreshold.isEmpty())
     	{

     		//System.out.println("No positive data");
     	}
     	else
     	{
     	WebElement pthreshold=posthreshold.get(0);
     	System.out.println(pthreshold.getText());
     	if(pthreshold.getText()!=null&&!pthreshold.getText().trim().isEmpty())
     	{
     	double threshold = Double.parseDouble(pthreshold.getText().replace("%",""));
     	System.out.println(threshold);
     	//double threshold=Double.parseDouble(pthreshold.getText());
     	//System.out.println(threshold);
    	if(threshold>0.2)
    	{
     	driver.findElement(By.xpath("//span[@class='pointer ml25']")).click();
     	System.out.println("Postive Threshold");

        }
      else

     	{
     		Thread.sleep(4500);
     		driver.findElement(By.xpath("//span[@class='pointer ml25']")).click();
            System.out.println("Timer");


     	}
     	}
     	}

     	List<WebElement>negthreshold=driver.findElements(By.xpath("//span[@class='red']"));

     	if(negthreshold.isEmpty())
     	{
     		//System.out.println("No negative data");
     	}
     	else
     	{
     	WebElement nthreshold=negthreshold.get(0);
     	double threshold = Double.parseDouble(nthreshold.getText().replace("%",""));
     	System.out.println(threshold);
     	//double threshold=Double.parseDouble(nthreshold.getText());
     	if(nthreshold.getText()!=null&&!nthreshold.getText().trim().isEmpty())
     	{
     	System.out.println(threshold);
     	if(threshold<0.2)
     	{
        driver.findElement(By.xpath("//span[@class='pointer ml25']")).click();
        System.out.println("negative Threshold");

     	}
     	else

     	{
     		Thread.sleep(45000);
     		driver.findElement(By.xpath("//span[@class='pointer ml25']")).click();
            System.out.println("Timer");

     	}
     	}

     	}*/



	



	

	

