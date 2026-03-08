package com.javaapi.service;

import com.javaapi.model.CodeExecutionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.tools.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JavaExecutionService {

    @Value("${java.execution.timeout:10}")
    private int timeoutSeconds;

    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("public\\s+class\\s+(\\w+)");

    /**
     * Compiles and executes the provided Java code
     */
    public CodeExecutionResponse executeCode(String code, String input) {
        long startTime = System.currentTimeMillis();
        Path tempDir = null;

        try {
            // Extract class name from code
            String className = extractClassName(code);
            if (className == null) {
                return CodeExecutionResponse.compilationError("Could not find public class declaration. Make sure your code contains 'public class ClassName'");
            }

            // Create temporary directory
            tempDir = Files.createTempDirectory("java-exec-");
            Path sourceFile = tempDir.resolve(className + ".java");
            
            // Write source code to file
            Files.writeString(sourceFile, code);

            // Compile the code
            String compilationError = compileCode(sourceFile);
            if (compilationError != null) {
                return CodeExecutionResponse.compilationError(compilationError);
            }

            // Execute the compiled code
            return executeCompiledCode(tempDir, className, input, startTime);

        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            return CodeExecutionResponse.runtimeError("Internal error: " + e.getMessage(), executionTime);
        } finally {
            // Cleanup temporary directory
            if (tempDir != null) {
                cleanupDirectory(tempDir);
            }
        }
    }

    /**
     * Extracts the public class name from Java code
     */
    private String extractClassName(String code) {
        Matcher matcher = CLASS_NAME_PATTERN.matcher(code);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * Compiles the Java source file
     */
    private String compileCode(Path sourceFile) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            return "Java compiler not available. Make sure you're running with JDK, not JRE.";
        }

        // Capture compilation errors
        StringWriter errorWriter = new StringWriter();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        
        try {
            Iterable<? extends JavaFileObject> compilationUnits = 
                fileManager.getJavaFileObjectsFromFiles(Collections.singletonList(sourceFile.toFile()));
            
            JavaCompiler.CompilationTask task = compiler.getTask(
                errorWriter,
                fileManager,
                diagnostics,
                null,
                null,
                compilationUnits
            );

            boolean success = task.call();
            fileManager.close();

            if (!success) {
                StringBuilder errorMsg = new StringBuilder();
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    errorMsg.append(String.format("Line %d: %s\n", 
                        diagnostic.getLineNumber(), 
                        diagnostic.getMessage(null)));
                }
                return errorMsg.toString();
            }

            return null; // No errors
        } catch (Exception e) {
            return "Compilation failed: " + e.getMessage();
        }
    }

    /**
     * Executes the compiled Java class
     */
    private CodeExecutionResponse executeCompiledCode(Path tempDir, String className, String input, long startTime) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        try {
            Future<ExecutionResult> future = executor.submit(() -> {
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder(
                        "java", "-cp", tempDir.toString(), className
                    );
                    processBuilder.redirectErrorStream(true);
                    
                    Process process = processBuilder.start();

                    // Provide input if specified
                    if (input != null && !input.isEmpty()) {
                        try (OutputStream os = process.getOutputStream();
                             PrintWriter writer = new PrintWriter(os)) {
                            writer.println(input);
                            writer.flush();
                        }
                    }

                    // Read output
                    StringBuilder output = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            output.append(line).append("\n");
                        }
                    }

                    // Wait for process to complete
                    boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
                    
                    if (!finished) {
                        process.destroyForcibly();
                        return new ExecutionResult(false, "", "Execution timed out after " + timeoutSeconds + " seconds");
                    }

                    int exitCode = process.exitValue();
                    if (exitCode != 0) {
                        return new ExecutionResult(false, output.toString(), "Program exited with code: " + exitCode);
                    }

                    return new ExecutionResult(true, output.toString(), null);

                } catch (Exception e) {
                    return new ExecutionResult(false, "", "Execution error: " + e.getMessage());
                }
            });

            ExecutionResult result = future.get(timeoutSeconds + 2, TimeUnit.SECONDS);
            long executionTime = System.currentTimeMillis() - startTime;

            if (result.success) {
                return CodeExecutionResponse.success(result.output, executionTime);
            } else {
                return CodeExecutionResponse.runtimeError(result.error, executionTime);
            }

        } catch (TimeoutException e) {
            long executionTime = System.currentTimeMillis() - startTime;
            return CodeExecutionResponse.runtimeError("Execution timed out", executionTime);
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            return CodeExecutionResponse.runtimeError("Error: " + e.getMessage(), executionTime);
        } finally {
            executor.shutdownNow();
        }
    }

    /**
     * Cleans up temporary directory
     */
    private void cleanupDirectory(Path directory) {
        try {
            Files.walk(directory)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        // Ignore cleanup errors
                    }
                });
        } catch (IOException e) {
            // Ignore cleanup errors
        }
    }

    /**
     * Helper class to store execution results
     */
    private static class ExecutionResult {
        final boolean success;
        final String output;
        final String error;

        ExecutionResult(boolean success, String output, String error) {
            this.success = success;
            this.output = output;
            this.error = error;
        }
    }
}
