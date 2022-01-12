FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 9999
ADD build/libs/elama_quiz-0.0.1-SNAPSHOT-plain.war elama_quiz.war
ENTRYPOINT ["java", "-jar", "elama_quiz.war"]