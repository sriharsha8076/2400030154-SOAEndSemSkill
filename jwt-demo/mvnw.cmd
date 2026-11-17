@REM ────────────────────────────────────────────────────────────────────────────
@REM  mvnw.cmd  — Maven Wrapper for Windows
@REM  Usage: mvnw.cmd spring-boot:run
@REM         mvnw.cmd test
@REM ────────────────────────────────────────────────────────────────────────────
@IF "%__MVNW_ARG0_NAME__%"=="" (SET "MVN_CMD=mvn.cmd") ELSE (SET "MVN_CMD=%__MVNW_ARG0_NAME__%")
@SET MAVEN_PROJECTBASEDIR=%~dp0

@SET MVNW_REPOURL=https://repo.maven.apache.org/maven2
@SET WRAPPER_VERSION=3.2.0
@SET WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar
@SET WRAPPER_PROPERTIES=%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.properties

@IF NOT EXIST "%WRAPPER_JAR%" (
  @ECHO Downloading Maven Wrapper...
  @curl -s -o "%WRAPPER_JAR%" "%MVNW_REPOURL%/org/apache/maven/wrapper/maven-wrapper/%WRAPPER_VERSION%/maven-wrapper-%WRAPPER_VERSION%.jar"
)

@java -jar "%WRAPPER_JAR%" %*

