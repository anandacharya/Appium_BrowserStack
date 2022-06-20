package base;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import utils.JsonParser;
import utils.TestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

public class BaseTest {
    protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
    protected static ThreadLocal<Properties> props = new ThreadLocal<Properties>();
    protected static ThreadLocal<String> platform = new ThreadLocal<String>();
    protected static ThreadLocal<String> dateTime = new ThreadLocal<String>();
    TestUtils utils;

    public AppiumDriver getDriver(){
        return driver.get();
    }

    public void setDriver(AppiumDriver driver){
        BaseTest.driver.set(driver);
    }

    public Properties getProps(){
        return props.get();
    }

    public void setProps(Properties props){
        BaseTest.props.set(props);
    }

    public String getPlatform(){
        return platform.get();
    }

    public void setPlatform(String platform){
        BaseTest.platform.set(platform);
    }

    public String getDateTime(){
        return dateTime.get();
    }

    public void setDateTime(String dateTime){
        BaseTest.dateTime.set(dateTime);
    }

    public BaseTest(){
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }

    @Parameters({"platformName"})
    @BeforeTest()
    public void initializeDriver(String platformName) throws IOException {
        utils = new TestUtils();
        setDateTime(utils.dateTime());
        setPlatform(platformName);
        URL url = null;
        InputStream inputStream = null;
        AppiumDriver driver;
        try{
            Properties props = new Properties();
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);
            setProps(props);

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("platformName", platformName);

            switch (platformName){
                case "Android":
                    if (props.getProperty("runOnBrowserStack").equals("true")) {
                        JSONArray devices = new JSONArray(JsonParser.parseJSONArray("androiddevices.json"));
                        JSONObject deviceObj = devices.getJSONObject(new Random().nextInt(devices.length()));
                        String userName = System.getenv("BROWSERSTACK_USERNAME");
                        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
                        String appid = System.getenv("BROWSERSTACK_APP_ID");
                        String browserstackLocal = System.getenv("BROWSERSTACK_LOCAL");
                        String browserstackLocalIdentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER");
                        try {
                            url = new URL("https://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        desiredCapabilities.setCapability("os_version", deviceObj.getString("os_version"));
                        desiredCapabilities.setCapability("device", deviceObj.getString("device"));
                        desiredCapabilities.setCapability("project", "WooliesGO_UAT");
                        desiredCapabilities.setCapability("build", "SampleProject");
                        desiredCapabilities.setCapability("browserstack.networkLogs", "true");
                        desiredCapabilities.setCapability("app", appid);
                    } else {
                        try {
                            url = new URL(props.getProperty("appiumURL"));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    //capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
                    desiredCapabilities.setCapability("appium:automationName", props.getProperty("androidAutomationName"));
                    desiredCapabilities.setCapability("appium:appPackage", props.getProperty("androidAppPackage"));
                    desiredCapabilities.setCapability("appium:appActivity", props.getProperty("androidAppActivity"));
                    //capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "foo");
                    desiredCapabilities.setCapability("appium:autoAcceptAlerts", "true");
                    desiredCapabilities.setCapability("unicodeKeyboard", true);
                    desiredCapabilities.setCapability("resetKeyboard", true);
                    desiredCapabilities.setCapability("appium:autoGrantPermissions", true);
                    desiredCapabilities.setCapability("appWaitForLaunch",false);
                    driver = new AndroidDriver(url, desiredCapabilities);
                    break;
                case "iOS":
                    if(props.getProperty("runOnBrowserStack").equals("true")){
                        JSONArray devices = new JSONArray(JsonParser.parseJSONArray("iosdevices.json"));
                        JSONObject deviceObj = devices.getJSONObject(new Random().nextInt(devices.length()));
                        String userName = System.getenv("BROWSERSTACK_USERNAME");
                        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
                        String appid = System.getenv("BROWSERSTACK_APP_ID");
                        String browserstackLocal = System.getenv("BROWSERSTACK_LOCAL");
                        String browserstackLocalIdentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER");
                        url = new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub");
                        desiredCapabilities.setCapability("os_version", deviceObj.getString("os_version"));
                        desiredCapabilities.setCapability("device", deviceObj.getString("device"));
                        desiredCapabilities.setCapability("browserstack.local", browserstackLocal);
                        desiredCapabilities.setCapability("browserstack.localIdentifier", browserstackLocalIdentifier);
                        desiredCapabilities.setCapability("browserstack.networkLogs", "true");
                        desiredCapabilities.setCapability("app", appid);
                    }
                    else{
                        desiredCapabilities.setCapability("appium:deviceName","Anand's iPhone");
                        desiredCapabilities.setCapability("appium:udid",props.getProperty("iOSudid"));
                        desiredCapabilities.setCapability("appium:xcodeOrgId",props.getProperty("iOSxcodeOrgId"));
                        desiredCapabilities.setCapability("appium:xcodeSigningId","iphone Developer");
                        url = new URL(props.getProperty("appiumURL"));
                    }
                    desiredCapabilities.setCapability("appium:automationName",props.getProperty("iOSAutomationName"));
                    desiredCapabilities.setCapability("appium:noReset",true);
                    desiredCapabilities.setCapability("appium:bundleId",props.getProperty("iOSBundleId"));
                    desiredCapabilities.setCapability("autoGrantPermissions", true);
                    desiredCapabilities.setCapability("autoAcceptAlerts", "true");
                    driver = new IOSDriver(url, desiredCapabilities);
                    break;
                default:
                    throw new Exception("Invalid plaform : "+platformName);
            }
            setDriver(driver);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                inputStream.close();
            }
        }
    }

    public void waitForVisibility(MobileElement e){
        WebDriverWait wait = new WebDriverWait(getDriver(), TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void click(MobileElement e){
        waitForVisibility(e);
        e.click();
    }

    public void sendKeys(MobileElement e, String txt){
        waitForVisibility(e);
        e.sendKeys(txt);
    }

    public String getAttribute(MobileElement e, String attribute){
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }

    public void clearText(MobileElement e){
        waitForVisibility(e);
        e.clear();
    }

    public String getText(MobileElement e){
        switch (getPlatform()){
            case "Android":
                return getAttribute(e, "text");
            case "iOS":
                return getAttribute(e, "label");
        }
        return null;
    }

    public boolean isElementEnabled(MobileElement e) {
        Boolean status = true;
        try{
            waitForVisibility(e);
        } catch (Exception ex){status = false;}
        return status;
    }

    public void closeApp(){
        ((InteractsWithApps)getDriver()).closeApp();
    }

    public void launchApp(){
        ((InteractsWithApps)getDriver()).launchApp();
    }

    public void scrollUsingTouchAction(String direction) {
        Dimension dim = getDriver().manage().window().getSize();
        int x = dim.getWidth() / 2;
        int startY = 0;
        int endY = 0;

        switch (direction) {
            case "up":
                startY = (int) (dim.getHeight() * 0.8);
                endY = (int) (dim.getHeight() * 0.2);
                break;
            case "down":
                startY = (int) (dim.getHeight() * 0.2);
                endY = (int) (dim.getHeight() * 0.8);
                break;
        }
        TouchAction t = new TouchAction(getDriver());
        t.press(PointOption.point(x, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(x, endY)).release().perform();
    }

    public void iOSScrollToElementByText(String text){
        RemoteWebElement element = (RemoteWebElement)getDriver().findElement(By.name("\""+text+"\""));
        String elementID = element.getId();
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("element", elementID);
        scrollObject.put("toVisible", "sdfnjksdnfkld");
        getDriver().executeScript("mobile:scroll", scrollObject);
    }

    public void scrollToTextAndClick(String text) {
        try{
            System.out.println("Scrolling to text : "+text);
            ((AndroidDriver<MobileElement>) getDriver()).findElementByAndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""+text+"\").instance(0))").click();
        } catch (Exception e) {System.out.println("Unable to scroll to text "+text);}
    }

    @AfterTest(alwaysRun = true)
    public void quit() {
        if (getDriver() != null) {
            System.out.println("Quitting driver session");
            getDriver().quit();
        }
    }

}
