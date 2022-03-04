FROM openjdk:11
VOLUME /tmp
EXPOSE 9999
ADD "build/libs/elama_quiz-0.0.1-SNAPSHOT.war" quiz.war
ENTRYPOINT ["java", "-jar", "quiz.war"]