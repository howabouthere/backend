# 요기모여(동네에서 배달을 함께 먹을 수 있는 웹 페이지)

## 기술 스택

### Spring Boot
- 백엔드는 스프링 부트를 사용해서 구현하였다.
- 스프링 부트의 내장되어있는 tomcat을 사용하여 WAS를 기동할 수 있다.
- 
<hr>

### Redis
- 레디스를 사용해서 채팅을 인메모리로 저장하도록 구현하였다.
- RedisDao는 RedisTemplate을 사용하여 구현하였고, ChatRoom이나 ChatRoomInfo는 HashOperations를 사용하였고, UserCount는 ValueOperations을 사용하였다.
- 레디스는 Read/Write를 수행하는 Master와 Read only를 지원하는 Replica로 이뤄져있고, LettuceConnection을 가지고 Connection을 구현할 때, REPLICA_PREFFERED를 사용하여 Read를 할 때는 Replica를 선호하도록 구현하였다.
<hr>

### STOMP
- 채팅을 구현할 때 STOMP를 사용하여 구현하였다.
- STOMP는 웹소켓의 서브 프로토콜로 사용되며, 웹소켓이 Text or Binary로만 정의하는데 반하여
<pre>
COMMAND
header1:value1
header2:value2

Body^@
</pre>
로 이뤄져 있기 때문에 규격을 갖춘 메시지를 보낼 수 있다.
- Redis의 pub/sub 구조를 가지고 메시지를 보내도록 구현했기 때문에, publisher는 모든 subscriber를 알 필요가 없으며, 그저 전달만 하면 된다.
- 

### MySQL
- MySQL을 사용해서 회원 정보 및 게시물을 저장하도록 구현하였다.
<hr>

### MyBatis
- MyBatis를 사용해서 JDBC를 통해 DB에 액세스하는 작업을 캡슐화하였다.

### JWT
- JWT를 사용하여 로그인을 구현하였다.

<code>
{
    "loginSuccess": true,
    "token": {
        "grantType": "Bearer",
        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNyIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2NzUwOTgzMjh9.PA96DnZcVzWX71SVaHMgd8CO2YYckR3fTw_znZI9lKdpd5vRAknnA350Doif1ZsLZ78dZRnDg739FYERT4TJGg",
        "accessTokenExpiresTime": 1675098328418,
        "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NzU2OTk1Mjh9.1gXP0_wEag_brzwqtbeYz3ldTjrqI6zsaizpZcMjfjZRBsldnXIXIMI-xUzDpfO-dxuApOznwvln1oXbukmpOg",
        "key": null
    }
}
</code>

- 토큰은 클라이언트 사이드에 저장되기 때문에 굳이 서버사이드에서 지울 필요는 없다.

***

## 기타 기술

### AOP
Logger를 AOP를 사용하여 로깅하도록 하였다.
<hr>

### TDD
테스트 주도 개발을 적용하였다.

<hr>
