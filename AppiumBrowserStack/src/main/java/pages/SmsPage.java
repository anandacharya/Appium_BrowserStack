package pages;

import base.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SmsPage extends BaseTest {

    @AndroidFindBy(id = "edt_sms_code")
    @iOSXCUITFindBy(accessibility = "smsTextField")
    private MobileElement smsTxtField;

    @AndroidFindBy(id = "btn_submit_sms_code")
    @iOSXCUITFindBy(iOSNsPredicate = "label == \"CONFIRM\" AND name == \"CONFIRM\" AND type == \"XCUIElementTypeButton\"")
    private MobileElement confirmBtn;

    public RunSelectionPage driverEntersSmsAndClicksConfirm(){
        sendKeys(smsTxtField,"123456");
        click(confirmBtn);
        return new RunSelectionPage();
    }
}
