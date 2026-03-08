# Deployment Guide for Render

**Simple, step-by-step guide to deploy your Java Compiler API to Render in 5 minutes.**

## Prerequisites

✅ GitHub account (free)  
✅ Render account (free) - Sign up at [render.com](https://render.com)  
✅ Your code pushed to GitHub

---

## 🚀 Quick Deployment (3 Steps)

### Step 1: Push Your Code to GitHub

Open PowerShell in your project folder:

```powershell
# Initialize git (if not done already)
git init

# Add all files
git add .

# Commit
git commit -m "Initial commit - Java Compiler API"

# Create a new repository on GitHub, then:
git remote add origin https://github.com/YOUR_USERNAME/java-compiler-api.git
git branch -M main
git push -u origin main
```

**Important:** Make sure these files are in your repository:
- `pom.xml`
- `system.properties` (sets Java version to 21)
- `render.yaml` (optional, but recommended)
- All source code in `src/` folder

---

### Step 2: Create Web Service on Render

1. **Go to Render Dashboard**
   - Visit: https://dashboard.render.com
   - Sign in (use GitHub to connect)

2. **Click "New +" → "Web Service"**

3. **Connect Your Repository**
   - Click "Configure account" if needed to give Render access to your repos
   - Find and select your `java-compiler-api` repository
   - Click "Connect"

4. **Configure the Service**

   Fill in these **exact** settings:

   | Setting | Value |
   |---------|-------|
   | **Name** | `java-compiler-api` (or any name you want) |
   | **Region** | Choose closest to you (e.g., Singapore for Asia) |
   | **Branch** | `main` |
   | **Root Directory** | **Leave blank** |
   | **Language** | **Select "Docker"** |
   | **Dockerfile Path** | **Leave blank** (defaults to `./Dockerfile`) |

   **✅ That's it!** No build or start commands needed - the Dockerfile handles everything!
   
   **Why Docker?**
   - Render doesn't always show "Java" as an option
   - Using Docker is simpler and more reliable
   - Your project includes a `Dockerfile` that handles the build automatically

5. **Set Instance Type**
   - **Free** (good for testing, spins down after 15 min inactivity)
   - **Starter ($7/month)** (always on, better for production)

6. **Advanced Settings** (Optional but Recommended)
   
   Click "Advanced" button to expand, then add:
   
   **Health Check Path:**
   - `/api/v1/health`
   
   **Environment Variables** (optional):
   - Key: `PORT`
   - Value: `8080`
   
   (Render usually sets this automatically, but you can specify it explicitly)

7. **Click "Create Web Service"**

---

### Step 3: Wait for Deployment

1. **Watch the Build Logs**
   - Render will show live logs
   - You'll see:
     - "Downloading Maven dependencies..."
     - "Compiling Java files..."
     - "BUILD SUCCESS"
     - "Application started"

2. **Deployment Time:** 5-10 minutes (first time)

3. **Your API URL:** 
   - You'll get a URL like: `https://java-compiler-api-xxxx.onrender.com`
   - Render shows it at the top of the dashboard

---

## ✅ Test Your Deployed API

Once deployment shows "Live", test it:

### 1. Health Check

Open in browser or use PowerShell:
```powershell
Invoke-RestMethod -Uri "https://YOUR-APP-NAME.onrender.com/api/v1/health"
```

Should return: `API is running`

### 2. Test Code Execution

```powershell
$json = '{"code":"public class Main { public static void main(String[] args) { System.out.println(\"Deployed to Render!\"); } }","input":""}'; Invoke-RestMethod -Uri "https://YOUR-APP-NAME.onrender.com/api/v1/execute" -Method Post -Body $json -ContentType "application/json"
```

Should return:
```json
{
  "success": true,
  "output": "Deployed to Render!\n",
  "error": "",
  "compilationError": "",
  "executionTimeMs": 123
}
```

### 3. Access Swagger UI

Open in browser:
```
https://YOUR-APP-NAME.onrender.com/swagger-ui.html
```

---

## 🔧 Troubleshooting

### Build Fails with "Java compiler not available"

**Problem:** Render is using JRE instead of JDK

**Solution:** Add `system.properties` file (already included):
```properties
java.runtime.version=21
```

### Can't Find Java in Environment Dropdown

**Problem:** Render doesn't show "Java" as an option

**Solution:** 
- Select **"Docker"** from the dropdown
- Render will auto-detect Java from `pom.xml`
- Or use the `render.yaml` file (already included)

### First Request is Slow (Free Tier)

**Problem:** Free tier spins down after 15 minutes

**Solution:** 
- First request takes 30-60 seconds (cold start)
- This is normal for free tier
- Upgrade to $7/month Starter plan for always-on service

### Build Fails: "Cannot download dependencies"

**Problem:** Maven can't reach repositories

**Solution:**
- Check Render build logs for specific errors
- Usually resolves on retry
- Click "Manual Deploy" → "Clear build cache & deploy"

### Port Binding Error

**Problem:** App can't bind to port

**Solution:**
- Render automatically sets `PORT` environment variable
- Spring Boot uses it automatically
- Make sure you're not hardcoding port 8080 in production

### JAR File Not Found

**Problem:** `target/java-compiler-api-1.0.0.jar` not found

**Solution:**
- Check your `pom.xml` - `<artifactId>` and `<version>` must match
- Current settings: `java-compiler-api` version `1.0.0`
- Start command must match: `java -jar target/java-compiler-api-1.0.0.jar`

---

## 🔄 Updating Your Deployed API

After making changes locally:

```powershell
git add .
git commit -m "Your update message"
git push
```

Render will **automatically redeploy** (if auto-deploy is enabled).

---

## 💰 Render Pricing

| Plan | Price | Features |
|------|-------|----------|
| **Free** | $0 | 750 hours/month, spins down after 15 min, 512MB RAM |
| **Starter** | $7/month | Always on, 512MB RAM, custom domain |
| **Standard** | $25/month | 2GB RAM, better performance |

The **Free** tier is perfect for testing and personal projects.

---

## 🌐 Custom Domain (Optional)

1. Go to your service on Render
2. Click "Settings"
3. Scroll to "Custom Domain"
4. Add your domain (e.g., `api.yourdomain.com`)
5. Update your DNS with the CNAME record shown

---

## 📊 Monitoring

### View Logs
- Go to your service dashboard
- Click "Logs" tab
- See real-time application logs

### Metrics
- Dashboard shows:
  - CPU usage
  - Memory usage
  - Request count
  - Response times

### Set Up Alerts
- Go to "Settings" → "Notifications"
- Add email or Slack webhook
- Get notified of errors or downtime

---

## 🔐 Security for Production

Before going live:

1. **Add Rate Limiting** - Prevent abuse
2. **Add Authentication** - API keys or JWT
3. **Set Environment Variables** - Don't hardcode secrets
4. **Enable HTTPS** - Render provides this automatically
5. **Review Timeout Settings** - Adjust in `application.properties`

---

## 🆘 Still Having Issues?

### Check These Common Problems:

1. ✅ `system.properties` file exists with `java.runtime.version=21`
2. ✅ `pom.xml` has correct artifact name
3. ✅ Start command matches JAR filename exactly
4. ✅ Build command is `mvn clean install -DskipTests`
5. Selected **Docker** (not Node/Python) in environment

### Get Help:

- **Render Docs:** https://render.com/docs
- **Render Community:** https://community.render.com
- **Spring Boot Docs:** https://spring.io/projects/spring-boot
- **Check build logs** for specific error messages

---

## ✨ Success!

Your API is live at: `https://your-app-name.onrender.com`

**Share your API:**
- Swagger UI: `https://your-app-name.onrender.com/swagger-ui.html`
- API Docs: `https://your-app-name.onrender.com/api-docs`
- Health Check: `https://your-app-name.onrender.com/api/v1/health`

**Example API Call:**
```bash
curl -X POST https://your-app-name.onrender.com/api/v1/execute \
  -H "Content-Type: application/json" \
  -d '{"code":"public class Main{public static void main(String[] args){System.out.println(\"Hello!\");}}"}'
```

---

**Need local testing?** See [QUICKSTART.md](QUICKSTART.md)  
**Want examples?** See [EXAMPLES.md](EXAMPLES.md)  
**Full documentation?** See [README.md](README.md)
   - Wait for "Application started" message

2. **Deployment usually takes 5-10 minutes**

### Step 4: Test Your API

Once deployed, you'll get a URL like: `https://java-compiler-api-xxxx.onrender.com`

1. **Test the health endpoint**:
   ```bash
   curl https://your-app-name.onrender.com/api/v1/health
   ```

2. **Test code execution**:
   ```bash
   curl -X POST https://your-app-name.onrender.com/api/v1/execute \
     -H "Content-Type: application/json" \
     -d '{"code":"public class Main{public static void main(String[] args){System.out.println(\"Hello from Render!\");}}"}'
   ```

3. **Access Swagger UI**:
   - Open browser: `https://your-app-name.onrender.com/swagger-ui.html`

## Important Notes

### Free Tier Limitations

- **Spin Down**: Free tier spins down after 15 minutes of inactivity
- **Cold Start**: First request after spin-down takes 30-60 seconds
- **Monthly Limit**: 750 hours per month
- **Memory**: Limited to 512MB RAM

### Upgrading to Paid Tier

For production use, consider upgrading:
- **No spin-down**: Always available
- **More memory**: Better performance
- **Faster builds**: Quicker deployments
- **Custom domain**: Your own domain name
- **Cost**: Starting at $7/month

### Custom Domain (Optional)

1. In Render dashboard, go to your service
2. Click "Settings"
3. Scroll to "Custom Domain"
4. Add your domain and follow DNS instructions

### Environment Variables

You can add/modify environment variables:
1. Go to service settings
2. Click "Environment"
3. Add variables:
   - `JAVA_EXECUTION_TIMEOUT`: Change timeout (default: 10)
   - `MAX_CODE_LENGTH`: Change max code size (default: 10000)

### Monitoring

- **Logs**: View in Render dashboard
- **Metrics**: CPU, memory usage available in dashboard
- **Alerts**: Set up notifications for errors

## Troubleshooting

### Build Fails

**Issue**: Maven build fails
**Solution**: 
- Check logs for specific errors
- Ensure `pom.xml` is correct
- Try building locally first: `mvn clean install`

### Application Won't Start

**Issue**: Build succeeds but app doesn't start
**Solution**:
- Check the JAR file name in start command matches the actual file
- Verify Java version (should be 17)
- Check logs for startup errors

### Out of Memory

**Issue**: App crashes with OOM
**Solution**:
- Add environment variable: `JAVA_TOOL_OPTIONS=-Xmx512m`
- Consider upgrading to paid tier with more memory

### API Not Responding

**Issue**: First request takes forever (free tier)
**Solution**:
- This is normal for free tier after spin-down
- Wait 30-60 seconds for cold start
- Or upgrade to paid tier

### Port Issues

**Issue**: App tries to use wrong port
**Solution**:
- Render sets `PORT` environment variable automatically
- Spring Boot uses it automatically
- Don't hardcode port in `application.properties`

## Updating Your API

1. **Make changes locally**
2. **Commit and push**:
   ```bash
   git add .
   git commit -m "Your update message"
   git push
   ```
3. **Render auto-deploys** (if enabled)
4. **Monitor deployment** in dashboard

## Rollback

If something goes wrong:
1. Go to Render dashboard
2. Click "Events"
3. Find previous deployment
4. Click "Rollback to this version"

## Security for Production

Before going to production:

1. **Add Rate Limiting** (see README)
2. **Add Authentication** (API keys)
3. **Set up monitoring** alerts
4. **Review timeout settings**
5. **Test with malicious code** samples
6. **Consider WAF** (Web Application Firewall)

## Cost Estimation

### Free Tier
- Cost: $0
- Good for: Testing, personal projects
- Limitations: Spin-down, 750 hours/month

### Starter Plan ($7/month)
- No spin-down
- 0.5GB RAM
- Good for: Small projects, demos

### Standard Plan ($25/month)
- 2GB RAM
- Better performance
- Good for: Production apps

## Support

If you encounter issues:
- Check [Render Docs](https://render.com/docs)
- Review build logs
- Check GitHub repository issues
- Contact Render support

---

🎉 **Congratulations!** Your Java Compiler API is now live!

Share your API URL with others:
```
https://your-app-name.onrender.com/swagger-ui.html
```
