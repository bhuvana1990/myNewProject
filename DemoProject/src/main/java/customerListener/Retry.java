package customerListener;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Retry implements IRetryAnalyzer{

	public static final Logger log = Logger.getLogger(Retry.class.getName());
	int min_count = 0;
	int max_count = 2;
	
	public boolean retry(ITestResult arg0) {
		
		if(min_count < max_count) {
			log("retrying Test "+ arg0.getName()+" with status "+
		 			getResultStatusName(arg0.getStatus())+" for the "+
		 			(min_count+1)+"times");
		 	min_count++;
		 	return true;
		}
		return false;
	}

	private void log(String data) {

		log.info(data);
		Reporter.log(data);
		
	}

	private String getResultStatusName(int status) {
		
		String resultName = null;
		if(status == 1)
			resultName = "SUCCESS";
		if(status == 2)
			resultName = "FAILURE";
		if(status == 3)
			resultName = "SKIPPED";
		return resultName;
	}
	
}
