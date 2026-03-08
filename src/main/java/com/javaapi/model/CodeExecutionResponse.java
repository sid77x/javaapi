package com.javaapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object containing execution results")
public class CodeExecutionResponse {

    @Schema(description = "Indicates whether the code executed successfully", example = "true")
    private boolean success;

    @Schema(description = "Standard output from the program", example = "Hello, World!")
    private String output;

    @Schema(description = "Error message if compilation or execution failed", example = "")
    private String error;

    @Schema(description = "Compilation errors if any", example = "")
    private String compilationError;

    @Schema(description = "Execution time in milliseconds", example = "123")
    private long executionTimeMs;

    public CodeExecutionResponse() {
    }

    public CodeExecutionResponse(boolean success, String output, String error, String compilationError, long executionTimeMs) {
        this.success = success;
        this.output = output;
        this.error = error;
        this.compilationError = compilationError;
        this.executionTimeMs = executionTimeMs;
    }

    public static CodeExecutionResponse success(String output, long executionTimeMs) {
        return new CodeExecutionResponse(true, output, "", "", executionTimeMs);
    }

    public static CodeExecutionResponse compilationError(String compilationError) {
        return new CodeExecutionResponse(false, "", "", compilationError, 0);
    }

    public static CodeExecutionResponse runtimeError(String error, long executionTimeMs) {
        return new CodeExecutionResponse(false, "", error, "", executionTimeMs);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCompilationError() {
        return compilationError;
    }

    public void setCompilationError(String compilationError) {
        this.compilationError = compilationError;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }
}
