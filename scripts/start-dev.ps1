# Starts backend and frontend in separate PowerShell windows for local development

$backendPath = "c:\Users\juanc\OneDrive\Desktop\Motor de Reglas de Priorización\backend"
$frontendPath = "c:\Users\juanc\OneDrive\Desktop\Motor de Reglas de Priorización\frontend"

Write-Host "Building backend (skip tests)..." -ForegroundColor Cyan
Push-Location $backendPath
try {
  .\mvnw.cmd -q --no-transfer-progress clean package -DskipTests
} finally {
  Pop-Location
}

Write-Host "Starting backend on :8080 in new window..." -ForegroundColor Cyan
$backendCmd = "cd `"$backendPath`"; java -jar target\motor-priorizacion-0.0.1-SNAPSHOT.jar"
Start-Process -FilePath powershell -ArgumentList "-NoExit","-Command", $backendCmd | Out-Null

Start-Sleep -Seconds 2

Write-Host "Starting frontend dev server on :3000 in new window..." -ForegroundColor Cyan
$frontendCmd = "cd `"$frontendPath`"; $env:Path = 'C:\Program Files\nodejs;' + $env:Path; npm.cmd start"
Start-Process -FilePath powershell -ArgumentList "-NoExit","-Command", $frontendCmd | Out-Null

Write-Host "Done. Backend: http://localhost:8080  |  Frontend: http://localhost:3000" -ForegroundColor Green
