FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN keytool -importcert -alias ROOT -keystore cacerts -file /var/run/secrets/kubernetes.io/serviceaccount/ca.cert
ENTRYPOINT ["java", "-jar", "app.jar"]