@echo off
setlocal

set JAVAC=C:\Users\admin\.p2\pool\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_21.0.9.v20251105-0741\jre\bin\javac.exe

if not exist "%JAVAC%" (
    echo ERROR: javac not found at %JAVAC%
    exit /b 1
)

if not exist bin mkdir bin

"%JAVAC%" -encoding UTF-8 -d bin src\masterchef\*.java
if %ERRORLEVEL% == 0 (
    echo BUILD SUCCESS
) else (
    echo BUILD FAILED
)
