FROM openjdk:15.0.2-jdk-slim-buster as bulid

WORKDIR application

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN --mount=type=cache,target=/root/.m2 ./mvnw  install -DskipTests

RUN cp /application/target/*.jar organizer-0.0.1-SNAPSHOT.jar
RUN java -Djarmode=layertools -jar organizer-0.0.1-SNAPSHOT.jar extract


RUN /usr/sbin/groupadd lolgroup && adduser  --ingroup lolgroup --disabled-password taric
USER lol

FROM openjdk:15.0.2-jdk-slim-buster
WORKDIR application
COPY --from=bulid application/dependencies/ ./
COPY --from=bulid application/spring-boot-loader/ ./
COPY --from=bulid application/snapshot-dependencies/ ./
COPY --from=bulid application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
