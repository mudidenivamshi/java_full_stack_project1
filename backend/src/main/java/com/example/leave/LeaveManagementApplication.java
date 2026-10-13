package com.example.leave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Employee Leave Management System.
 *
 * @SpringBootApplication enables:
 *   - @Configuration     : marks this class as a source of bean definitions
 *   - @EnableAutoConfiguration : auto-configures Spring based on dependencies
 *   - @ComponentScan    : scans sub-packages for Spring components
 */
@SpringBootApplication
public class LeaveManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeaveManagementApplication.class, args);
    }
}
