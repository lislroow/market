#### scouter-agent.conf

- `net_collector_ip` 는 scouter-server 의 IP 를 지정해야 함 (netsh interface portproxy 로 안됨)
- mariadb 는 목록에 없으므로 hook 타입 직접 설정 필요

```properties
net_collector_ip=rocky8-market
net_collector_udp_port=6100
net_collector_tcp_port=6100
hook_jdbc_pstmt_classes=org/mariadb/jdbc/ClientPreparedStatement,org/mariadb/jdbc/ServerPreparedStatement
hook_jdbc_stmt_classes=org/mariadb/jdbc/Statement
hook_jdbc_rs_classes=org/mariadb/jdbc/client/result/CompleteResult,org/mariadb/jdbc/client/result/UpdatableResult
```


#### vmargs 추가

- (TODO) `obj_name` 을 변수 설정 방법 확인: 이클립스, VSCode, ...

```
-javaagent:../scouter/scouter.agent.jar
-Dscouter.config=../scouter/scouter.agent.conf
-Dobj_name=
```
