# University Information System : Specialization Server
## Getting started
### Create docker image
1. ``export JAVA_HOME='C:\Program Files\Java\jdk17.0.5_8'`` 
2. ``mvn clean package``
3. ``docker build -t fenix23707/academic-server .``
4. ``docker run -dp 8080:8080 fenix23707/academic-server`` (optional)

### Push docker image to docker hub
1. ``docker login``
2. ``docker push fenix23707/academic-server``


### Before run make sure:
1. postgres is running
2. credentials for postgres are correct
3. database named **uis** was created

### Run locally: 
1. ``cd academic-server-rest``
2. ``mvn spring-boot:run`` optionally ``-Dspring.profiles.active=dev``

To test that it works, open a browser at http://localhost:8082/swagger-ui/index.html .
