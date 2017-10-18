import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class test {

    private AndroidDriver driver;
    private File imgDir;

    @Before
    public void setUp() throws MalformedURLException {
        File classpathRoot = new File(System.getProperty("user.dir"));

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        //CHANGE REQUIRED: put your apk in the src/main/resources folder and change the path below.
        desiredCapabilities.setCapability("app", new File(classpathRoot, "src/main/resources/APKNAME.apk"));
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("deviceName", "ZZZ");
        desiredCapabilities.setCapability("noReset", true);
        desiredCapabilities.setCapability("fullReset", false);

        URL remoteUrl = new URL("http://localhost:4723/wd/hub");

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);

        //location of screenshots
        imgDir = new File(classpathRoot, "src/main/resources");

    }

    @Test
    public void sbFarm() throws InterruptedException, IOException {
        //Sleep is basically a pause for the given amount of time, useful for loading screens
        Thread.sleep(1000);

        //Below is used to get your screensize, useful for supporting multiple devices
        double height = driver.manage().window().getSize().getHeight();
        double width = driver.manage().window().getSize().getWidth();
        System.out.println("h: " + height + " w: " + width);

        //Example tap action, hitting middle of the screen
        (new TouchAction(driver)).tap((int) width/2,(int) height/2).perform();

        //Example swipe action, starts in middle, goes up 100, right 100, the releases, pausing for 250ms between each move.
        (new TouchAction(driver)).press((int) width/2,(int) height/2)
             .waitAction(Duration.ofMillis(250))
             .moveTo(-100, 0)
             .waitAction(Duration.ofMillis(250))
             .moveTo(0, 100)
             .waitAction(Duration.ofMillis(250))
             .release()
            .perform();

    }

    @After
    public void tearDown() throws IOException {

        File srcFile=driver.getScreenshotAs(OutputType.FILE);
        File targetFile=new File(imgDir + "/end.jpg");
        FileUtils.copyFile(srcFile,targetFile);

        driver.quit();
    }
}

