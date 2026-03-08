# Java Compiler API 🚀

A RESTful API service that compiles and executes Java code dynamically. Perfect for online code judges, educational platforms, or any application that needs to run Java code on the fly.

## 🌟 Features

- **Dynamic Compilation**: Compile Java code on-the-fly
- **Safe Execution**: Timeout protection and resource limits
- **Detailed Output**: Get stdout, compilation errors, or runtime errors
- **Input Support**: Pass stdin input to your programs
- **Interactive Documentation**: Built-in Swagger UI
- **Easy Deployment**: Ready to deploy on Render or any cloud platform

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher (JDK, not JRE)
- Maven 3.6+

### Local Development

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd javaapi
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the API**
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - API Docs: `http://localhost:8080/api-docs`

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/v1
```

### Endpoints

#### 1. Execute Java Code

**POST** `/api/v1/execute`

Compiles and executes Java code, returning the output or any errors.

**Request Body:**
```json
{
  "code": "public class Main { public static void main(String[] args) { System.out.println(\"Hello, World!\"); } }",
  "input": ""
}
```

**Response:**
```json
{
  "success": true,
  "output": "Hello, World!\n",
  "error": "",
  "compilationError": "",
  "executionTimeMs": 123
}
```

**Fields:**
- `code` (required): Java source code with a public class containing a main method
- `input` (optional): Input to be passed to the program via stdin

#### 2. Health Check

**GET** `/api/v1/health`

Returns the health status of the API.

**Response:**
```
API is running
```

#### 3. Welcome

**GET** `/api/v1/`

Returns welcome message and documentation link.

## 💡 Usage Examples

### Example 1: Hello World

**Request:**
```bash
curl -X POST http://localhost:8080/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class Main { public static void main(String[] args) { System.out.println(\"Hello, World!\"); } }"
  }'
```

**Response:**
```json
{
  "success": true,
  "output": "Hello, World!\n",
  "error": "",
  "compilationError": "",
  "executionTimeMs": 105
}
```

### Example 2: Program with Input

**Request:**
```bash
curl -X POST http://localhost:8080/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "import java.util.Scanner; public class Main { public static void main(String[] args) { Scanner sc = new Scanner(System.in); String name = sc.nextLine(); System.out.println(\"Hello, \" + name + \"!\"); } }",
    "input": "Alice"
  }'
```

**Response:**
```json
{
  "success": true,
  "output": "Hello, Alice!\n",
  "error": "",
  "compilationError": "",
  "executionTimeMs": 150
}
```

### Example 3: Compilation Error

**Request:**
```bash
curl -X POST http://localhost:8080/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class Main { public static void main(String[] args) { System.out.println(x); } }"
  }'
```

**Response:**
```json
{
  "success": false,
  "output": "",
  "error": "",
  "compilationError": "Line 1: cannot find symbol\n  symbol:   variable x\n  location: class Main\n",
  "executionTimeMs": 0
}
```

### Example 4: Runtime Error

**Request:**
```bash
curl -X POST http://localhost:8080/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class Main { public static void main(String[] args) { int[] arr = new int[5]; System.out.println(arr[10]); } }"
  }'
```

**Response:**
```json
{
  "success": false,
  "output": "",
  "error": "Program exited with code: 1",
  "compilationError": "",
  "executionTimeMs": 120
}
```

### Example 5: Using JavaScript/Python

**JavaScript (using fetch):**
```javascript
const response = await fetch('http://localhost:8080/api/v1/execute', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    code: 'public class Main { public static void main(String[] args) { System.out.println("Hello from JS!"); } }',
    input: ''
  })
});

const result = await response.json();
console.log(result.output);
```

**Python:**
```python
import requests
import json

url = 'http://localhost:8080/api/v1/execute'
data = {
    'code': 'public class Main { public static void main(String[] args) { System.out.println("Hello from Python!"); } }',
    'input': ''
}

response = requests.post(url, json=data)
result = response.json()
print(result['output'])
```

## ⚙️ Configuration

Edit `src/main/resources/application.properties` to customize:

```properties
# Server port
server.port=8080

# Execution timeout (seconds)
java.execution.timeout=10

# Maximum code length (characters)
java.execution.max-code-length=10000
```

## 🌐 Deploying to Render

1. **Push your code to GitHub**
   ```bash
   git init
   git add .
   git commit -m "Initial commit"
   git remote add origin <your-github-repo-url>
   git push -u origin main
   ```

2. **Create a new Web Service on Render**
   - Go to [Render Dashboard](https://dashboard.render.com/)
   - Click "New +" → "Web Service"
   - Connect your GitHub repository
   - Configure the service:
     - **Name**: `java-compiler-api` (or your preferred name)
     - **Environment**: `Java`
     - **Build Command**: `mvn clean install`
     - **Start Command**: `java -jar target/java-compiler-api-1.0.0.jar`
     - **Instance Type**: Free (or your preferred tier)

3. **Environment Variables** (Optional)
   - Add any custom environment variables if needed

4. **Deploy**
   - Click "Create Web Service"
   - Render will automatically build and deploy your API
   - Your API will be available at: `https://your-app-name.onrender.com`

5. **Test your deployed API**
   ```bash
   curl -X POST https://your-app-name.onrender.com/api/v1/execute \
     -H "Content-Type: application/json" \
     -d '{"code": "public class Main { public static void main(String[] args) { System.out.println(\"Deployed!\"); } }"}'
   ```

6. **Access Swagger UI**
   - Visit: `https://your-app-name.onrender.com/swagger-ui.html`

### Important Notes for Render Deployment

- **Free Tier**: The free tier on Render spins down after 15 minutes of inactivity. First request after spin-down may take 30-60 seconds.
- **Java Version**: Render uses Java 17 by default. If you need a different version, add a `system.properties` file (already included in this project).
- **Logs**: View logs in the Render dashboard to debug any issues.

## 🛡️ Security Considerations

**⚠️ Important**: This API executes arbitrary Java code. Consider these security measures:

- **Timeout**: Set appropriate execution timeouts (default: 10 seconds)
- **Code Size Limit**: Limit maximum code length (default: 10,000 characters)
- **Rate Limiting**: Implement rate limiting in production
- **Authentication**: Add API key authentication for production use
- **Resource Limits**: Run in a containerized environment with resource constraints
- **Input Validation**: The API validates input but consider additional checks
- **Sandboxing**: For production, consider using Docker containers or Java SecurityManager

## 🧪 Testing

Run tests using Maven:

```bash
mvn test
```

## 📊 Response Fields

| Field | Type | Description |
|-------|------|-------------|
| `success` | boolean | Whether code executed successfully |
| `output` | string | Standard output from the program |
| `error` | string | Runtime error message if any |
| `compilationError` | string | Compilation error message if any |
| `executionTimeMs` | long | Total execution time in milliseconds |

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License.

## 📞 Support

For issues and questions:
- Open an issue on GitHub
- Check the Swagger documentation at `/swagger-ui.html`

## 🎯 Use Cases

- **Online Code Judges**: Build competitive programming platforms
- **Educational Platforms**: Let students run code in browser
- **Code Playgrounds**: Create interactive Java tutorials
- **Interview Platforms**: Build technical assessment tools
- **API Testing**: Test Java code snippets quickly

## 🔧 Troubleshooting

### "Java compiler not available"
- Make sure you're running with JDK, not JRE
- Verify `JAVA_HOME` points to JDK installation

### Timeout errors
- Increase timeout in `application.properties`
- Check for infinite loops in code

### Port already in use
- Change port in `application.properties`
- Or kill the process using the port

---

Made with ❤️ for the developer community
