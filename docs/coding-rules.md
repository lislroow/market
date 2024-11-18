### 1) url customer


#### domain (도메인) 분리 단위 

- customer 도메인의 어플리케이션은 market-api-customer 으로 명명할 수 있습니다.
- 어플리케이션의 uri 는 클라이언트 별로 uri 를 다음 규칙을 적용합니다. 
    ```
    /api/customer/v1/{task}/{entity}
    /partner/customer/v1/{task}/{entity}
    /openapi/customer/v1/{task}/{entity}
    /internal/customer/v1/{task}/{entity}
    ```
- 클라이언트 별로 인증 처리를 거친 후 token 을 발급 받습니다.
- token 에 포함되어있는 scope 으로 api 접근에 대한 인가 처리를 합니다.
- 인가 처리를 위해 spring-security 

market-partner-customer
market-openapi-customer
market-openapi-customer



#### dto 클래스를 라이브러리로 관리할 경우

1) 장점

```
- 
```

2) 단점

```
- 
```


