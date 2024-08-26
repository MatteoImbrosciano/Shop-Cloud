FROM maven:3.8.6

COPY src /home/app/src
COPY pom.xml /home/app

WORKDIR /home/app

RUN mvn clean package
CMD java -jar target/*.jar /home/app/
