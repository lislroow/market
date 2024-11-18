#### multiple-datasource 설정

- application.properties

```properties
market.mybatis.primary=maria

market.datasource.h2.init=true
market.datasource.h2.hikari.enabled=true
market.datasource.h2.hikari.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
market.datasource.h2.hikari.jdbc-url=jdbc:log4jdbc:h2:mem:~/market-h2;db_close_delay=-1;
market.datasource.h2.hikari.username=sa
market.datasource.h2.hikari.password=
market.datasource.h2.hikari.connection-timeout=250
market.datasource.h2.hikari.maximum-pool-size=3
market.datasource.h2.hikari.max-lifetime=1800000
market.datasource.h2.hikari.pool-name=hikari-h2
market.datasource.h2.hikari.read-only=false

market.datasource.maria.init=false
market.datasource.maria.hikari.enabled=true
market.datasource.maria.hikari.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
market.datasource.maria.hikari.jdbc-url=jdbc:log4jdbc:mariadb://rocky8-market:3306/develop
market.datasource.maria.hikari.username=mkuser
market.datasource.maria.hikari.password=1
market.datasource.maria.hikari.connection-timeout=250
market.datasource.maria.hikari.maximum-pool-size=3
market.datasource.maria.hikari.max-lifetime=1800000
market.datasource.maria.hikari.pool-name=hikari-maria
market.datasource.maria.hikari.read-only=false

market.datasource.oracle.init=false
market.datasource.oracle.hikari.enabled=true
market.datasource.oracle.hikari.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
market.datasource.oracle.hikari.jdbc-url=jdbc:log4jdbc:oracle:thin:@//rocky8-oracle19c:1521/develop
market.datasource.oracle.hikari.username=MKUSER
market.datasource.oracle.hikari.password=1
market.datasource.oracle.hikari.connection-timeout=250
market.datasource.oracle.hikari.maximum-pool-size=3
market.datasource.oracle.hikari.max-lifetime=1800000
market.datasource.oracle.hikari.pool-name=hikari-oracle
market.datasource.oracle.hikari.read-only=false

market.datasource.postgres.init=false
market.datasource.postgres.hikari.enabled=true
market.datasource.postgres.hikari.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
market.datasource.postgres.hikari.jdbc-url=jdbc:log4jdbc:postgresql://rocky8-market:5432/spring
market.datasource.postgres.hikari.username=spring
market.datasource.postgres.hikari.password=1
market.datasource.postgres.hikari.connection-timeout=250
market.datasource.postgres.hikari.maximum-pool-size=3
market.datasource.postgres.hikari.max-lifetime=1800000
market.datasource.postgres.hikari.pool-name=hikari-postgres
market.datasource.postgres.hikari.read-only=false

market.mybatis.h2.enabled=true
market.mybatis.h2.mapper-locations=classpath*:mapper/h2/*.xml
market.mybatis.h2.type-aliases-package=market.**.vo; java.lang
market.mybatis.h2.map-underscore-to-camel-case=true
market.mybatis.h2.jdbc-type-for-null=varchar
market.mybatis.h2.default-statement-timeout=300
market.mybatis.h2.cache-enabled=true

market.mybatis.maria.enabled=true
market.mybatis.maria.mapper-locations=classpath*:mapper/maria/*.xml
market.mybatis.maria.type-aliases-package=market.**.vo; java.lang
market.mybatis.maria.map-underscore-to-camel-case=true
market.mybatis.maria.jdbc-type-for-null=varchar
market.mybatis.maria.default-statement-timeout=300
market.mybatis.maria.cache-enabled=true

market.mybatis.oracle.enabled=true
market.mybatis.oracle.mapper-locations=classpath*:mapper/oracle/*.xml
market.mybatis.oracle.type-aliases-package=market.**.vo; java.lang
market.mybatis.oracle.map-underscore-to-camel-case=true
market.mybatis.oracle.jdbc-type-for-null=varchar
market.mybatis.oracle.default-statement-timeout=300
market.mybatis.oracle.cache-enabled=true

market.mybatis.postgres.enabled=true
market.mybatis.postgres.mapper-locations=classpath*:mapper/postgres/*.xml
market.mybatis.postgres.type-aliases-package=market.**.vo; java.lang
market.mybatis.postgres.map-underscore-to-camel-case=true
market.mybatis.postgres.jdbc-type-for-null=varchar
market.mybatis.postgres.default-statement-timeout=300
market.mybatis.postgres.cache-enabled=true
```

- build.gradle

```gradle
dependencies {
  implementation 'com.h2database:h2'
  implementation 'org.mariadb.jdbc:mariadb-java-client'
  implementation 'com.oracle.database.jdbc:ojdbc8'
  implementation 'org.postgresql:postgresql'
}
```