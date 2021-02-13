import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.fail;

public class VoiceDeleter {
    private final WebDriver driver;
    private final StringBuffer verificationErrors = new StringBuffer();
    private final long DELAY = 500L;

    public VoiceDeleter() {
        ChromeOptions options = new ChromeOptions();
        String profileDir = System.getProperty("user.home") + "/temp/chromium-profile";
        File pDir = new File(profileDir);
        if (!pDir.isDirectory()) pDir.mkdirs();
        options.addArguments("user-data-dir=" + profileDir);
        driver = new ChromeDriver(options);
        // Increase this delay if you want to kill the program before it closes the browser.
        // useful to kill the program and login to google account. This removes logging in every time.
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public void deleteMessage() throws InterruptedException {
        driver.get("https://voice.google.com/u/0/messages?");
        for (int i = 0; i < 50; i++) {
            driver.findElement(By.xpath("//div[@id='messaging-view']/div/md-content/div/gv-conversation-list/" +
                    "md-virtual-repeat-container/div/div[2]/div/div/gv-text-thread-item/gv-thread-item/div/" +
                    "div[2]/ng-transclude/gv-thread-item-detail/gv-annotation")).click();

            driver.findElement(By.cssSelector("button.md-icon-button.uM2Vn-pzCKEc.md-button > " +
                    "mat-icon.mat-icon.notranslate.mat-accent > svg")).click();

            Thread.sleep(DELAY);
            driver.findElement(By.xpath("(//button[@type='button'])[9]")).click();
            Thread.sleep(DELAY);
//            WebElement iUnderstand = driver.findElement(By.xpath("//md-checkbox/div[1]"));
//            if (understand != null) iUnderstand.click();
//            Thread.sleep(DELAY);
            driver.findElement(By.xpath("//gv-flat-button/span/button/span")).click();
            Thread.sleep(DELAY);
        }
    }

    public void destroy() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VoiceDeleter r = new VoiceDeleter();
        try {
            r.deleteMessage();
        } finally {
            r.destroy();
        }
    }
}
