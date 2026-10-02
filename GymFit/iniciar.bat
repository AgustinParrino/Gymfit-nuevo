@echo off
cd /d "%~dp0"
if not exist target\gymfit-1.0.0.jar (
  call mvn clean package
  if errorlevel 1 (pause & exit /b 1)
)
java -jar target\gymfit-1.0.0.jar
pause
