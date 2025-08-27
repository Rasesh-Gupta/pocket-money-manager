# 🚀 GitHub Setup for Automatic APK Building

## Step 1: Create GitHub Repository

1. Go to https://github.com
2. Click "New repository"
3. Name it: `pocket-money-manager`
4. Make it Public (for free Actions)
5. Click "Create repository"

## Step 2: Upload Your Code

### Option A: Using GitHub Desktop (Easiest)
1. Download GitHub Desktop from https://desktop.github.com
2. Clone your new repository
3. Copy all files from `PocketMoneyManager` folder to the cloned folder
4. Commit and push

### Option B: Using Git Commands
```bash
cd "c:\Users\Rasesh Gupta\OneDrive\Desktop\demo of aws\PocketMoneyManager"
git init
git add .
git commit -m "Initial commit - Pocket Money Manager app"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/pocket-money-manager.git
git push -u origin main
```

## Step 3: Get Your APK

1. Go to your GitHub repository
2. Click "Actions" tab
3. Wait for the build to complete (5-10 minutes)
4. Click on the completed build
5. Download "pocket-money-manager-apk" artifact
6. Extract the ZIP file
7. Install `app-debug.apk` on your phone!

## 🎉 That's it!

Every time you push code changes, GitHub will automatically build a new APK for you!

## Troubleshooting

- If build fails, check the Actions logs
- Make sure all files are uploaded correctly
- The first build might take longer as it downloads dependencies

Your APK will be ready in minutes! 📱