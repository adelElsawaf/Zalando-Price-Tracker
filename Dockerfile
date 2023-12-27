FROM openjdk:21-oracle
ADD out/artifacts/Zalando_Price_Tracker_jar .
EXPOSE 8080
ENTRYPOINT ["java" ,"-jar" , "Zalando-Price-Tracker.jar"]