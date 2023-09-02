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