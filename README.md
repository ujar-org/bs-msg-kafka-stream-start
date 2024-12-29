# Kafka Streams starter app

Minimal Spring Boot based sample Kafka Streams app.

### Pre-Requisites to run this example locally

- Setup git command line tool (https://help.github.com/articles/set-up-git)
- Clone source code to the local machine:

```
git clone https://github.com/KnowHowSpringBoot/quickstart-kafka-stream-initialize.git

cd quickstart-kafka-stream-initialize
```

- Install Docker [https://docs.docker.com/get-docker/](https://docs.docker.com/get-docker/)
- Add new version of Docker Compose [https://docs.docker.com/compose/install/](https://docs.docker.com/compose/install/)
- Spin-up single instance of Kafka broker, ZooKeeper by running command:

```
docker compose -f compose.yaml up -d
```

### Running locally

This application is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built
using [Maven](https://spring.io/guides/gs/maven/). You can build a jar files and run it from the command line:

- Create jar packages:

```
./mvnw package
```

- Run **quickstart-kafka-stream-initialize** app:

```
java -jar target/*.jar
```

You might also want to use Maven's `spring-boot:run` goal - applications run in an exploded form, as they do in your IDE:

```
./mvnw spring-boot:run -Dspring-boot.run.profiles=local -P dev
```

## Code conventions

The code follows [Google Code Conventions](https://google.github.io/styleguide/javaguide.html). Code
quality is measured by:

- Sonarqube
- [PMD](https://pmd.github.io/)
- [CheckStyle](https://checkstyle.sourceforge.io/)
- [SpotBugs](https://spotbugs.github.io/)

### Tests

This project has standard JUnit tests. To run them execute this command:

```
mvn test
```

## Versioning

Project uses a three-segment [CalVer](https://calver.org/) scheme, with a short year in the major version slot, short month in the minor version slot, and micro/patch version in the third
and final slot.

```
YY.MM.MICRO
```

1. **YY** - short year - 6, 16, 106
1. **MM** - short month - 1, 2 ... 11, 12
1. **MICRO** - "patch" segment
