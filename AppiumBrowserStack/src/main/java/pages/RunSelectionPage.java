package pages;

import base.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class RunSelectionPage extends BaseTest {

    @AndroidFindBy(id = "btnSelect")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"SELECT\"]")
    private MobileElement selectRouteBtn;

    public DashboardPage driverTapsOnSelectRouteButton(){
        click(selectRouteBtn);
        return new DashboardPage();
    }
}
