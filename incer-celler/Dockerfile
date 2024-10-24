FROM amazoncorretto:21-alpine
EXPOSE 8080

# JAR 파일 복사
COPY build/libs/incer-celler-0.0.1-SNAPSHOT.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]