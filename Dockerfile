# 使用多架构基础镜像
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]