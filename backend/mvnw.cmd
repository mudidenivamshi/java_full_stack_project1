@REM ----------------------------------------------------------------------------
@REM Maven Wrapper startup batch script for Windows
@REM This script downloads Maven automatically if it is not already installed.
@REM Just run: mvnw.cmd compile  OR  mvnw.cmd spring-boot:run
@REM ----------------------------------------------------------------------------
@IF "%__MVNW_ARG0_NAME__%"=="" (SET "MVN_CMD=mvn") ELSE (SET "MVN_CMD=%__MVNW_ARG0_NAME__%")
@SET MAVEN_WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
@SET MAVEN_WRAPPER_PROPERTIES="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties"

@REM Find the project base directory (where pom.xml is)
@SET MAVEN_PROJECTBASEDIR=%~dp0

@REM Download and run Maven via the wrapper
@SET JAVA_HOME_OVERRIDE=

@REM Try to find java in PATH
@WHERE java >nul 2>&1
@IF ERRORLEVEL 1 (
    @echo Error: Java not found. Please install JDK 21 or higher.
    @exit /B 1
)

@REM Use PowerShell to download Maven wrapper jar if missing, then run Maven
@IF NOT EXIST "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar" (
    @echo Downloading Maven Wrapper...
    @powershell -Command "& { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar' -OutFile '%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar' }"
)

@java -jar "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar" %*
