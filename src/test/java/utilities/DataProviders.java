package utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	//data provider 1
	
	@DataProvider(name="LoginData")
	public String [][] getData() throws Exception{
		String path=".\\testData\\OPencart_LoginData.xlsx"; //taking xl file from testData
		
		ExcelUtility xlutil=new ExcelUtility(path);
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);
		
		String logindata[][]=new String[totalrows][totalcols]; //created two dimension array which can store the data
		
		for(int i=1;i<=totalrows;i++) { //1  //read the data from xl storing in 2da array
			for(int j=0;j<totalcols;j++) {  //0  // i is rows j is col
				logindata[i-1][j]=xlutil.getCellData("Sheet1", i, j); //1,0
			}
		}
		
		return logindata; //returning 2d array
		
	}
	 
	
	//data provider 2
	
	//data provider 3
	
	//data provider 4
}
