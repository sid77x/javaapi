# API Usage Examples

Complete collection of examples for using the Java Compiler API.

## Table of Contents
- [Basic Examples](#basic-examples)
- [Advanced Examples](#advanced-examples)
- [Integration Examples](#integration-examples)
- [Error Handling](#error-handling)

## Basic Examples

### 1. Hello World

```bash
curl -X POST http://localhost:8080/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class Main { public static void main(String[] args) { System.out.println(\"Hello, World!\"); } }"
  }'
```

### 2. Simple Math

```bash
curl -X POST http://localhost:8080/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class Main { public static void main(String[] args) { int sum = 5 + 3; System.out.println(\"Sum: \" + sum); } }"
  }'
```

### 3. Using Scanner with Input

```bash
curl -X POST http://localhost:8080/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "import java.util.Scanner; public class Main { public static void main(String[] args) { Scanner sc = new Scanner(System.in); String name = sc.nextLine(); int age = sc.nextInt(); System.out.println(name + \" is \" + age + \" years old\"); } }",
    "input": "Alice\n25"
  }'
```

## Advanced Examples

### 4. Fibonacci Series

```bash
curl -X POST http://localhost:8080/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class Main { public static void main(String[] args) { int n1 = 0, n2 = 1; System.out.print(\"Fibonacci Series: \" + n1 + \" \" + n2); for (int i = 2; i < 10; i++) { int n3 = n1 + n2; System.out.print(\" \" + n3); n1 = n2; n2 = n3; } } }"
  }'
```

### 5. Array Operations

```bash
curl -X POST http://localhost:8080/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class Main { public static void main(String[] args) { int[] numbers = {5, 2, 8, 1, 9}; int max = numbers[0]; for (int num : numbers) { if (num > max) max = num; } System.out.println(\"Maximum: \" + max); } }"
  }'
```

### 6. String Manipulation

```bash
curl -X POST http://localhost:8080/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class Main { public static void main(String[] args) { String text = \"Hello World\"; System.out.println(\"Uppercase: \" + text.toUpperCase()); System.out.println(\"Reversed: \" + new StringBuilder(text).reverse().toString()); System.out.println(\"Length: \" + text.length()); } }"
  }'
```

### 7. Prime Number Check

```bash
curl -X POST http://localhost:8080/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "import java.util.Scanner; public class Main { public static void main(String[] args) { Scanner sc = new Scanner(System.in); int num = sc.nextInt(); boolean isPrime = num > 1; for (int i = 2; i <= Math.sqrt(num); i++) { if (num % i == 0) { isPrime = false; break; } } System.out.println(num + \" is \" + (isPrime ? \"prime\" : \"not prime\")); } }",
    "input": "17"
  }'
```

### 8. Sorting Array

```bash
curl -X POST http://localhost:8080/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{
    "code": "import java.util.Arrays; public class Main { public static void main(String[] args) { int[] arr = {64, 34, 25, 12, 22, 11, 90}; System.out.println(\"Original: \" + Arrays.toString(arr)); Arrays.sort(arr); System.out.println(\"Sorted: \" + Arrays.toString(arr)); } }"
  }'
```

## Integration Examples

### JavaScript (Browser)

```javascript
async function runJavaCode(code, input = '') {
  const response = await fetch('http://localhost:8080/api/v1/execute', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ code, input })
  });
  
  const result = await response.json();
  
  if (result.success) {
    console.log('Output:', result.output);
  } else if (result.compilationError) {
    console.error('Compilation Error:', result.compilationError);
  } else {
    console.error('Runtime Error:', result.error);
  }
  
  return result;
}

// Usage
const code = `
  public class Main {
    public static void main(String[] args) {
      System.out.println("Hello from JavaScript!");
    }
  }
`;

runJavaCode(code);
```

### Python

```python
import requests
import json

def run_java_code(code, input_data=''):
    url = 'http://localhost:8080/api/v1/execute'
    payload = {
        'code': code,
        'input': input_data
    }
    
    response = requests.post(url, json=payload)
    result = response.json()
    
    if result['success']:
        print('Output:', result['output'])
    elif result['compilationError']:
        print('Compilation Error:', result['compilationError'])
    else:
        print('Runtime Error:', result['error'])
    
    return result

# Usage
code = """
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello from Python!");
    }
}
"""

run_java_code(code)
```

### Node.js

```javascript
const axios = require('axios');

async function runJavaCode(code, input = '') {
  try {
    const response = await axios.post('http://localhost:8080/api/v1/execute', {
      code: code,
      input: input
    });
    
    const result = response.data;
    
    if (result.success) {
      console.log('Output:', result.output);
    } else if (result.compilationError) {
      console.error('Compilation Error:', result.compilationError);
    } else {
      console.error('Runtime Error:', result.error);
    }
    
    return result;
  } catch (error) {
    console.error('Request failed:', error.message);
  }
}

// Usage
const code = `
  public class Main {
    public static void main(String[] args) {
      System.out.println("Hello from Node.js!");
    }
  }
`;

runJavaCode(code);
```

### React Component

```jsx
import React, { useState } from 'react';

function JavaCodeRunner() {
  const [code, setCode] = useState('');
  const [input, setInput] = useState('');
  const [output, setOutput] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const runCode = async () => {
    setLoading(true);
    setOutput('');
    setError('');

    try {
      const response = await fetch('http://localhost:8080/api/v1/execute', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ code, input })
      });

      const result = await response.json();

      if (result.success) {
        setOutput(result.output);
      } else if (result.compilationError) {
        setError(result.compilationError);
      } else {
        setError(result.error);
      }
    } catch (err) {
      setError('Failed to connect to API: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <textarea
        value={code}
        onChange={(e) => setCode(e.target.value)}
        placeholder="Enter Java code..."
        rows={10}
      />
      <textarea
        value={input}
        onChange={(e) => setInput(e.target.value)}
        placeholder="Enter input (optional)..."
        rows={3}
      />
      <button onClick={runCode} disabled={loading}>
        {loading ? 'Running...' : 'Run Code'}
      </button>
      
      {output && (
        <div>
          <h3>Output:</h3>
          <pre>{output}</pre>
        </div>
      )}
      
      {error && (
        <div style={{ color: 'red' }}>
          <h3>Error:</h3>
          <pre>{error}</pre>
        </div>
      )}
    </div>
  );
}

export default JavaCodeRunner;
```

## Error Handling

### Handling Compilation Errors

```javascript
const result = await runJavaCode('public class Main { invalid code }');

if (!result.success && result.compilationError) {
  // Parse and display compilation errors
  console.log('Compilation failed:');
  console.log(result.compilationError);
}
```

### Handling Runtime Errors

```javascript
const result = await runJavaCode(`
  public class Main {
    public static void main(String[] args) {
      int[] arr = new int[5];
      System.out.println(arr[10]); // ArrayIndexOutOfBoundsException
    }
  }
`);

if (!result.success && result.error) {
  console.log('Runtime error:', result.error);
}
```

### Handling Timeouts

```javascript
// Code with infinite loop
const result = await runJavaCode(`
  public class Main {
    public static void main(String[] args) {
      while (true) {
        // Infinite loop
      }
    }
  }
`);

if (!result.success && result.error.includes('timeout')) {
  console.log('Code execution timed out');
}
```

## Testing with Postman

1. **Create a new request**
   - Method: POST
   - URL: `http://localhost:8080/api/v1/execute`

2. **Headers**
   - Content-Type: application/json

3. **Body** (raw JSON)
   ```json
   {
     "code": "public class Main { public static void main(String[] args) { System.out.println(\"Test\"); } }",
     "input": ""
   }
   ```

4. **Send** and view response

## Testing with cURL (Windows PowerShell)

```powershell
$body = @{
    code = "public class Main { public static void main(String[] args) { System.out.println(`"Hello`"); } }"
    input = ""
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/v1/execute" -Method Post -Body $body -ContentType "application/json"
```

## Rate Limiting Example (For Production)

If you implement rate limiting, handle it like this:

```javascript
async function runCodeWithRetry(code, input, maxRetries = 3) {
  for (let i = 0; i < maxRetries; i++) {
    try {
      const response = await fetch('http://localhost:8080/api/v1/execute', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ code, input })
      });

      if (response.status === 429) {
        // Rate limited, wait and retry
        await new Promise(resolve => setTimeout(resolve, 1000 * (i + 1)));
        continue;
      }

      return await response.json();
    } catch (error) {
      if (i === maxRetries - 1) throw error;
    }
  }
}
```

---

For more examples, visit the Swagger UI at `/swagger-ui.html`
