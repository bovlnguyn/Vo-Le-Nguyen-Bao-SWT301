package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    protected WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    protected void hideFixedObstacles() {
        String js = """
      (function(){
        const kill = sel => document.querySelectorAll(sel).forEach(e => e.remove());
        // Quảng cáo quen thuộc trên DemoQA
        kill('#fixedban');
        kill('#adplus-anchor');
        kill('iframe[style*="position: fixed"], iframe[style*="z-index"]');

        // Dọn mọi overlay nổi bật
        document.querySelectorAll('*').forEach(el => {
          try {
            const cs = getComputedStyle(el);
            if (cs.position === 'fixed') {
              const zi = parseInt(cs.zIndex || '0', 10);
              if (zi >= 1000 || cs.bottom === '0px' || cs.top === '0px') {
                el.parentNode && el.parentNode.removeChild(el);
              }
            }
          } catch(e){}
        });
      })();
    """;
        ((JavascriptExecutor) driver).executeScript(js);
    }

    protected void waitPageReady() {
        // Chấp nhận 'complete' hoặc 'interactive' để tránh treo khi tài nguyên phụ chưa load xong
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> {
                Object s = ((JavascriptExecutor) d).executeScript("return document.readyState");
                return "complete".equals(s) || "interactive".equals(s);
            });
        } catch (TimeoutException ignore) {
        }

        // Nếu gặp 502/Bad Gateway thì thử reload nhanh 1 lần
        String html = driver.getPageSource().toLowerCase();
        if (html.contains("bad gateway") || html.contains("502")) {
            driver.navigate().refresh();
            try {
                new WebDriverWait(driver, Duration.ofSeconds(15)).until(d -> {
                    Object s = ((JavascriptExecutor) d).executeScript("return document.readyState");
                    return "complete".equals(s) || "interactive".equals(s);
                });
            } catch (TimeoutException ignore) {}
        }
    }

    /* ===== Actions ===== */
    protected void click(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (ElementClickInterceptedException e) {
            WebElement el = waitForVisibility(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    protected void type(By locator, String text) {
        WebElement element = waitForPresence(locator); // presence trước
        scrollIntoView(locator);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        if (text != null) element.sendKeys(text);
    }


    public void navigateTo(String url) {
        driver.get(url);
    }

    protected void scrollIntoView(By locator) {
        WebElement el = waitForPresence(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);
    }
}
