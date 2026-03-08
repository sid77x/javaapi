package com.javaapi.controller;

import com.javaapi.model.CodeExecutionRequest;
import com.javaapi.model.CodeExecutionResponse;
import com.javaapi.service.JavaExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Java Compiler API", description = "APIs for compiling and executing Java code")
@CrossOrigin(origins = "*")
public class JavaCompilerController {

    @Autowired
    private JavaExecutionService executionService;

    @PostMapping("/execute")
    @Operation(
        summary = "Compile and execute Java code",
        description = "Accepts Java source code, compiles it, and executes it. Returns the output or any errors."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Code executed successfully or returned compilation/runtime errors",
            content = @Content(schema = @Schema(implementation = CodeExecutionResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request (empty code or exceeds size limit)"
        )
    })
    public ResponseEntity<CodeExecutionResponse> executeCode(
            @Valid @RequestBody CodeExecutionRequest request) {
        
        CodeExecutionResponse response = executionService.executeCode(
            request.getCode(), 
            request.getInput()
        );
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    @Operation(
        summary = "Health check endpoint",
        description = "Returns the health status of the API"
    )
    @ApiResponse(responseCode = "200", description = "API is healthy")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("API is running");
    }

    @GetMapping("/")
    @Operation(
        summary = "Welcome endpoint",
        description = "Returns welcome message with API documentation link"
    )
    public ResponseEntity<WelcomeResponse> welcome() {
        WelcomeResponse response = new WelcomeResponse(
            "Java Compiler API",
            "1.0.0",
            "Welcome to the Java Compiler API! Visit /swagger-ui.html for interactive documentation.",
            "/swagger-ui.html"
        );
        return ResponseEntity.ok(response);
    }

    @Schema(description = "Welcome response")
    public static class WelcomeResponse {
        @Schema(description = "API name", example = "Java Compiler API")
        private String name;
        
        @Schema(description = "API version", example = "1.0.0")
        private String version;
        
        @Schema(description = "Welcome message")
        private String message;
        
        @Schema(description = "Documentation URL", example = "/swagger-ui.html")
        private String documentation;

        public WelcomeResponse(String name, String version, String message, String documentation) {
            this.name = name;
            this.version = version;
            this.message = message;
            this.documentation = documentation;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDocumentation() {
            return documentation;
        }

        public void setDocumentation(String documentation) {
            this.documentation = documentation;
        }
    }
}
