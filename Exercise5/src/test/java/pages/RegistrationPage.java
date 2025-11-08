package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class RegistrationPage extends BasePage {

    private final By firstName      = By.id("firstName");
    private final By lastName       = By.id("lastName");
    private final By email          = By.id("userEmail");
    private final By phone          = By.id("userNumber");
    private final By dobInput       = By.id("dateOfBirthInput");
    private final By subjectsInput  = By.id("subjectsInput");

    private final By stateContainer = By.id("state");
    private final By stateInput     = By.id("react-select-3-input");
    private final By cityContainer  = By.id("city");
    private final By cityInput      = By.id("react-select-4-input");

    private final By submitBtn      = By.id("submit");

    private final By modal          = By.cssSelector(".modal-content");
    private final By closeModalBtn  = By.id("closeLargeModal");

    public RegistrationPage(WebDriver driver) { super(driver); }

    public void open() {
        navigateTo("https://demoqa.com/automation-practice-form");
        waitPageReady();
        hideFixedObstacles(); // quan trọng để không bị che form
        // cuộn nhẹ để đảm bảo input vào vùng nhìn thấy
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 100);");
        waitForPresence(firstName);
    }

    public RegistrationPage setFirstName(String value) {
        type(firstName, safe(value)); return this;
    }
    public RegistrationPage setLastName(String value) {
        type(lastName, safe(value)); return this;
    }
    public void setEmail(String value) {
        type(email, safe(value));
    }

    public void selectGender(String genderText) {
        By gender = By.xpath("//label[normalize-space()='" + genderText + "']");
        scrollIntoView(gender);
        click(gender);
    }

    public void setMobile(String value) {
        type(phone, safe(value));
    }

    public void setDateOfBirth(String formattedDate) {
        WebElement input = waitForVisibility(dobInput);
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(formattedDate);
        input.sendKeys(Keys.ENTER);
    }

    public void addSubject(String subject) {
        WebElement in = waitForVisibility(subjectsInput);
        in.sendKeys(subject);
        // chờ suggestion nảy lên rồi mới ENTER để chắc chắn select
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div[id^='react-select-2-option-']")));
        in.sendKeys(Keys.ENTER);
    }

    public void selectHobby(String hobbyText) {
        By hobby = By.xpath("//label[normalize-space()='" + hobbyText + "']");
        scrollIntoView(hobby);
        click(hobby);
    }

    public void selectStateAndCity(String state, String city) {
        scrollIntoView(stateContainer);
        click(stateContainer);
        WebElement sIn = wait.until(ExpectedConditions.visibilityOfElementLocated(stateInput));
        sIn.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        sIn.sendKeys(state);

        By stateOpt = By.xpath("//div[starts-with(@id,'react-select-3-option') and normalize-space()='" + state + "']");
        wait.until(ExpectedConditions.presenceOfElementLocated(stateOpt));
        sIn.sendKeys(Keys.ENTER);

        // ✅ xác nhận state đã hiển thị trong container
        wait.until(ExpectedConditions.textToBePresentInElementLocated(stateContainer, state));

        // ----- CITY -----
        wait.until(ExpectedConditions.elementToBeClickable(cityContainer));
        click(cityContainer);
        WebElement cIn = wait.until(ExpectedConditions.visibilityOfElementLocated(cityInput));
        cIn.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        cIn.sendKeys(city);

        By cityOpt = By.xpath("//div[starts-with(@id,'react-select-4-option') and normalize-space()='" + city + "']");
        wait.until(ExpectedConditions.presenceOfElementLocated(cityOpt));
        cIn.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.textToBePresentInElementLocated(cityContainer, city));

    }

    public void submit() {
        scrollIntoView(submitBtn);
        click(submitBtn);
    }

    public boolean isResultModalVisible() {
        try {
            wait.withTimeout(Duration.ofSeconds(8))
                    .until(ExpectedConditions.visibilityOfElementLocated(modal));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void closeModal() { click(closeModalBtn); }

    private String safe(String s) { return s == null ? "" : s.strip(); }
}
