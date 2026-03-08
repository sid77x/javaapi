# Java Compiler API - Project Summary

## Overview

A production-ready Spring Boot REST API that compiles and executes Java code dynamically. Perfect for educational platforms, coding challenges, and interactive development environments.

## What This API Does

**Input**: Java source code (as a string)
**Output**: Compilation + execution results (stdout, errors, execution time)

## Key Features

✅ **Dynamic Compilation**: Compiles Java code on-the-fly using Java Compiler API
✅ **Safe Execution**: Timeout protection (10s default), code size limits
✅ **Comprehensive Output**: Returns stdout, compilation errors, or runtime errors
✅ **Input Support**: Pass stdin input to programs
✅ **Interactive Docs**: Built-in Swagger UI for testing
✅ **Production Ready**: CORS support, error handling, validation
✅ **Easy Deployment**: One-click deploy to Render

## API Endpoint

```
POST /api/v1/execute
```

**Request:**
```json
{
  "code": "public class Main { public static void main(String[] args) { System.out.println(\"Hello!\"); } }",
  "input": ""
}
```

**Response:**
```json
{
  "success": true,
  "output": "Hello!\n",
  "error": "",
  "compilationError": "",
  "executionTimeMs": 123
}
```

## Project Structure

```
javaapi/
├── src/main/java/com/javaapi/
│   ├── JavaCompilerApiApplication.java    # Main app
│   ├── controller/
│   │   └── JavaCompilerController.java    # REST endpoints
│   ├── service/
│   │   └── JavaExecutionService.java      # Compilation & execution logic
│   ├── model/
│   │   ├── CodeExecutionRequest.java      # Request model
│   │   └── CodeExecutionResponse.java     # Response model
│   ├── config/
│   │   ├── OpenAPIConfig.java             # Swagger config
│   │   └── CorsConfig.java                # CORS config
│   └── exception/
│       └── GlobalExceptionHandler.java    # Error handling
├── src/main/resources/
│   └── application.properties             # Configuration
├── pom.xml                                # Maven dependencies
├── system.properties                      # Java version for Render
├── render.yaml                            # Render deployment config
├── .gitignore                             # Git ignore rules
├── README.md                              # Full documentation
├── QUICKSTART.md                          # 5-minute setup guide
├── EXAMPLES.md                            # Code examples
├── DEPLOYMENT.md                          # Render deployment guide
└── PROJECT_SUMMARY.md                     # This file
```

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **JDK**: Java 17
- **Build Tool**: Maven
- **API Documentation**: Springdoc OpenAPI (Swagger)
- **Compilation**: Java Compiler API (javax.tools)

## Dependencies

```xml
- spring-boot-starter-web (REST API)
- spring-boot-starter-validation (Input validation)
- springdoc-openapi-starter-webmvc-ui (Swagger)
- lombok (Cleaner code)
```

## How It Works

1. **Receive Request**: Client sends Java code via POST request
2. **Validate Input**: Check code length, not empty
3. **Extract Class Name**: Parse code to find public class name
4. **Create Temp Files**: Write code to temporary directory
5. **Compile**: Use JavaCompiler to compile the code
6. **Execute**: Run compiled class in separate process
7. **Capture Output**: Collect stdout/stderr
8. **Return Results**: Send response with output or errors
9. **Cleanup**: Delete temporary files

## Configuration Options

In `application.properties`:

```properties
server.port=8080                          # API port
java.execution.timeout=10                  # Timeout in seconds
java.execution.max-code-length=10000       # Max code size
```

## Security Features

- ✅ Execution timeout (prevents infinite loops)
- ✅ Code size limits (prevents memory issues)
- ✅ Input validation
- ✅ Temporary file cleanup
- ✅ Process isolation
- ⚠️ **For production**: Add rate limiting, authentication, sandboxing

## Using the API

### Local Development

```bash
# Build
mvn clean install

# Run
mvn spring-boot:run

# Test
curl -X POST http://localhost:8080/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{"code":"public class Main{public static void main(String[] args){System.out.println(\"Test\");}}"}'
```

### Access Documentation

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs
- **Health Check**: http://localhost:8080/api/v1/health

## Deployment

### Render (Recommended)

1. Push code to GitHub
2. Connect repository on Render
3. Render auto-detects `render.yaml`
4. Deploy with one click
5. Get URL: `https://your-app.onrender.com`

**Free Tier**: 750 hours/month, spins down after 15 min inactivity
**Paid Tier**: Always on, starts at $7/month

See [DEPLOYMENT.md](DEPLOYMENT.md) for detailed steps.

## Client Integration Examples

### JavaScript
```javascript
const response = await fetch('API_URL/api/v1/execute', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ code: javaCode, input: inputData })
});
const result = await response.json();
```

### Python
```python
import requests
response = requests.post('API_URL/api/v1/execute', 
  json={'code': java_code, 'input': input_data})
result = response.json()
```

### cURL
```bash
curl -X POST API_URL/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{"code":"...","input":""}'
```

## Response Types

### Success
```json
{
  "success": true,
  "output": "Program output here\n",
  "error": "",
  "compilationError": "",
  "executionTimeMs": 123
}
```

### Compilation Error
```json
{
  "success": false,
  "output": "",
  "error": "",
  "compilationError": "Line 1: cannot find symbol...",
  "executionTimeMs": 0
}
```

### Runtime Error
```json
{
  "success": false,
  "output": "",
  "error": "Program exited with code: 1",
  "compilationError": "",
  "executionTimeMs": 150
}
```

### Timeout
```json
{
  "success": false,
  "output": "",
  "error": "Execution timed out after 10 seconds",
  "compilationError": "",
  "executionTimeMs": 10000
}
```

## Use Cases

🎓 **Education**: Online Java tutorials and courses
🏆 **Competitions**: Coding challenge platforms
💼 **Interviews**: Technical assessment tools
🧪 **Testing**: Quick Java snippet testing
📚 **Documentation**: Interactive code examples

## Limitations

- **JDK Required**: Needs full JDK, not JRE
- **Single Class**: Best for single-class programs
- **Timeout**: Long-running programs will be terminated
- **No External Libraries**: Can't use external JARs (by default)
- **Resource Limits**: Depends on server resources

## Future Enhancements

Potential additions:
- [ ] Rate limiting
- [ ] API key authentication
- [ ] Multiple test cases support
- [ ] Code analysis/metrics
- [ ] Support for multiple files
- [ ] Custom library support
- [ ] Docker sandboxing
- [ ] WebSocket for real-time output

## Documentation Files

- **README.md**: Complete documentation with examples
- **QUICKSTART.md**: Get started in 5 minutes
- **EXAMPLES.md**: Comprehensive usage examples
- **DEPLOYMENT.md**: Step-by-step Render deployment
- **PROJECT_SUMMARY.md**: This overview document

## Quick Commands

```bash
# Build project
mvn clean install

# Run locally
mvn spring-boot:run

# Run JAR directly
java -jar target/java-compiler-api-1.0.0.jar

# Test endpoint
curl http://localhost:8080/api/v1/health

# View logs
# (check terminal output)
```

## Support & Resources

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Spring Boot Docs**: https://spring.io/projects/spring-boot
- **Render Docs**: https://render.com/docs
- **GitHub Issues**: (your repository)

## License

MIT License - Free to use and modify

---

**Ready to deploy?** See [DEPLOYMENT.md](DEPLOYMENT.md)
**Need examples?** See [EXAMPLES.md](EXAMPLES.md)
**Quick start?** See [QUICKSTART.md](QUICKSTART.md)

Built with ❤️ using Spring Boot
