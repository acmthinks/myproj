FROM openjdk

COPY target/myproj-1.0-SNAPSHOT.jar /app.jar

ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
# docker build --tag myproj:1.1 .
# docker run --publish 8001:8001 --detach --name myproj myproj:1.1
