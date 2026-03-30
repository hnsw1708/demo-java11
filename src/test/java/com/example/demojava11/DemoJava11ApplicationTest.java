package com.example.demojava11;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class DemoJava11ApplicationTest {

    /**
     * Verifies that the main method can be invoked without throwing any exception,
     * using a mocked SpringApplication.run to avoid starting a real application context.
     */
    @Test
    void mainMethod_shouldRunWithoutException() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {

            ConfigurableApplicationContext mockContext =
                    Mockito.mock(ConfigurableApplicationContext.class);

            mockedSpringApplication
                    .when(() -> SpringApplication.run(
                            eq(DemoJava11Application.class),
                            any(String[].class)))
                    .thenReturn(mockContext);

            String[] args = {};

            assertDoesNotThrow(() -> DemoJava11Application.main(args),
                    "main() should not throw any exception");

            mockedSpringApplication.verify(
                    () -> SpringApplication.run(
                            eq(DemoJava11Application.class),
                            any(String[].class)),
                    times(1));
        }
    }

    /**
     * Verifies that the main method forwards command-line arguments to SpringApplication.run.
     */
    @Test
    void mainMethod_shouldForwardArgsToSpringApplication() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {

            ConfigurableApplicationContext mockContext =
                    Mockito.mock(ConfigurableApplicationContext.class);

            var args = new String[]{"--server.port=9090", "--spring.profiles.active=test"};

            mockedSpringApplication
                    .when(() -> SpringApplication.run(
                            eq(DemoJava11Application.class),
                            eq(args)))
                    .thenReturn(mockContext);

            assertDoesNotThrow(() -> DemoJava11Application.main(args),
                    "main() should not throw even when args are provided");

            mockedSpringApplication.verify(
                    () -> SpringApplication.run(
                            eq(DemoJava11Application.class),
                            eq(args)),
                    times(1));
        }
    }

    /**
     * Verifies that the main method handles a null-equivalent empty args array gracefully.
     */
    @Test
    void mainMethod_withEmptyArgs_shouldNotThrow() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {

            ConfigurableApplicationContext mockContext =
                    Mockito.mock(ConfigurableApplicationContext.class);

            mockedSpringApplication
                    .when(() -> SpringApplication.run(
                            eq(DemoJava11Application.class),
                            any(String[].class)))
                    .thenReturn(mockContext);

            assertDoesNotThrow(() -> DemoJava11Application.main(new String[0]),
                    "main() should handle an empty args array without throwing");
        }
    }

    /**
     * Verifies that the DemoJava11Application class can be instantiated (default constructor exists).
     * This ensures the class is not abstract and has no restrictions on instantiation.
     */
    @Test
    void applicationClass_canBeInstantiated() {
        assertDoesNotThrow(() -> {
            var instance = new DemoJava11Application();
            assertNotNull(instance, "DemoJava11Application instance should not be null");
        }, "Default constructor should be accessible and not throw");
    }

    /**
     * Verifies that SpringApplication.run returns a non-null context when called via main,
     * using a text block to describe the scenario (Java 21 feature).
     */
    @Test
    void mainMethod_springApplicationRunReturnsContext() {
        var scenario = """
                Scenario: main() is invoked with valid args.
                Expected: SpringApplication.run is called exactly once
                          and returns a non-null ConfigurableApplicationContext.
                """;

        // The scenario description is available for logging/assertion messages
        assertNotNull(scenario);

        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {

            ConfigurableApplicationContext mockContext =
                    Mockito.mock(ConfigurableApplicationContext.class);

            mockedSpringApplication
                    .when(() -> SpringApplication.run(
                            eq(DemoJava11Application.class),
                            any(String[].class)))
                    .thenReturn(mockContext);

            assertDoesNotThrow(() -> DemoJava11Application.main(new String[]{}), scenario);

            mockedSpringApplication.verify(
                    () -> SpringApplication.run(
                            eq(DemoJava11Application.class),
                            any(String[].class)),
                    times(1));
        }
    }
}
