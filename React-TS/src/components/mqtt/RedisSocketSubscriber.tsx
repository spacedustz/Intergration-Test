import React, { useEffect, useState } from 'react';
import {Client} from '@stomp/stompjs';

interface RedisState {
    messages: string[];
    subscribed: boolean;
    client: Client;
}

const RedisSocketSubscriber: React.FC<RedisState> = () => {
    const [messages, setMessages] = useState<string[]>([]);
    const [subscribed, setSubscribed] = useState(false);
    const [client, setClient] = useState<Client>();

    // Life Cycle Hooks
    useEffect(() => {
        subscribeToRedis();
        return () => {
            unSubscribeFromRedis();
        };
    }, []);

    // 구독 함수
    const subscribeToRedis = () => {
        const client = new Client({
            brokerURL: 'ws://localhost:18080/ws',

            debug: (str: string) => {
                console.log(str);
            },
        });

        client.onConnect = () => {
            console.log('Socket Connected');

            // 1번째 파라미터로 Redis Subscribe명, 2번째는 콜백 함수
            client.subscribe('/topic/message', (frame) => {
                    const newMessage = `Test - Redis: ${frame.body}`;
                    setMessages((prevMessages) => [...prevMessages, newMessage]);
                },
                {
                    id: 'Test-Subscribe',
                });
            setSubscribed(true);
        };

        // 오류 메시지의 세부 정보 출력
        client.onStompError = (frame) => {
            console.error('STOMP error', frame.headers['message']);
            console.log('Error Details:', frame.body);
        };

        setClient(client);
        client.activate();
    };

    // 구독 해제 함수, 버튼을 클릭하면 구독을 해제함
    const unSubscribeFromRedis = () => {
        if (client) {
            client.unsubscribe('Test-Subscribe');
            setClient(null);
            setSubscribed(false);
        }
    };

    return (
        <div>
            <h2>Redis Listener</h2>
            <ul>
                {messages.map((message, index) => (
                    <li key={index}>
                        <p>{message}</p>
                    </li>
                ))}
            </ul>
            {!subscribed ? (
                <button onClick={subscribeToRedis}>Subscribe</button>
            ) : (
                // 구독 중일 때 해지 버튼
                <button onClick={unSubscribeFromRedis}>Unsubscribe</button>
            )}
        </div>
    );
};

export default RedisSocketSubscriber;