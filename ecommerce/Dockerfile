FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
COPY target/ecommerce-1.0.jar ecommerceService.jar
ENTRYPOINT ["java","-jar","ecommerceService.jar"]