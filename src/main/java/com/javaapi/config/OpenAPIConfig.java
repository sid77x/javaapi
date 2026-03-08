package com.javaapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Java Compiler API")
                        .version("1.0.0")
                        .description("""
                            # Java Compiler API
                            
                            A RESTful API that compiles and executes Java code dynamically.
                            
                            ## Features
                            - Compile Java code on the fly
                            - Execute compiled code with optional stdin input
                            - Get execution output, compilation errors, or runtime errors
                            - Automatic timeout protection
                            - Code size validation
                            
                            ## Usage
                            Send a POST request to `/api/v1/execute` with your Java code in the request body.
                            
                            ## Example Request
                            ```json
                            {
                              "code": "public class Main { public static void main(String[] args) { System.out.println(\\"Hello, World!\\"); } }",
                              "input": ""
                            }
                            ```
                            
                            ## Example Response
                            ```json
                            {
                              "success": true,
                              "output": "Hello, World!\\n",
                              "error": "",
                              "compilationError": "",
                              "executionTimeMs": 123
                            }
                            ```
                            """)
                        .contact(new Contact()
                                .name("Java Compiler API")
                                .email("support@example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Local server"),
                        new Server()
                                .url("https://your-app.onrender.com")
                                .description("Production server on Render")
                ));
    }
}
