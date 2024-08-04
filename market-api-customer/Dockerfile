FROM lislroow/amazoncorretto:17-alpine-jdk-scouter
WORKDIR /app
COPY target/market-api-customer*.jar market-api-customer.jar
ENTRYPOINT ["/bin/sh", "-c", "id && ls -al && \
  $(nohup java -classpath ./agent.host:./agent.host/scouter.host.jar \
               -Dnet_collector_ip=${SPRING_PROFILE}.scouter-server \
               scouter.boot.Boot ./agent.host/lib \
    > nohup.out & ) && \
  java -javaagent:./agent.java/scouter.agent.jar \
       -Dscouter.config=./agent.java/scouter.conf \
       -Dnet_collector_ip=${SPRING_PROFILE}.scouter-server \
       -Dobj_name=${APP_NAME} \
       -Dspring.profiles.active=${SPRING_PROFILE} \
       -jar ${APP_NAME}.jar"]
