package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.pages.*;
import com.swaglabs.utils.TestDataReader;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("SauceDemo Checkout Testing")
@Feature("Checkout Field Validation and Empty Cart Scenarios")
public class CheckoutValidationTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutValidationTest.class);

    @Test(description = "Verify checkout form validation for Numerical field", priority = 1)
    @Story("Checkout Form Required Letter Field Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test validates that Numerical entry shows error when numbers are entered")
    public void testCheckoutNumericalValidation() {
        logger.info("Starting checkout Numerical validation test");

        // Step 1: Login and add items to cart
        LoginPage loginPage = new LoginPage(driver, wait);
        String username = TestDataReader.getTestDataAsString("credentials.username");
        String password = TestDataReader.getTestDataAsString("credentials.password");

        InventoryPage inventoryPage = loginPage.login(username, password);

        // Add items to cart
        inventoryPage.addBackpackToCart();

        // Step 2: Navigate to checkout
        CartPage cartPage = inventoryPage.clickShoppingCartLink();
        CheckoutStepOnePage checkoutPage = cartPage.clickCheckoutButton();

        // Step 3: Fill form with empty First Name
        checkoutPage.enterFirstName("12345"); // Empty field
        checkoutPage.enterLastName("12345");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinueButton();

        // Step 4: Verify error message appears
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(),
                "Error message should be displayed for Numerical entry");

        String errorMessage = checkoutPage.getErrorMessageText();
        Assert.assertTrue(errorMessage.contains("Letters are required") ||
                        errorMessage.contains("Error: Letters are required"),
                "Error message should indicate Letters are required. Actual: " + errorMessage);

        takeScreenshot();
        logger.info("Numerical entry validation test completed successfully");
    }
    @Test(description = "Verify checkout form validation for empty First Name field", priority = 2)
    @Story("Checkout Form Required Field Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test validates that First Name field shows error when left empty")
    public void testCheckoutEmptyFirstNameValidation() {
        logger.info("Starting checkout empty First Name validation test");

        // Step 1: Login and add items to cart
        LoginPage loginPage = new LoginPage(driver, wait);
        String username = TestDataReader.getTestDataAsString("credentials.username");
        String password = TestDataReader.getTestDataAsString("credentials.password");

        InventoryPage inventoryPage = loginPage.login(username, password);

        // Add items to cart
        inventoryPage.addBackpackToCart();

        // Step 2: Navigate to checkout
        CartPage cartPage = inventoryPage.clickShoppingCartLink();
        CheckoutStepOnePage checkoutPage = cartPage.clickCheckoutButton();

        // Step 3: Fill form with empty First Name
        checkoutPage.enterFirstName(""); // Empty field
        checkoutPage.enterLastName("Arhu");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinueButton();

        // Step 4: Verify error message appears
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(),
                "Error message should be displayed for empty First Name");

        String errorMessage = checkoutPage.getErrorMessageText();
        Assert.assertTrue(errorMessage.contains("First Name is required") ||
                        errorMessage.contains("Error: First Name is required"),
                "Error message should indicate First Name is required. Actual: " + errorMessage);

        takeScreenshot();
        logger.info("Empty First Name validation test completed successfully");
    }

    @Test(description = "Verify checkout form validation for empty Last Name field", priority = 3)
    @Story("Checkout Form Required Field Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test validates that Last Name field shows error when left empty")
    public void testCheckoutEmptyLastNameValidation() {
        logger.info("Starting checkout empty Last Name validation test");

        // Step 1: Login and add items to cart
        LoginPage loginPage = new LoginPage(driver, wait);
        String username = TestDataReader.getTestDataAsString("credentials.username");
        String password = TestDataReader.getTestDataAsString("credentials.password");

        InventoryPage inventoryPage = loginPage.login(username, password);

        // Add items to cart
        inventoryPage.addBackpackToCart();

        // Step 2: Navigate to checkout
        CartPage cartPage = inventoryPage.clickShoppingCartLink();
        CheckoutStepOnePage checkoutPage = cartPage.clickCheckoutButton();

        // Step 3: Fill form with empty Last Name
        checkoutPage.enterFirstName("Emmanuel");
        checkoutPage.enterLastName(""); // Empty field
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinueButton();

        // Step 4: Verify error message appears
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(),
                "Error message should be displayed for empty Last Name");

        String errorMessage = checkoutPage.getErrorMessageText();
        Assert.assertTrue(errorMessage.contains("Last Name is required") ||
                        errorMessage.contains("Error: Last Name is required"),
                "Error message should indicate Last Name is required. Actual: " + errorMessage);

        takeScreenshot();
        logger.info("Empty Last Name validation test completed successfully");
    }

    @Test(description = "Verify checkout form validation for empty Postal Code field", priority = 4)
    @Story("Checkout Form Required Field Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test validates that Postal Code field shows error when left empty")
    public void testCheckoutEmptyPostalCodeValidation() {
        logger.info("Starting checkout empty Postal Code validation test");

        // Step 1: Login and add items to cart
        LoginPage loginPage = new LoginPage(driver, wait);
        String username = TestDataReader.getTestDataAsString("credentials.username");
        String password = TestDataReader.getTestDataAsString("credentials.password");

        InventoryPage inventoryPage = loginPage.login(username, password);

        // Add items to cart
        inventoryPage.addBackpackToCart();

        // Step 2: Navigate to checkout
        CartPage cartPage = inventoryPage.clickShoppingCartLink();
        CheckoutStepOnePage checkoutPage = cartPage.clickCheckoutButton();

        // Step 3: Fill form with empty Postal Code
        checkoutPage.enterFirstName("Emmanuel");
        checkoutPage.enterLastName("Arhu");
        checkoutPage.enterPostalCode(""); // Empty field
        checkoutPage.clickContinueButton();

        // Step 4: Verify error message appears
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(),
                "Error message should be displayed for empty Postal Code");

        String errorMessage = checkoutPage.getErrorMessageText();
        Assert.assertTrue(errorMessage.contains("Postal Code is required") ||
                        errorMessage.contains("Error: Postal Code is required"),
                "Error message should indicate Postal Code is required. Actual: " + errorMessage);

        takeScreenshot();
        logger.info("Empty Postal Code validation test completed successfully");
    }

    @Test(description = "Verify checkout form accepts valid mixed postal code", priority = 5)
    @Story("Checkout Form Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test validates that postal code accepts mix of letters and numbers")
    public void testCheckoutValidPostalCodeMixed() {
        logger.info("Starting checkout valid mixed postal code test");

        // Step 1: Login and add items to cart
        LoginPage loginPage = new LoginPage(driver, wait);
        String username = TestDataReader.getTestDataAsString("credentials.username");
        String password = TestDataReader.getTestDataAsString("credentials.password");

        InventoryPage inventoryPage = loginPage.login(username, password);

        // Add items to cart
        inventoryPage.addBackpackToCart();

        // Step 2: Navigate to checkout
        CartPage cartPage = inventoryPage.clickShoppingCartLink();
        CheckoutStepOnePage checkoutPage = cartPage.clickCheckoutButton();

        // Step 3: Fill form with valid data including mixed postal code
        checkoutPage.enterFirstName("Emmanuel");
        checkoutPage.enterLastName("Arhu");
        checkoutPage.enterPostalCode("K1A0A6"); // Canadian postal code format

        // Step 4: Continue to next step
        CheckoutStepTwoPage checkoutStepTwo = checkoutPage.clickContinueButton();

        // Step 5: Verify successful navigation to step two
        Assert.assertTrue(checkoutStepTwo.isCheckoutStepTwoPageLoaded(),
                "Should navigate to checkout step two with valid mixed postal code");

        takeScreenshot();
        logger.info("Valid mixed postal code test completed successfully");
    }

    @Test(description = "Verify checkout with empty cart shows appropriate behavior", priority = 6)
    @Story("Empty Cart Checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test validates behavior when trying to checkout with empty cart")
    public void testCheckoutWithEmptyCart() {
        logger.info("Starting checkout with empty cart test");

        // Step 1: Login without adding items to cart
        LoginPage loginPage = new LoginPage(driver, wait);
        String username = TestDataReader.getTestDataAsString("credentials.username");
        String password = TestDataReader.getTestDataAsString("credentials.password");

        InventoryPage inventoryPage = loginPage.login(username, password);

        // Step 2: Navigate directly to cart (empty)
        CartPage cartPage = inventoryPage.clickShoppingCartLink();

        // Step 3: Verify cart is empty
        Assert.assertEquals(cartPage.getCartItemCount(), 0, "Cart should be empty");
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty");

        // Step 4: Check if checkout button is present/clickable
        boolean isCheckoutAvailable = cartPage.isCheckoutButtonAvailable();

        if (isCheckoutAvailable) {
            logger.info("Checkout button is available for empty cart, testing the flow");

            // Try to proceed to checkout with empty cart
            CheckoutStepOnePage checkoutPage = cartPage.clickCheckoutButton();
            Assert.assertTrue(checkoutPage.isCheckoutStepOnePageLoaded(),
                    "Should still load checkout page even with empty cart");

            // Fill valid form data
            checkoutPage.enterFirstName("Emmanuel");
            checkoutPage.enterLastName("Arhu");
            checkoutPage.enterPostalCode("12345");

            // Try to continue - this should either show error or not proceed
            checkoutPage.clickContinueButton();

            // The behavior might vary - either error message or staying on same page
            if (checkoutPage.isErrorMessageDisplayed()) {
                String errorMessage = checkoutPage.getErrorMessageText();
                logger.info("Error message for empty cart checkout: {}", errorMessage);
            } else {
                // Check if we're still on checkout step one
                Assert.assertTrue(checkoutPage.isCheckoutStepOnePageLoaded(),
                        "Should handle empty cart gracefully");
            }
        } else {
            logger.info("Checkout button not available for empty cart - expected behavior");
        }

        takeScreenshot();
        logger.info("Empty cart checkout test completed successfully");
    }

    @Test(description = "Verify comprehensive checkout validation with all empty fields", priority = 7)
    @Story("Comprehensive Checkout Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test validates form behavior when all required fields are empty")
    public void testCheckoutAllEmptyFieldsValidation() {
        logger.info("Starting comprehensive checkout validation test with all empty fields");

        // Step 1: Login and add items to cart
        LoginPage loginPage = new LoginPage(driver, wait);
        String username = TestDataReader.getTestDataAsString("credentials.username");
        String password = TestDataReader.getTestDataAsString("credentials.password");

        InventoryPage inventoryPage = loginPage.login(username, password);

        // Add items to cart
        inventoryPage.addBackpackToCart();
        inventoryPage.addBikeLightToCart();

        // Step 2: Navigate to checkout
        CartPage cartPage = inventoryPage.clickShoppingCartLink();
        CheckoutStepOnePage checkoutPage = cartPage.clickCheckoutButton();

        // Step 3: Leave all fields empty and try to continue
        checkoutPage.enterFirstName("");
        checkoutPage.enterLastName("");
        checkoutPage.enterPostalCode("");

        checkoutPage.clickContinueButton();

        // Step 4: Verify error message appears
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(),
                "Error message should be displayed for empty form fields");

        String errorMessage = checkoutPage.getErrorMessageText();

        // Should contain reference to required fields
        boolean hasRequiredFieldError = errorMessage.contains("First Name is required") ||
                errorMessage.contains("required") ||
                errorMessage.contains("Error:");

        Assert.assertTrue(hasRequiredFieldError,
                "Error message should indicate required field validation. Actual: " + errorMessage);

        takeScreenshot();
        logger.info("Comprehensive checkout validation test with empty fields completed successfully");
    }

    @Test(description = "Verify successful checkout with valid data", priority = 8)
    @Story("Valid Checkout Flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test validates successful checkout with properly formatted data")
    public void testSuccessfulCheckoutWithValidData() {
        logger.info("Starting successful checkout with valid data test");

        // Step 1: Login and add items to cart
        LoginPage loginPage = new LoginPage(driver, wait);
        String username = TestDataReader.getTestDataAsString("credentials.username");
        String password = TestDataReader.getTestDataAsString("credentials.password");

        InventoryPage inventoryPage = loginPage.login(username, password);

        // Add items to cart
        inventoryPage.addBackpackToCart();
        inventoryPage.addBikeLightToCart();

        // Step 2: Navigate to checkout
        CartPage cartPage = inventoryPage.clickShoppingCartLink();
        CheckoutStepOnePage checkoutPage = cartPage.clickCheckoutButton();

        // Step 3: Fill form with valid data
        checkoutPage.enterFirstName("Emmanuel");
        checkoutPage.enterLastName("Arhu");
        checkoutPage.enterPostalCode("A1B2C3"); // Mixed format - should pass

        // Step 4: Continue to next step
        CheckoutStepTwoPage checkoutStepTwo = checkoutPage.clickContinueButton();

        // Step 5: Verify successful navigation
        Assert.assertTrue(checkoutStepTwo.isCheckoutStepTwoPageLoaded(),
                "Should successfully navigate to checkout step two with valid data");

        // Step 6: Verify checkout overview details
        Assert.assertTrue(checkoutStepTwo.isPaymentInfoDisplayed(),
                "Payment information should be displayed");
        Assert.assertTrue(checkoutStepTwo.isShippingInfoDisplayed(),
                "Shipping information should be displayed");

        // Step 7: Complete the checkout
        CheckoutCompletePage completePage = checkoutStepTwo.clickFinishButton();
        Assert.assertTrue(completePage.isCheckoutCompletePageLoaded(),
                "Should complete checkout successfully");

        takeScreenshot();
        logger.info("Successful checkout with valid data test completed successfully");
    }

    @Test(description = "Verify cancel button functionality on checkout page", priority = 9)
    @Story("Checkout Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test validates cancel button returns user to cart page")
    public void testCheckoutCancelButton() {
        logger.info("Starting checkout cancel button test");

        // Step 1: Login and add items to cart
        LoginPage loginPage = new LoginPage(driver, wait);
        String username = TestDataReader.getTestDataAsString("credentials.username");
        String password = TestDataReader.getTestDataAsString("credentials.password");

        InventoryPage inventoryPage = loginPage.login(username, password);

        // Add items to cart
        inventoryPage.addBackpackToCart();

        // Step 2: Navigate to checkout
        CartPage cartPage = inventoryPage.clickShoppingCartLink();
        CheckoutStepOnePage checkoutPage = cartPage.clickCheckoutButton();

        // Step 3: Verify we're on checkout page
        Assert.assertTrue(checkoutPage.isCheckoutStepOnePageLoaded(),
                "Should be on checkout step one page");

        // Step 4: Click cancel button
        CartPage returnedCartPage = checkoutPage.clickCancelButton();

        // Step 5: Verify we're back on cart page
        Assert.assertTrue(returnedCartPage.isCartPageLoaded(),
                "Should return to cart page after clicking cancel");

        takeScreenshot();
        logger.info("Checkout cancel button test completed successfully");
    }

    /**
     * Helper method to handle any browser popups that might appear
     */
    private void handleAnyPopups() {
        try {
            // Small wait to allow any popups to appear
            Thread.sleep(1000);

            // Handle potential browser popups (like password manager)
            com.swaglabs.utils.AlertHandler.handlePasswordChangeAlert(driver, 3);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.debug("Thread interrupted while handling popups");
        } catch (Exception e) {
            logger.debug("No popups to handle: {}", e.getMessage());
        }
    }
}