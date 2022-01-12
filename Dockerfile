FROM bellsoft/liberica-openjdk-alpine
RUN apk update && apk add maven
COPY . /backend
RUN cd /backend && mvn package
RUN apk del maven
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/backend/target/wordQuiz-0.0.1-SNAPSHOT.war"]
EXPOSE 8080