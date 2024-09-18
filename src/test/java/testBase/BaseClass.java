package testBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
		
	
	@Parameters({"os","browser"})
	@BeforeClass(groups = {"Sanity","Regression","Master"})
	public void setup(String os,String br) throws Exception {
		
		//Loading config.properties file
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		
		
		logger=LogManager.getLogger(this.getClass()); //automatically get the log4j2.xml file to this class
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities=new DesiredCapabilities();
			//capabilities.setPlatform(Platform.WIN11);
			//capabilities.setBrowserName("chrome");
			
			//os
			if(os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			}
			else if(os.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			}
			else {
				System.out.println("No matching os");
				return;
			}
			
			//browser
			
			switch (br.toLowerCase()) {
			case "chrome":capabilities.setBrowserName("chrome")	; break;
			case "edge":capabilities.setBrowserName("MicrosoftEdge"); break;
			case "Firefox": capabilities.setBrowserName("firefox"); break;
			default: System.out.println("No matching browser");	return;
			}
			
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
			
		}
		if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
			
			switch(br.toLowerCase()) {
			case "chrome" : driver=new ChromeDriver();break;
			case "edge" : driver=new EdgeDriver();break;
			case "firefox" : driver=new FirefoxDriver();break;
			default : System.out.println("Invalid browser name..."); return;
			}
			
		}
		
		driver.manage().deleteAllCookies(); //delete all the cookies in the website
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//driver.get("https://tutorialsninja.com/demo/");
		driver.get(p.getProperty("appURL")); //reading URL from properties file
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups = {"Sanity","Regrrssion","Master"})
	public void tearDown() {
		driver.quit();
	}
	
	public String randomeString() {
		String generatedstring=RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
	}
	
	public String randomeNumber() {
		String generatedNumber=RandomStringUtils.randomAlphanumeric(10);
		return generatedNumber;
	}
	
	public String randomeAlphaNumberic() {
		String generatedNumber=RandomStringUtils.randomAlphanumeric(3);
		String generatedstring=RandomStringUtils.randomAlphabetic(3);
		return (generatedNumber+"@"+generatedstring);
	}
	
	public String captureScreen(String tname) throws Exception{
		
		String timeStamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takeScreenshoot=(TakesScreenshot)driver;
		File sourcefile=takeScreenshoot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\"+ tname + "_" + timeStamp + ".png";
		File tagetFile=new File(targetFilePath);
		
		sourcefile.renameTo(tagetFile);
		
		return targetFilePath;
	}
}
