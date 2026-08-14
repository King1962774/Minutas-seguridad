@echo off
cd /d "%~dp0"
echo Iniciando Minutas Seguridad...
set "MAVEN_CMD=mvn"
where mvn >nul 2>&1
if %errorlevel% neq 0 (
    set "MAVEN_CMD=%USERPROFILE%\AppData\Local\Temp\maven\apache-maven-3.9.6\bin\mvn.cmd"
)
call "%MAVEN_CMD%" javafx:run
pause
