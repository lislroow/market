FROM lislroow/amazoncorretto:17-alpine-jdk-scouter
WORKDIR /app
COPY target/market-com-gateway*.jar market-com-gateway.jar
ENTRYPOINT ["/bin/sh", "-c", "id && ls -al && \
  $(nohup java -classpath ./agent.host:./agent.host/scouter.host.jar \
               -Dnet_collector_ip=${SPRING_PROFILE}.scouter-server \
               scouter.boot.Boot ./agent.host/lib \
    > nohup.out & ) && \
  java -Dspring.profiles.active=${SPRING_PROFILE} \
       -jar ${APP_NAME}.jar"]
