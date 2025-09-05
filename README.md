# Coding challenge


## Building and running the application

* **Run Postgres DB**: `docker run --name coding_challenge -e POSTGRES_USER=myuser -e POSTGRES_PASSWORD=mypassword -e POSTGRES_DB=challenge_db -p 5432:5432 -d postgres:17` 
* **Run Kafka**: run `docker-compose up -d` in the backend directory
* **Run backend**: `mvn clean install` and `mvn spring-boot:run` in the backend directory
* **Install frontend dependencies**: `npm install` in the frontend directory
* **Run frontend**: `npm run dev` in the frontend directory


### Testing 
* **Run backend tests**:  `mvn verify` in the backend directory
* **Run cypress tests**: `npx cypress run --headless --component` in the frontend directory

### Updating api in forntend
* **Run open api**: `npx @openapitools/openapi-generator-cli generate -i api-docs.json -g typescript-fetch   -o src/types --additional-properties=typescriptThreePlus=true` in the frontend directory

### Containerisierung
To run the backend and frontend with docker run the following commands
* `docker-compose up -d`

*  `docker run -d --name coding_challenge  -e POSTGRES_USER=myuser -e POSTGRES_PASSWORD=mypassword   -e POSTGRES_DB=challenge_db -p 5432:5432 postgres:17`

* `docker build -t coding-challenge-breuninger .` in the backend directory

* `docker network create my-network`

* `docker network connect my-network coding_challenge`

* `docker network connect coding_challenge_default coding-challenge-breuninger`


* `docker run -d   --name coding-challenge-breuninger   --network my-network  --network coding_challenge_default -e SPRING_DATASOURCE_URL=jdbc:postgresql://coding_challenge:5432/challenge_db   -e SPRING_DATASOURCE_USERNAME=myuser   -e SPRING_DATASOURCE_PASSWORD=mypassword  -e SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092  -p 8080:8080   coding-challenge-breuninger`

* `docker build -t coding-challenge-breuninger-frontend .` in the frontend directory
* `docker run -it -p 5173:5173 coding-challenge-breuninger-frontend`

## Lokales und Cloud-basiertes Deployment

Für das lokale Deployment der Anwendung verfolgen wir eine Strategie, die auf Docker Compose basiert. Alle zentralen Bestandteile, wie Datenbank, Kafka, Backend und Frontend, werden in separaten Containern gebündelt und gemeinsam orchestriert. Dadurch lässt sich die komplette Umgebung mit nur einem einzigen Befehl starten. Diese Vorgehensweise ermöglicht es, schnell und konsistent auf eine voll funktionsfähige Umgebung zuzugreifen, ohne die einzelnen Komponenten manuell konfigurieren oder betreiben zu müssen.

Im cloudbasierten Deployment liegt der Fokus auf Skalierbarkeit, Automatisierung und Sicherheit. Die Anwendung wird weiterhin in Docker-Containern betrieben, die über Kubernetes orchestriert werden. Mithilfe von Terraform wird die benötigte Cloud-Infrastruktur, wie Cluster, Netzwerke oder Datenbanken, reproduzierbar und versionierbar bereitgestellt. Anschließend übernehmen Helm-Charts die Installation und Konfiguration der Anwendungen innerhalb des Kubernetes-Clusters. Eingebettet ist dieser Prozess in eine CI/CD-Pipeline, die vor jedem Deployment automatisierte Tests, Testabdeckungsanalysen und Codequalitätsmetriken wie SonarQube sowie Sicherheitsprüfungen, etwa mit Trivy-Scans, ausführt. Dadurch wird sichergestellt, dass ausschließlich geprüfte, stabile und sichere Versionen der Anwendung in der Cloud verfügbar gemacht werden.

(Dieser Text wurde mit Hilfe von ChatGPT auf Basis eigenständig erstellter Stichpunkte verfasst. Bei der Entwicklung habe ich Copilot verwendet. Hierbei nutzte ich vor allem die Autovervollständigung.)