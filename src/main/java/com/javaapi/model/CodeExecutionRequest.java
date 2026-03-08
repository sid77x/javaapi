package com.javaapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request object for compiling and executing Java code")
public class CodeExecutionRequest {

    @Schema(description = "Java source code to compile and execute", 
            example = "public class Main { public static void main(String[] args) { System.out.println(\"Hello, World!\"); } }",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Code cannot be empty")
    @Size(max = 10000, message = "Code length cannot exceed 10000 characters")
    private String code;

    @Schema(description = "Optional input to be passed to the program via stdin", 
            example = "John\n25")
    private String input;

    public CodeExecutionRequest() {
    }

    public CodeExecutionRequest(String code, String input) {
        this.code = code;
        this.input = input;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
