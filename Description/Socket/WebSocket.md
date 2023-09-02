## RabbitMQ를 이용한 MQTT 데이터의 실시간 통신

1. 특정 소프트웨어에서 MQTT로 데이터가 계속 나옴
2. Message Broker인 RabbitMQ를 이용해 MQTT 데이터를 Queue에 쌓는다.
3. Queue에 쌓인 데이터를 Pub/Sub 구조로 프론트엔드(MQTT Client)와 실시간 통신을 하고싶음(Web Socket)

<br>

이번엔 도커 컨테이너를 사용 안하고 로컬에 RabbitMQ등을 설치해서 진행합니다.

---
## 1. Erlang OTP 설치

RabbitMQ를 설치하기 전 Erlang을 설치해야 하는데, 설치는 **관리자 권한**으로 설치해야 합니다.

관리자 권한이 아니라면 윈도우 서비스에서 RabbitMQ를 검색할 수 없게 됩니다.

<br>

[Erlang OTP 설치](https://erlang.org/download/otp_versions_tree.html)

설치 페이지로 이동해서 원하는 버전의 **win64** 버튼을 클릭해 다운로드 후 설치합니다.

![img](https://raw.githubusercontent.com/spacedustz/Obsidian-Image-Server/main/img2/erlang.png)

---

## 2. RabbitMQ 설치

Erlang을 **관리자 권한**으로 설치 했으면 이제 RabbitMQ를 설치합니다.

[RabbitMQ 설치](https://www.rabbitmq.com/install-windows.html)

링크로 이동해서 마우스 스크롤을 내리다보면 아래 사진 부분이 나오는데 Download 부분에 있는 다운로드 링크를 클릭해 설치합니다.

![img](https://raw.githubusercontent.com/spacedustz/Obsidian-Image-Server/main/img2/rabbit.png)

<br>

**시스템 환경 변수 설정**

1. 윈도우 검색창에 **시스템 환경**까지만 검색하면 **시스템 환경 변수 편집** 메뉴가 나옵니다.

2. 클릭해서 열어주고 제일 하단의 **환경 변수(N)**를 클릭합니다.

3. 2개의 탭 중 **시스템 변수(S)** 부분에서 스크롤을 내려 **Path**를 찾아서 더블클릭 합니다.

4. **새로 만들기**를 눌러서 RabbitMQ가 설치된 폴더 내부의 bin 폴더를 지정하고 추가해줍니다. (ex: C:\Program Files\RabbitMQ Server\rabbitmq_server-3.12.4\sbin)

<br>

**윈도우 서버 재시작 시RabbitMQ 자동 실행(윈도우 서비스 등록) 설정**

1. 윈도우 CMD를 **관리자 권한**으로 엽니다.
2. 환경변수로 등록한 RabbitMQ의 sbin 폴더로 이동해줍니다.
3. 아래 명령어들을 차례대로 입력합니다.

```shell
rabbitmq-service.bat install
sc config RabbitMQ start=auto
rabbitmq-service.bat start
```

---

## RabbitMQ 서버 실행 & 설정

테스트를 위해 윈도우 Local 환경에서 진행합니다.

<br>

**RabbitMQ 기본 사용 포트**

- eqmd: 4369
- Erlang Distributuin: 25672
- AMQP TLS : 5671, 5672
- Management Plugin : 15672
- MQTT : 1883, 8883
- RabbitMQ Socket Port : 15674

<br>

**로그 파일 위치**

- C:\Users\계정명\AppData|Roaming\RabbitMQ\log

<br>

**RabbitMQ 플러그인 설치**

- 관리자 페이지 : rabbitmq_management
- MQTT : rabbitmq_mqtt

<br>

**RabbitMQ Conf 파일 생성 위치**

- C:\Program Files\RabbitMQ Server\rabbitmq_server-3.12.4\etc\rabbitmq\rabbitmq.conf

<br>

**윈도우 CMD창을 열어 아래 명령어를 입력하여 RabbitMQ를 실행합니다.**

```shell
rabbitmq-server
```

<br>

**RabbitMQ 관리자 페이지를 GUI로 보기 위한 플러그인, MQTT 플러그인 설치**

- rabbitmq_management : 웹 관리 콘솔 플러그인
- rabbitmq_mqtt : MQTT 플러그인
- rabbitmq_web_mqtt : 웹 소켓 연결을 지원하는 MQTT 플러그인
- rabbitmq_web_stmop : 웹 소켓 플러그인

```shell
rabbitmq-plugins enable rabbitmq_management
rabbitmq-plugins enable rabbitmq_mqtt
rabbitmq-plugins enable rabbitmq_web_mqtt
rabbitmq-plugins enable rabbitmq_web_stomp
```

<br>

**RabbitMQ 관리자 페이지, MQTT 포트 떠있는지 확인**

```
netstat -ano | findstr :15672
netstat -ano | findstr :1883
```

<br>

**RabbitMQ 관리자 페이지 접속 (인터넷 주소창에 입력)**

- 첫 로그인 ID : guest
- 비밀번호 : guest

```
http://localhost:15672
```

<br>

**RabbitMQ 웹 관리 콘솔 Exchange, Queue 생성**

**Exchanges 생성**
- Name: Exchange 이름
- Type : 보통 "Topic"을 선택 (MQTT Topic Routing에 가장 적함함)
- Durable & Auto Delete : 일반적으로 체크하지 않음 (메시지 보존 및 삭제 정책에 따라 선택)
- Add Exchange

**Queue** 생성
- Name: Queue 이름
- Durable, Exclusive & Auto Delete : 일반적으로 체크하지 않음 (메시지 보존 및 삭제 정책에 따라 선택)
- Add Queue

**Exchange <-> Queue 바인딩**
- Queue 탭으로 이동 후 만든 큐의 이름 클릭
- 하단의 Bindings 섹션에서 Bind from an Exchange 옵션 선택
- From Exchange 필드에 앞서 만든 Exchange의 이름 입력
- Routing Key 필드에 MQTT Topic Pattern 입력 (ex: test/topic)
- Bind 클릭

<br>

위의 설정을 마무리 하면 RabbitMQ는 설정한 Topic으로 발행된 MQTT 메시지를 수신할 준비가 됩니다.

이렇게 설정한 큐는 React + TypeScript 앱(MQTT Client)이 해당 Topic을 Subscribe하기 시작하면,

그때부터 해당 Topic으로 발행(Publish)되는 모든 MQTT 메시지를 받을 수 있습니다.

<br>

이제 MQTT Producer에서 MQTT를 내보낼때 Topic을 설정하고 내보내면 RabbitMQ의 Exchange를 거쳐, Routing Key에 맞는 Queue에 MQTT 데이터가 쌓입니다.

![img](https://raw.githubusercontent.com/spacedustz/Obsidian-Image-Server/main/img2/rabbitqueue.png)

---

## MQTT Client 설정

MQTT Client는 React + TypeScript 환경에서 진행합니다.
<br>

**RabitMqWebSocketHandler.tsx**

이제 프론트엔드 서버인 React에 MQTT Client 코드를 작성합니다.

웹 소켓을 열고 RabbitMQ의 웹 소켓 플러그인의 포트인 15674,15675 둘중 하나에 `ws://URL/ws`로 연결해줍니다.

나머지 코드는 Exchange와 Queue & Topic에 대한 설정입니다.

Exchange & Queue에 맞는 Routing Key와 Topic을 설정하고 출력하는 컴포넌트를 작성했습니다.

```tsx
import React, { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';

const RabbitMqWebSocketHandler: React.FC = () => {
    const [messages, setMessages] = useState<string[]>([]);
    const stompBrokerUrl = 'ws://localhost:15674/ws';
    const stompTopic = 'TestQueue'; // RabbitMQ의 Queue 이름에 맞게 설정  

    useEffect(() => {
        // STOMP 클라이언트 설정  
        const stompClient = new Client({
            brokerURL: stompBrokerUrl,
            connectHeaders: {
                login: 'guest',
                passcode: 'guest', // RabbitMQ의 인증 정보에 맞게 설정  
            },
            debug: (str: string) => {
                console.log(str);
            },
        });

        stompClient.onConnect = () => {
            console.log('STOMP connected');
            stompClient.subscribe(stompTopic, (frame) => {
                const newMessage = `STOMP - Message: ${frame.body}`;
                setMessages((prevMessages) => [...prevMessages, newMessage]);
            });
        };

        stompClient.onStompError = (frame) => {
            console.error('STOMP error', frame.headers['message']);
        };

        stompClient.activate();

        // 컴포넌트 언마운트 시 클라이언트 연결 해제  
        return () => {
            stompClient.deactivate();
        };
    }, []);

    return (
        <div>
            <h2>RabbitMQ Listener</h2>
            <ul>
                {messages.map((message, index) => (
                    <li key={index}>
                        <p>{message}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default RabbitMqWebSocketHandler;
```

<br>

RabbitMQ의 소켓 포트인 15674 포트를 확인해보면 양방향으로 Established 된것을 확인 할 수 있습니다.

![img](https://raw.githubusercontent.com/spacedustz/Obsidian-Image-Server/main/img2/socket.png)

<br>

프론트엔드 서버의 URL로 들어가보면 웹 소켓을 통해 실시간으로 데이터가 계속 들어옵니다.

![img](https://raw.githubusercontent.com/spacedustz/Obsidian-Image-Server/main/img2/connectsocket.png)

<br>

간단하게 MQTT 데이터를 RabbitMQ를 통해 Queue로 받아서 프론트엔드에서 실시간 통신을 해보았습니다.

다음 글에선 백엔드에서 RabbitMQ의 데이터를 받아 Rest API로 프론트+백엔드 둘다 이용하여 실시간 차트를 만들어보겠습니다.