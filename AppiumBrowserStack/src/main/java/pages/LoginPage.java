package pages;

import base.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class LoginPage extends BaseTest {

    @AndroidFindBy(id = "edt_driverid")
    @iOSXCUITFindBy(accessibility = "loginTextField")
    private MobileElement driverIdTxtBox;

    @AndroidFindBy(id = "btn_login")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"LOGIN\"]")
    private MobileElement loginBtn;

    @iOSXCUITFindBy(accessibility = "Invalid driver ID")
    private MobileElement errText;

    @AndroidFindBy(id = "txt_app_version")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"LOGIN\"]/parent::*[1]/preceding-sibling::*[1]")
    private MobileElement versionText;


    public SmsPage driverLogsInTheApp(String driverId){
        clearText(driverIdTxtBox);
        sendKeys(driverIdTxtBox,driverId);
        click(loginBtn);
        return new SmsPage();
    }

    public String getErrTxt(){
        return getText(errText);
    }

    public String getVersionNumber(){
        return getText(versionText);
    }
}
