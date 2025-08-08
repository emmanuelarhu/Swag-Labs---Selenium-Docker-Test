package com.swaglabs.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class to help with form validation testing
 */
public class FormValidationHelper {
    private static final Logger logger = LoggerFactory.getLogger(FormValidationHelper.class);

    /**
     * Common error message patterns for SwagLabs
     */
    public static final String[] FIRST_NAME_ERROR_PATTERNS = {
            "First Name is required",
            "Error: First Name is required",
            "Please enter your first name",
            "First name cannot be empty"
    };

    public static final String[] LAST_NAME_ERROR_PATTERNS = {
            "Last Name is required",
            "Error: Last Name is required",
            "Please enter your last name",
            "Last name cannot be empty"
    };

    public static final String[] POSTAL_CODE_ERROR_PATTERNS = {
            "Postal Code is required",
            "Error: Postal Code is required",
            "Zip Code is required",
            "Error: Zip Code is required",
            "Please enter your postal code"
    };

    /**
     * Check if error message contains any of the expected patterns
     */
    public static boolean containsAnyPattern(String actualMessage, String[] patterns) {
        if (actualMessage == null || actualMessage.trim().isEmpty()) {
            return false;
        }

        String normalizedMessage = actualMessage.toLowerCase().trim();

        for (String pattern : patterns) {
            if (normalizedMessage.contains(pattern.toLowerCase())) {
                logger.info("Found matching error pattern: '{}' in message: '{}'", pattern, actualMessage);
                return true;
            }
        }

        logger.warn("No matching patterns found in error message: '{}'. Expected patterns: {}",
                actualMessage, Arrays.toString(patterns));
        return false;
    }

    /**
     * Validate that First Name error message is correct
     */
    public static boolean isValidFirstNameError(String errorMessage) {
        return containsAnyPattern(errorMessage, FIRST_NAME_ERROR_PATTERNS);
    }

    /**
     * Validate that Last Name error message is correct
     */
    public static boolean isValidLastNameError(String errorMessage) {
        return containsAnyPattern(errorMessage, LAST_NAME_ERROR_PATTERNS);
    }

    /**
     * Validate that Postal Code error message is correct
     */
    public static boolean isValidPostalCodeError(String errorMessage) {
        return containsAnyPattern(errorMessage, POSTAL_CODE_ERROR_PATTERNS);
    }

    /**
     * Check if any form field has error styling
     */
    public static boolean hasFieldErrorStyling(WebDriver driver, String fieldDataTest) {
        try {
            // Common error styling selectors
            String[] errorSelectors = {
                    String.format("[data-test='%s'].error", fieldDataTest),
                    String.format("[data-test='%s']:invalid", fieldDataTest),
                    String.format("[data-test='%s'][aria-invalid='true']", fieldDataTest),
                    String.format("[data-test='%s'].field-error", fieldDataTest),
                    String.format("[data-test='%s'][class*='error']", fieldDataTest)
            };

            for (String selector : errorSelectors) {
                try {
                    WebElement element = driver.findElement(By.cssSelector(selector));
                    if (element.isDisplayed()) {
                        logger.info("Found error styling on field '{}' with selector: {}", fieldDataTest, selector);
                        return true;
                    }
                } catch (Exception e) {
                    // Continue to next selector
                }
            }

            return false;
        } catch (Exception e) {
            logger.debug("Error checking field error styling for '{}': {}", fieldDataTest, e.getMessage());
            return false;
        }
    }

    /**
     * Get all visible error messages on the page
     */
    public static String getAllErrorMessages(WebDriver driver) {
        StringBuilder allErrors = new StringBuilder();

        // Common error message selectors
        String[] errorSelectors = {
                ".error-message-container",
                "[data-test='error']",
                ".error",
                ".error-banner",
                ".field-error",
                ".validation-error",
                ".form-error"
        };

        for (String selector : errorSelectors) {
            try {
                List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                for (WebElement element : elements) {
                    if (element.isDisplayed()) {
                        String text = element.getText().trim();
                        if (!text.isEmpty() && !allErrors.toString().contains(text)) {
                            if (allErrors.length() > 0) {
                                allErrors.append(" | ");
                            }
                            allErrors.append(text);
                        }
                    }
                }
            } catch (Exception e) {
                // Continue with next selector
            }
        }

        String result = allErrors.toString();
        logger.info("All error messages found: {}", result.isEmpty() ? "None" : result);
        return result;
    }

    /**
     * Check if a postal code format is valid (allows mixed alphanumeric)
     */
    public static boolean isValidPostalCodeFormat(String postalCode) {
        if (postalCode == null || postalCode.trim().isEmpty()) {
            return false;
        }

        // Allow alphanumeric postal codes (common formats)
        // Examples: 12345, K1A0A6, SW1A 1AA, 12345-6789
        String normalized = postalCode.replaceAll("[\\s-]", ""); // Remove spaces and hyphens
        return normalized.matches("^[a-zA-Z0-9]{3,10}$");
    }

    /**
     * Generate test data for different validation scenarios
     */
    public static class TestData {
        public static final String EMPTY_STRING = "";
        public static final String VALID_FIRST_NAME = "Emmanuel";
        public static final String VALID_LAST_NAME = "Arhu";
        public static final String VALID_POSTAL_CODE_NUMERIC = "12345";
        public static final String VALID_POSTAL_CODE_MIXED = "K1A0A6";
        public static final String VALID_POSTAL_CODE_WITH_DASH = "12345-6789";
        public static final String INVALID_POSTAL_CODE_TOO_SHORT = "12";
        public static final String INVALID_POSTAL_CODE_TOO_LONG = "123456789012345";
        public static final String SPECIAL_CHARACTERS = "!@#$%";
        public static final String NUMBERS_ONLY = "12345";
        public static final String LETTERS_ONLY = "ABCDE";
    }

    /**
     * Validation scenarios for comprehensive testing
     */
    public static class ValidationScenarios {

        public static class EmptyFields {
            public String firstName = TestData.EMPTY_STRING;
            public String lastName = TestData.EMPTY_STRING;
            public String postalCode = TestData.EMPTY_STRING;
            public String expectedError = "required";
        }

        public static class ValidData {
            public String firstName = TestData.VALID_FIRST_NAME;
            public String lastName = TestData.VALID_LAST_NAME;
            public String postalCode = TestData.VALID_POSTAL_CODE_MIXED;
            public boolean shouldPass = true;
        }

        public static class EmptyFirstName {
            public String firstName = TestData.EMPTY_STRING;
            public String lastName = TestData.VALID_LAST_NAME;
            public String postalCode = TestData.VALID_POSTAL_CODE_NUMERIC;
            public String expectedError = "First Name is required";
        }

        public static class EmptyLastName {
            public String firstName = TestData.VALID_FIRST_NAME;
            public String lastName = TestData.EMPTY_STRING;
            public String postalCode = TestData.VALID_POSTAL_CODE_NUMERIC;
            public String expectedError = "Last Name is required";
        }

        public static class EmptyPostalCode {
            public String firstName = TestData.VALID_FIRST_NAME;
            public String lastName = TestData.VALID_LAST_NAME;
            public String postalCode = TestData.EMPTY_STRING;
            public String expectedError = "Postal Code is required";
        }
    }

    /**
     * Wait for error message to appear with timeout
     */
    public static boolean waitForErrorMessage(WebDriver driver, int timeoutSeconds) {
        try {
            for (int i = 0; i < timeoutSeconds * 2; i++) {
                String errorMessage = getAllErrorMessages(driver);
                if (!errorMessage.isEmpty()) {
                    return true;
                }
                Thread.sleep(500);
            }
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}