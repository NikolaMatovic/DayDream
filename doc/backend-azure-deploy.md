# Backend Deployment to Azure Web App

This project includes a backend-only deployment workflow:

- Workflow file: `.github/workflows/backend-azure-deploy.yml`
- Trigger: push to `main` or `daydream` when files in `_backend/` change
- Deploy target: Azure Web App via Publish Profile (no Azure CLI login required)

## 1. Create GitHub Secret

Add this repository secret:

- `AZURE_WEBAPP_PUBLISH_PROFILE`

Value must be the full XML content from Azure App Service "Publish profile" download.

## 2. Verify workflow values

The workflow currently uses these Azure values:

- `WEBAPP_NAME=MeinAppName-Nikola`
- `APP_PORT=8080`

If needed, change them in `.github/workflows/backend-azure-deploy.yml`.

## 3. Use same port locally and in Azure

Use `8080` for both local run and Azure.

Local Docker run (same as Azure container port):

```bash
cd _backend
docker build -t daydream-backend .
docker run --rm -p 8080:8080 daydream-backend
```

Azure starts the JAR with:

```bash
java -jar /home/site/wwwroot/<jar-file>.jar --server.port=8080
```

## 4. Run deployment

Two options:

- Push a commit that changes `_backend/**`
- Run workflow manually via GitHub Actions (`workflow_dispatch`)

## 5. Result

After success, backend is available at:

- `https://meinewebbapp-nidzo.azurewebsites.net`

Use `https://meinewebbapp-nidzo.azurewebsites.net/v1/users/all` to test the API.

## 6. Important Azure App Service setting

This workflow deploys a Spring Boot JAR. In Azure App Service, your Runtime Stack must be Java (not custom container).