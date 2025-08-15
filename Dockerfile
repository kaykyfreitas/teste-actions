FROM gradle:8.13.0-jdk21-alpine AS build

WORKDIR /app

COPY build.gradle settings.gradle ./

RUN gradle build -x test -x testIntegration --no-daemon || return 0

COPY . .

RUN gradle build -x test -x testIntegration --no-daemon

FROM eclipse-temurin:21-jre-alpine AS runtime

WORKDIR /app

ARG APPLICATION_PORT

ENV APPLICATION_PORT=${APPLICATION_PORT}

COPY --from=build /app/build/libs/app.jar app.jar

EXPOSE ${APPLICATION_PORT}

ENV JAVA_OPTS="-XX:+UseContainerSupport \
               -XX:MaxRAMPercentage=75.0 \
               -XX:+ExitOnOutOfMemoryError \
               -XX:+UseG1GC \
               -XX:+UseStringDeduplication \
               -XX:+OptimizeStringConcat \
               -XX:TieredStopAtLevel=1 \
               -XX:+TieredCompilation \
               -Xverify:none \
               -XX:+UseCompressedOops \
               -XX:+UseCompressedClassPointers \
               -Dfile.encoding=UTF-8 \
               -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]