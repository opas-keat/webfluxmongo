#Stage 1
# initialize build and set base image for first stage
FROM maven:3.8.1-adoptopenjdk-11 as stage1
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
# set working directory
WORKDIR /opt/demo
# copy just pom.xml
COPY pom.xml .
# go-offline using the pom.xml
RUN mvn dependency:go-offline
# copy your other files
COPY ./src ./src
# compile the source code and package it in a jar file
RUN mvn clean install -Dmaven.test.skip=true
#Stage 2
# set base image for second stage
# FROM --platform=linux/amd64 eclipse-temurin:11.0.16_8-jre-alpine
# FROM --platform=linux/amd64 adoptopenjdk/openjdk11-openj9:x86_64-alpine-jre-11.0.16_8_openj9-0.33.0
# FROM arm64v8/adoptopenjdk:11.0.10_9-jre-openj9-0.24.0
FROM adoptopenjdk/openjdk11-openj9:x86_64-alpine-jre-11.0.16_8_openj9-0.33.0
# set deployment directory
WORKDIR /opt/demo
# copy over the built artifact from the maven image
COPY --from=stage1 /opt/demo/target/springBootApp.jar /opt/demo/app.jar
# run
CMD java $JAVA_OPTIONS -jar /opt/demo/app.jar
# FROM adoptopenjdk:11.0.11_9-jre-hotspot-focal as build
# WORKDIR /workspace/app
#
# COPY gradle gradle
# COPY build.gradle settings.gradle gradlew ./
# COPY src src
#
# RUN ./gradlew build -x test
# RUN mkdir -p build/libs/dependency && (cd build/libs/dependency; jar -xf ../*.jar)
#
# FROM adoptopenjdk:11.0.11_9-jre-hotspot-focal
# VOLUME /tmp
# ARG DEPENDENCY=/workspace/app/build/libs/dependency
# COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
# COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
# ENTRYPOINT ["java","-cp","app:app/lib/*","com.praxis.dapconnect.DapconnectApplication"]

# # FROM maslick/minimalka:jdk11
# # FROM --platform=linux/amd64 eclipse-temurin:11.0.16_8-jre-alpine
# FROM adoptopenjdk:11.0.11_9-jre-hotspot-focal
# # Refer to Maven build -> finalName
# ARG JAR_FILE=target/springBootApp.jar
# # cd /app
# WORKDIR /app
# # EXPOSE 8080
# # cp target/spring-boot-web.jar /opt/app/app.jar
# COPY ${JAR_FILE} ./app.jar
# # java -jar /opt/app/app.jar
# CMD java $JAVA_OPTIONS -jar app.jar