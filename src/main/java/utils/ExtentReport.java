package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.File;

public class ExtentReport {

    public static ExtentReports extentreport = null;

    public static ExtentTest extentlog;

    public static void initialize(String extentconfigxmlPATH) {

        if (extentreport == null) {
            extentreport = new ExtentReports(extentconfigxmlPATH, true);
            extentreport.addSystemInfo("Host Name", System.getProperty("user.name"));
            extentreport.addSystemInfo("Environment", "QA");
            extentreport.addSystemInfo("OS", "Windows 11 Business - 26100.7840");
            extentreport.loadConfig(new File(System.getProperty("user.dir") + "/resources/extent.config.xml"));

        }
    }
}
