# Coding challenge




## Building and running the application

* **Run Postgres DB**: `docker run --name coding_challenge -e POSTGRES_USER=myuser -e POSTGRES_PASSWORD=mypassword -e POSTGRES_DB=challenge_db -p 5432:5432 -d postgres:16` 
* **Run Kafka**: run `docker-compose up -d` in the backend directory
* **Run backend**: `mvn clean install` and `mvn spring-boot:run`
* **Install frontend dependencies**: `npm install`
* **Run frontend**: `npm run dev`


### Testing 
* **Run backend tests**:  `mvn verify`
* **Run cypress tests**: `npx cypress run --headless --component`

### Updating api in forntend
* **Run open api**: `npx @openapitools/openapi-generator-cli generate -i api-docs.json -g typescript-fetch   -o src/types --additional-properties=typescriptThreePlus=true`

### Containerisierung
To run the backend and frontend with docker run the following commands
* `docker-compose up -d`

*  `docker run -d --name coding_challenge  -e POSTGRES_USER=myuser -e POSTGRES_PASSWORD=mypassword   -e POSTGRES_DB=challenge_db -p 5432:5432 postgres:17`

* `docker build -t coding-challenge-breuninger .` in the backend folder

* `docker network create my-network`

* `docker network connect my-network coding_challenge`

* `docker network connect coding_challenge_default coding-challenge-breuninger`


* `docker run -d   --name coding-challenge-breuninger   --network my-network  --network coding_challenge_default -e SPRING_DATASOURCE_URL=jdbc:postgresql://coding_challenge:5432/challenge_db   -e SPRING_DATASOURCE_USERNAME=myuser   -e SPRING_DATASOURCE_PASSWORD=mypassword  -e SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092  -p 8080:8080   coding-challenge-breuninger`

* `docker build -t coding-challenge-breuninger-frontend .`
* `docker run -it -p 5173:5173 coding-challenge-breuninger-frontend`

### Lokales und Cloud-basiertes Deployment

Für das lokale Deployment der Anwendung verfolgen wir die Strategie, alle zentralen Komponenten, wie Datenbank, Kafka, Backend und Frontend, innerhalb von Docker-Containern zu betreiben und diese über Docker Compose zu orchestrieren. Ziel ist es, eine einheitliche und sofort einsatzbereite Entwicklungsumgebung zu schaffen. Durch die Möglichkeit, die gesamte Umgebung mit einem einzigen Befehl zu starten, wird die Effizienz bei der lokalen Entwicklung deutlich gesteigert, Fehlerquellen werden reduziert und die Einarbeitung neuer Entwicklerinnen und Entwickler beschleunigt.

Für das cloudbasierte Deployment setzen wir auf eine Strategie, die Skalierbarkeit, Automatisierung und Sicherheit miteinander vereint. Die Anwendung wird weiterhin in Docker-Containern betrieben, die jedoch durch Kubernetes orchestriert werden. Helm-Charts ermöglichen ein reproduzierbares und versionierbares Deployment, das flexibel an unterschiedliche Umgebungen angepasst werden kann. Ergänzend sorgt die CI/CD-Pipeline dafür, dass vor jedem Deployment automatisiert Tests, Testabdeckung und Sicherheitsprüfungen wie Trivy-Scans ausgeführt werden. So wird sichergestellt, dass nur getestete, stabile und sichere Versionen der Anwendung in der Cloud bereitgestellt werden.