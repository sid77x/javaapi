# Quick Start Guide

Get your Java Compiler API up and running in 5 minutes!

## Prerequisites

- Java 17 or higher (JDK)
- Maven 3.6+

> **Note**: Make sure you have JDK installed, not just JRE. You can verify by running:
> ```bash
> java -version
> javac -version
> ```

## Installation

### 1. Clone or Download

Download the project or clone from your repository.

### 2. Build the Project

```bash
cd javaapi
mvn clean install
```

This will:
- Download all dependencies
- Compile the code
- Run tests (if any)
- Create a JAR file in `target/` directory

### 3. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR directly:
```bash
java -jar target/java-compiler-api-1.0.0.jar
```

### 4. Verify It's Running

Open your browser and visit:
- **Health Check**: http://localhost:8080/api/v1/health
- **Swagger UI**: http://localhost:8080/swagger-ui.html

You should see:
- Health check returns: `API is running`
- Swagger UI shows the interactive API documentation

## First API Call

### Using cURL (Bash/Linux/Mac)

```bash
curl -X POST http://localhost:8080/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class Main { public static void main(String[] args) { System.out.println(\"Hello, World!\"); } }"
  }'
```

### Using PowerShell (Windows)

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/v1/execute" `
  -Method Post `
  -ContentType "application/json" `
  -Body '{"code":"public class Main{public static void main(String[] args){System.out.println(\"Hello, World!\");}}"}' 
```

### Using Swagger UI

1. Go to http://localhost:8080/swagger-ui.html
2. Find the `/api/v1/execute` endpoint
3. Click "Try it out"
4. Enter your Java code in the request body
5. Click "Execute"
6. See the response below!

### Expected Response

```json
{
  "success": true,
  "output": "Hello, World!\n",
  "error": "",
  "compilationError": "",
  "executionTimeMs": 123
}
```

## Common Issues

### "Java compiler not available"

**Problem**: Running with JRE instead of JDK

**Solution**: Install JDK and set JAVA_HOME
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17

# Linux/Mac
export JAVA_HOME=/usr/lib/jvm/java-17
```

### Port 8080 already in use

**Problem**: Another application is using port 8080

**Solution 1**: Stop the other application

**Solution 2**: Change port in `application.properties`:
```properties
server.port=8081
```

### Build fails with "dependencies cannot be resolved"

**Problem**: Maven can't download dependencies

**Solution**: Check internet connection and try:
```bash
mvn clean install -U
```

## Next Steps

1. **Read the full documentation**: See [README.md](README.md)
2. **Try more examples**: See [EXAMPLES.md](EXAMPLES.md)
3. **Deploy to Render**: See [DEPLOYMENT.md](DEPLOYMENT.md)
4. **Explore API**: Visit Swagger UI at http://localhost:8080/swagger-ui.html

## Configuration

Edit `src/main/resources/application.properties` to customize:

```properties
# Change port
server.port=8080

# Change timeout (seconds)
java.execution.timeout=10

# Change max code length
java.execution.max-code-length=10000
```

## Stopping the Application

Press `Ctrl+C` in the terminal where the application is running.

## Development Mode

For development with auto-reload:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Building for Production

Create an optimized JAR for production:

```bash
mvn clean package -DskipTests
```

The JAR will be in `target/java-compiler-api-1.0.0.jar`

## Testing the API

### Postman

1. Import the Swagger JSON: http://localhost:8080/api-docs
2. Or manually create requests to `/api/v1/execute`

### Browser JavaScript Console

```javascript
fetch('http://localhost:8080/api/v1/execute', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    code: 'public class Main { public static void main(String[] args) { System.out.println("Test"); } }'
  })
})
.then(r => r.json())
.then(console.log);
```

## Project Structure

```
javaapi/
├── src/
│   └── main/
│       ├── java/com/javaapi/
│       │   ├── JavaCompilerApiApplication.java  # Main application
│       │   ├── controller/                       # REST endpoints
│       │   ├── service/                          # Business logic
│       │   ├── model/                            # Request/Response models
│       │   ├── config/                           # Configuration
│       │   └── exception/                        # Error handling
│       └── resources/
│           └── application.properties            # Configuration
├── pom.xml                                       # Maven dependencies
├── README.md                                     # Full documentation
├── DEPLOYMENT.md                                 # Deployment guide
├── EXAMPLES.md                                   # Usage examples
└── QUICKSTART.md                                 # This file
```

## Need Help?

- Check [README.md](README.md) for detailed documentation
- Try examples in [EXAMPLES.md](EXAMPLES.md)
- Use Swagger UI for interactive testing
- Review logs in the terminal

---

**You're all set!** 🎉

Start building amazing things with your Java Compiler API!
