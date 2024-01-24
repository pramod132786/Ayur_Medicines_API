#FROM openjdk:11

#COPY target/AnarghyaAyurCustomer-0.0.1-SNAPSHOT.jar /usr/app/

#WORKDIR /usr/app

#ENTRYPOINT [ "java","-jar","AnarghyaAyurCustomer-0.0.1-SNAPSHOT.jar" ]

From openjdk:17
add target/SpringBootMedicineServices-0.0.1-SNAPSHOT.jar application.jar 
ENTRYPOINT [ "java","-jar","/application.jar" ]