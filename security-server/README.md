# University Information System : auth Server

## Getting started
### Create docker image
1. ``export JAVA_HOME='C:\Program Files\Java\jdk17.0.5_8'`` 
2. ``mvn clean package``
3. ``docker build fenix23707/security-server .``

### Before run make sure:
1. postgres is running
2. credentials for postgres are correct
3. database named **uis** was created

### Run app: `` ./gradlew clean uis-api::bootRun`` (use `-Dorg.gradle.java.home={java17homePath}` in case java 17 is not default)

To test that it works, open a browser at http://localhost:8080/swagger-ui/index.html .