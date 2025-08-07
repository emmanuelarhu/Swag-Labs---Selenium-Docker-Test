package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CheckoutStepOnePage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutStepOnePage.class);

    @FindBy(css = "[data-test='title']")
    private WebElement pageTitle;

    @FindBy(css = "[data-test='firstName']")
    private WebElement firstNameField;

    @FindBy(css = "[data-test='lastName']")
    private WebElement lastNameField;

    @FindBy(css = "[data-test='postalCode']")
    private WebElement postalCodeField;

    @FindBy(css = "[data-test='continue']")
    private WebElement continueButton;

    @FindBy(css = "[data-test='cancel']")
    private WebElement cancelButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @FindBy(css = ".error-message-container")
    private WebElement errorMessageContainer;

    @FindBy(css = ".error-message-container .error")
    private WebElement errorBanner;

    @FindBy(css = ".error-message-container h3")
    private WebElement errorMessageTitle;

    public CheckoutStepOnePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
        logger.info("CheckoutStepOnePage initialized");
    }

    @Step("Verify checkout step one page is loaded")
    public boolean isCheckoutStepOnePageLoaded() {
        boolean isLoaded = isElementDisplayed(pageTitle) &&
                getCurrentUrl().contains("checkout-step-one.html") &&
                getElementText(pageTitle).equals("Checkout: Your Information");
        logger.info("Checkout step one page loaded: {}", isLoaded);
        return isLoaded;
    }

    @Step("Enter first name: {firstName}")
    public CheckoutStepOnePage enterFirstName(String firstName) {
        logger.info("Entering first name: {}", firstName);
        clearAndType(firstNameField, firstName);
        return this;
    }

    @Step("Enter last name: {lastName}")
    public CheckoutStepOnePage enterLastName(String lastName) {
        logger.info("Entering last name: {}", lastName);
        clearAndType(lastNameField, lastName);
        return this;
    }

    @Step("Enter postal code: {postalCode}")
    public CheckoutStepOnePage enterPostalCode(String postalCode) {
        logger.info("Entering postal code: {}", postalCode);
        clearAndType(postalCodeField, postalCode);
        return this;
    }

    @Step("Fill checkout information - First: {firstName}, Last: {lastName}, Postal: {postalCode}")
    public CheckoutStepOnePage fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        logger.info("Filling checkout information - First: {}, Last: {}, Postal: {}",
                firstName, lastName, postalCode);
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        return this;
    }

    @Step("Click continue button")
    public CheckoutStepTwoPage clickContinueButton() {
        logger.info("Clicking continue button");
        clickElement(continueButton);

        // Wait a moment for potential error messages or navigation
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return new CheckoutStepTwoPage(driver, wait);
    }

    @Step("Click cancel button")
    public CartPage clickCancelButton() {
        logger.info("Clicking cancel button");
        clickElement(cancelButton);
        return new CartPage(driver, wait);
    }

    @Step("Verify form fields are displayed")
    public boolean areFormFieldsDisplayed() {
        boolean fieldsDisplayed = isElementDisplayed(firstNameField) &&
                isElementDisplayed(lastNameField) &&
                isElementDisplayed(postalCodeField);
        logger.info("Form fields displayed: {}", fieldsDisplayed);
        return fieldsDisplayed;
    }

    @Step("Verify error message is displayed")
    public boolean isErrorMessageDisplayed() {
        try {
            // Wait a moment for error to appear
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Check multiple possible error message locations
        boolean standardErrorDisplayed = false;
        boolean containerErrorDisplayed = false;
        boolean bannerErrorDisplayed = false;

        try {
            standardErrorDisplayed = errorMessage != null && errorMessage.isDisplayed();
        } catch (Exception e) {
            logger.debug("Standard error message not found: {}", e.getMessage());
        }

        try {
            containerErrorDisplayed = errorMessageContainer != null && errorMessageContainer.isDisplayed();
        } catch (Exception e) {
            logger.debug("Error message container not found: {}", e.getMessage());
        }

        try {
            bannerErrorDisplayed = errorBanner != null && errorBanner.isDisplayed();
        } catch (Exception e) {
            logger.debug("Error banner not found: {}", e.getMessage());
        }

        // Also check for any element with error-related classes using dynamic lookup
        boolean anyErrorDisplayed = false;
        try {
            List<WebElement> errorElements = driver.findElements(By.cssSelector(
                    ".error-message-container, [data-test='error'], .error, .error-banner, .field-error"));

            for (WebElement element : errorElements) {
                if (element.isDisplayed()) {
                    anyErrorDisplayed = true;
                    logger.debug("Found error element with selector and text: {} - {}",
                            element.getTagName(), element.getText());
                    break;
                }
            }
        } catch (Exception e) {
            logger.debug("No error elements found with common selectors");
        }

        boolean isDisplayed = standardErrorDisplayed || containerErrorDisplayed ||
                bannerErrorDisplayed || anyErrorDisplayed;

        logger.info("Error message displayed: {} (standard: {}, container: {}, banner: {}, any: {})",
                isDisplayed, standardErrorDisplayed, containerErrorDisplayed,
                bannerErrorDisplayed, anyErrorDisplayed);

        return isDisplayed;
    }

    @Step("Get error message text")
    public String getErrorMessageText() {
        String errorText = "";

        // Try different error message locations in order of preference
        try {
            if (errorMessage != null && errorMessage.isDisplayed()) {
                errorText = getElementText(errorMessage);
                logger.info("Standard error message: {}", errorText);
                return errorText;
            }
        } catch (Exception e) {
            logger.debug("Standard error message not available: {}", e.getMessage());
        }

        try {
            if (errorMessageContainer != null && errorMessageContainer.isDisplayed()) {
                errorText = getElementText(errorMessageContainer);
                logger.info("Container error message: {}", errorText);
                return errorText;
            }
        } catch (Exception e) {
            logger.debug("Container error message not available: {}", e.getMessage());
        }

        try {
            if (errorBanner != null && errorBanner.isDisplayed()) {
                errorText = getElementText(errorBanner);
                logger.info("Banner error message: {}", errorText);
                return errorText;
            }
        } catch (Exception e) {
            logger.debug("Banner error message not available: {}", e.getMessage());
        }

        // Try to find any error element with common selectors
        try {
            List<WebElement> errorElements = driver.findElements(By.cssSelector(
                    ".error-message-container, [data-test='error'], .error, .error-banner, .field-error"));

            for (WebElement element : errorElements) {
                if (element.isDisplayed() && !element.getText().trim().isEmpty()) {
                    errorText = element.getText().trim();
                    logger.info("Generic error message found: {}", errorText);
                    return errorText;
                }
            }
        } catch (Exception e) {
            logger.debug("No error message found with any selector: {}", e.getMessage());
        }

        // Try specific SauceDemo error patterns
        try {
            WebElement sauceDemoError = driver.findElement(By.cssSelector(".error-message-container h3"));
            if (sauceDemoError.isDisplayed()) {
                errorText = sauceDemoError.getText().trim();
                logger.info("SauceDemo specific error message found: {}", errorText);
                return errorText;
            }
        } catch (Exception e) {
            logger.debug("SauceDemo specific error pattern not found: {}", e.getMessage());
        }

        logger.warn("No error message text found");
        return errorText;
    }

    @Step("Check if error message contains specific text: {expectedText}")
    public boolean errorMessageContains(String expectedText) {
        try {
            String errorText = getErrorMessageText();
            boolean contains = errorText.toLowerCase().contains(expectedText.toLowerCase());
            logger.info("Error message contains '{}': {} (actual message: '{}')",
                    expectedText, contains, errorText);
            return contains;
        } catch (Exception e) {
            logger.error("Error checking error message content: {}", e.getMessage());
            return false;
        }
    }

    @Step("Verify specific field error messages")
    public boolean hasSpecificFieldError(String field) {
        String errorText = getErrorMessageText();
        boolean hasFieldError = false;

        switch (field.toLowerCase()) {
            case "firstname":
            case "first name":
                hasFieldError = errorText.contains("First Name") || errorText.contains("firstName");
                break;
            case "lastname":
            case "last name":
                hasFieldError = errorText.contains("Last Name") || errorText.contains("lastName");
                break;
            case "postalcode":
            case "postal code":
            case "zipcode":
            case "zip code":
                hasFieldError = errorText.contains("Postal Code") || errorText.contains("Zip Code") ||
                        errorText.contains("postalCode");
                break;
            default:
                hasFieldError = errorText.toLowerCase().contains(field.toLowerCase());
        }

        logger.info("Field '{}' error present: {} (error message: '{}')", field, hasFieldError, errorText);
        return hasFieldError;
    }

    @Step("Verify field validation errors are displayed")
    public boolean areFieldValidationErrorsDisplayed() {
        // Check if any input fields have error styling
        try {
            // Look for error styling on input fields
            List<WebElement> fieldsWithErrors = driver.findElements(By.cssSelector(
                    "[data-test='firstName'].error, [data-test='firstName']:invalid, " +
                            "[data-test='firstName'][aria-invalid='true'], " +
                            "[data-test='lastName'].error, [data-test='lastName']:invalid, " +
                            "[data-test='lastName'][aria-invalid='true'], " +
                            "[data-test='postalCode'].error, [data-test='postalCode']:invalid, " +
                            "[data-test='postalCode'][aria-invalid='true']"));

            boolean hasFieldErrors = !fieldsWithErrors.isEmpty();
            for (WebElement field : fieldsWithErrors) {
                if (field.isDisplayed()) {
                    logger.info("Field validation error found on: {}", field.getAttribute("data-test"));
                    hasFieldErrors = true;
                }
            }

            logger.info("Field validation errors displayed: {}", hasFieldErrors);
            return hasFieldErrors;

        } catch (Exception e) {
            logger.debug("No field validation errors found: {}", e.getMessage());
            return false;
        }
    }

    @Step("Get first name field value")
    public String getFirstNameValue() {
        try {
            String value = firstNameField.getAttribute("value");
            logger.info("First name field value: {}", value);
            return value != null ? value : "";
        } catch (Exception e) {
            logger.error("Error getting first name value: {}", e.getMessage());
            return "";
        }
    }

    @Step("Get last name field value")
    public String getLastNameValue() {
        try {
            String value = lastNameField.getAttribute("value");
            logger.info("Last name field value: {}", value);
            return value != null ? value : "";
        } catch (Exception e) {
            logger.error("Error getting last name value: {}", e.getMessage());
            return "";
        }
    }

    @Step("Get postal code field value")
    public String getPostalCodeValue() {
        try {
            String value = postalCodeField.getAttribute("value");
            logger.info("Postal code field value: {}", value);
            return value != null ? value : "";
        } catch (Exception e) {
            logger.error("Error getting postal code value: {}", e.getMessage());
            return "";
        }
    }

    @Step("Clear all form fields")
    public CheckoutStepOnePage clearAllFields() {
        logger.info("Clearing all checkout form fields");
        try {
            firstNameField.clear();
            lastNameField.clear();
            postalCodeField.clear();
        } catch (Exception e) {
            logger.error("Error clearing form fields: {}", e.getMessage());
        }
        return this;
    }

    @Step("Verify continue button is enabled")
    public boolean isContinueButtonEnabled() {
        try {
            boolean isEnabled = continueButton.isEnabled();
            logger.info("Continue button enabled: {}", isEnabled);
            return isEnabled;
        } catch (Exception e) {
            logger.error("Error checking continue button state: {}", e.getMessage());
            return false;
        }
    }

    @Step("Verify cancel button is enabled")
    public boolean isCancelButtonEnabled() {
        try {
            boolean isEnabled = cancelButton.isEnabled();
            logger.info("Cancel button enabled: {}", isEnabled);
            return isEnabled;
        } catch (Exception e) {
            logger.error("Error checking cancel button state: {}", e.getMessage());
            return false;
        }
    }

    @Step("Wait for error message to appear")
    public boolean waitForErrorMessage() {
        try {
            // Wait up to 5 seconds for error message to appear
            for (int i = 0; i < 10; i++) {
                if (isErrorMessageDisplayed()) {
                    return true;
                }
                Thread.sleep(500);
            }
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting for error message");
            return false;
        }
    }
}