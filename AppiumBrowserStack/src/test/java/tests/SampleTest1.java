package tests;

import base.BaseTest;
import org.testng.annotations.*;
import pages.DashboardPage;
import pages.LoginPage;
import pages.RunSelectionPage;
import pages.SmsPage;
import utils.HttpHelper;

import java.lang.reflect.Method;

public class SampleTest1 extends BaseTest {
    LoginPage loginPage;
    SmsPage smsPage;
    RunSelectionPage runSelectionPage;
    DashboardPage dashboardPage;

    static String routeId = "f7bde1bc-e36e-4448-b332-828f6cd5e8e2";
    static String driverId = "9300806";

    @BeforeClass
    public void beforeClass(){
        //closeApp();
        HttpHelper.resetRoute(routeId);
        //launchApp();
    }

    @AfterClass
    public void afterClass(){

    }

    @BeforeMethod
    public void beforeMethod(Method m){
        loginPage = new LoginPage();
        System.out.println("\n"+"******** starting test: "+m.getName()+" **********"+"\n");
    }

    @AfterMethod
    public void afterMethod(Method m){
        System.out.println("\n"+"******** ending test: "+m.getName()+" **********"+"\n");
    }

    @Test
    public void verifyLogin(){
        smsPage = loginPage.driverLogsInTheApp("9300806");
        runSelectionPage = smsPage.driverEntersSmsAndClicksConfirm();
        dashboardPage = runSelectionPage.driverTapsOnSelectRouteButton();
        dashboardPage.isThisDashBoardPage();
    }

}
