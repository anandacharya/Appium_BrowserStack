package pages;

import base.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.testng.Assert;

public class DashboardPage extends BaseTest {

    @AndroidFindBy(id = "txt_current_drop")
    @iOSXCUITFindBy(accessibility = "nextButton")
    private MobileElement nextButton;

    public void isThisDashBoardPage(){
        Assert.assertTrue(isElementEnabled(nextButton));
    }
}
