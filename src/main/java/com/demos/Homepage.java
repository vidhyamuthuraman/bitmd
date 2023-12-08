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
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Homepage {
	public static String dri="com.mysql.cj.jdbc.Driver";
	public static String url="jdbc:mysql://a2nlmysql19plsk.secureserver.net:3306/bitdatadump";
	public static String dbusername="bitstream";
	public static String dbpassword="Trebo@123";

	public void openbrowser(String symbol,String sign) throws InterruptedException, IOException
	{
		
		File f=new File("C:\\Users\\ctvla\\OneDrive\\Documents\\bit-mtd\\bit-mtd-login.xlsx");
		FileInputStream fis=new FileInputStream(f);
		XSSFWorkbook workbook=new XSSFWorkbook(fis);
		XSSFSheet sheet=workbook.getSheet("sheet1");
		//XSSFCell username,password,negthreshold,posthreshold,transtimer;
		int totalrows=sheet.getPhysicalNumberOfRows();
		Row rowcell=sheet.getRow(0);
		Cell username=rowcell.getCell(0);
		Cell password=rowcell.getCell(1);
		Cell negthresholdcell=rowcell.getCell(2);
		Cell posthresholdcell=rowcell.getCell(3);
		Cell trans_timer_cell=rowcell.getCell(4);
		double negthreshold=negthresholdcell.getNumericCellValue();
		double posthreshold=posthresholdcell.getNumericCellValue();
		double trans_timer=trans_timer_cell.getNumericCellValue();
		//int totalcolumns=rowcell.getPhysicalNumberOfCells();
		 //username=sheet.getRow(0).getCell(0);
		//password=sheet.getRow(0).getCell(1);
		//negthreshold=sheet.getRow(0).getCell(2).getNumericCellValue();
		
		System.out.println(username+ " "+ password+ " "+negthreshold);
		System.out.println(posthreshold+ " "+ trans_timer);
		
		
		
		WebDriver driver=new ChromeDriver();
		
		driver.get("https://bit-mtd.site/index.html#/home");
		driver.manage().window().maximize();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@class='plr20 bdblue ptb5 radius2']")).click();
		driver.findElement(By.xpath("//input[@placeholder='Please enter your email']")).sendKeys(username.toString());
		driver.findElement(By.xpath("//input[@placeholder='Please input a password']")).sendKeys(password.toString());
		driver.findElement(By.xpath("//div[@class='mt40 bgblue white tc h48 lh48 radius2 pointer']")).click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("Contracts")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@class='search_box']")).sendKeys(symbol);
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[contains(text(),\""+symbol+"\")]")).click();
		Thread.sleep(4000);
		List<WebElement> leverage=driver.findElements(By.xpath("//input[@placeholder='Adjust Leverage']"));
		Thread.sleep(3000);
		List<WebElement> l1=driver.findElements(By.xpath("//button[@class='button-quantity']"));

		if(sign.equals("positive"))
		{
			WebElement pos=leverage.get(0);
			pos.click();
			Thread.sleep(1000);
			List<WebElement>dropdownlever=driver.findElements(By.xpath("//span[text()='25']"));
			//for 50get(3),change span value
			WebElement levervalue=dropdownlever.get(1);
			levervalue.click();
			Thread.sleep(2000);
			WebElement pospercentage=l1.get(3);
			pospercentage.click();
			driver.findElement(By.xpath("//div[@class='bggreen tc ht30 lh30 white radius2']")).click();


		}
		else
		{
			WebElement neg=leverage.get(1);
			neg.click();
			List<WebElement>dropdownlever=driver.findElements(By.xpath("//span[text()='25']"));
			WebElement levervalue=dropdownlever.get(1);
			levervalue.click();
			Thread.sleep(2000);
			WebElement negpercentage=l1.get(7);
			negpercentage.click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[@class='bgred tc ht30 lh30 white radius2']")).click();

		}
		Thread.sleep(2000);
		//long timer=3000;
		long starttime=System.currentTimeMillis();
		while(System.currentTimeMillis()-starttime<trans_timer)
		{
			WebElement risk=driver.findElement(By.xpath("//p"));
			System.out.println(risk.getText());
			String inputText =risk.getText();
			// Define a regular expression to match the number
			String regex = "\\d+\\.\\d+";
			// Create a Pattern object
			Pattern pattern = Pattern.compile(regex);

			// Create a Matcher object
			Matcher matcher = pattern.matcher(inputText);
			double number=0;
			// Find the first match
			if (matcher.find()) {
				// Extract the matched number
				String extractedNumber = matcher.group();

				// Convert the extracted number to a double
				number = Double.parseDouble(extractedNumber);
				System.out.println(number);


			}
			if(number<negthreshold)
			{
				driver.findElement(By.xpath("//span[@class='pointer ml25']")).click();
				System.out.println("Order closed after Negative Threshold");
				Thread.sleep(4000);
				break;
			}
			if(number>posthreshold)
			{
				driver.findElement(By.xpath("//span[@class='pointer ml25']")).click();
				System.out.println(" Order closed Positve Threshold");
				Thread.sleep(4000);
				break;
			}}

		Thread.sleep(2000);
		By locator = By.xpath("//span[@class=\"pointer ml25\"]");
		boolean isElementPresent = !driver.findElements(locator).isEmpty();

		if (isElementPresent) {
			//System.out.println("Close is visible");
			driver.findElement(By.xpath("//span[@class=\"pointer ml25\"]")).click();
			System.out.println("Order closed after the time interval");
			Thread.sleep(4000);

		} else {
			System.out.println("Close is not visible");
		}

		driver.close();	
	}

	public static void main(String args[]) throws ClassNotFoundException, InterruptedException, IOException, SQLException
	{
		Homepage h=new Homepage();

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
		
		while(true)
		{
			Connection conn=null;

			try
			{
				Class.forName(dri);
				conn=DriverManager.getConnection(url, dbusername, dbpassword);
				Statement st=conn.createStatement();
				System.out.println("connection established");

				ResultSet rs=st.executeQuery(query);
				boolean hasresult=rs.next();
				System.out.println(hasresult);
				if(hasresult)
				{
					while(rs.next()){  

						System.out.println(rs.getString(3)+ " "+rs.getString(9));

						String symbol=rs.getString(3);
						String sign=rs.getString(9);
						h.openbrowser(symbol,sign);

						Thread.sleep(10000);
						//create a function and call that with the above parameters

					}
				}
				else
				{
					Thread.sleep(1000);
					System.out.println("No data");
				}
				conn.close();
				Thread.sleep(4000);
				break;

			}
			catch(SQLException e){
				e.printStackTrace();
				System.out.println("error");
			}


		}
	}
}