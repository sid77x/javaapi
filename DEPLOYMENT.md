# Deployment Guide for Render

This guide walks you through deploying the Java Compiler API to Render.

## Prerequisites

1. A [GitHub](https://github.com) account
2. A [Render](https://render.com) account (free tier available)
3. Your code pushed to a GitHub repository

## Step-by-Step Deployment

### Step 1: Prepare Your Repository

1. **Initialize Git** (if not already done):
   ```bash
   git init
   ```

2. **Add all files**:
   ```bash
   git add .
   ```

3. **Commit your changes**:
   ```bash
   git commit -m "Initial commit - Java Compiler API"
   ```

4. **Create a GitHub repository**:
   - Go to [GitHub](https://github.com/new)
   - Create a new repository (e.g., `java-compiler-api`)
   - Don't initialize with README (you already have one)

5. **Push to GitHub**:
   ```bash
   git remote add origin https://github.com/YOUR_USERNAME/java-compiler-api.git
   git branch -M main
   git push -u origin main
   ```

### Step 2: Deploy on Render

#### Option A: Using render.yaml (Recommended)

1. **Login to Render**:
   - Go to [render.com](https://render.com)
   - Sign in with your GitHub account

2. **Create New Web Service**:
   - Click "New +" button
   - Select "Web Service"

3. **Connect Repository**:
   - Find and select your `java-compiler-api` repository
   - Click "Connect"

4. **Configure Service**:
   - Render will automatically detect `render.yaml`
   - It will use the settings from that file
   - Review and confirm the configuration

5. **Deploy**:
   - Click "Create Web Service"
   - Render will start building your application
   - Wait for deployment to complete (5-10 minutes)

#### Option B: Manual Configuration

If `render.yaml` isn't detected or you prefer manual setup:

1. **After connecting repository, configure**:
   - **Name**: `java-compiler-api`
   - **Environment**: `Java`
   - **Region**: Choose closest to your users
   - **Branch**: `main`
   - **Build Command**: `mvn clean install -DskipTests`
   - **Start Command**: `java -jar target/java-compiler-api-1.0.0.jar`

2. **Advanced Settings**:
   - **Health Check Path**: `/api/v1/health`
   - **Auto-Deploy**: Yes (recommended)

3. **Environment Variables** (Optional):
   Add these if needed:
   ```
   JAVA_TOOL_OPTIONS=-Xmx512m
   ```

4. **Instance Type**:
   - Select "Free" for testing
   - Upgrade to paid tier for production use

5. **Click "Create Web Service"**

### Step 3: Monitor Deployment

1. **Watch the logs** in real-time:
   - Render will show build logs
   - Wait for "Maven build success"
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
