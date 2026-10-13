package com.disney.app.disneycruise.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    // Initialize the Log4j2 Logger
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        // Create a record holding sensitive data
        UserRecord safeUser = new UserRecord(
                "alice_dev",
                "123-456-789",
                "1111-2222-3333-4444",
                "superSecretPwd123!",
                "alice@company.com"
        );

        logger.info("--- Starting Masking Demonstration ---");

        // Log4j calls user.toString() under the hood to fill the '{}'
        logger.info("Successfully fetched user record: {}", safeUser);

        // Notice that the record data itself is still completely accessible and untouched inside your application code
        System.out.println("\n[Verification] Inside memory, raw application data remains intact:");
        System.out.println("Real Password is still: " + safeUser.password());

        logger.info("--- Demonstration Finished ---");
    }
}