// import React, { useEffect, useState } from 'react';
// import { Client, StompHeaders } from '@stomp/stompjs';
//
// interface RabbitMqWebSocketHandlerState {
//     messages: string[];
//     subscribed: boolean;
//     client: Client;
// }
//
// const RabbitMqWebSocketHandler: React.FC<RabbitMqWebSocketHandlerState> = () => {
//     const [messages, setMessages] = useState<string[]>([]);
//     const [subscribed, setSubscribed] = useState(false);
//     const [client, setClient] = useState<Client>();
//
//     // Life Cycle Hooks
//     useEffect(() => {
//         subscribeToQueue();
//         return () => {
//             unSubscribeFromQueue();
//         };
//     }, []);
//
//     // 구독 함수
//     const subscribeToQueue = () => {
//         const client = new Client({
//             brokerURL: 'ws://localhost:15674/ws',
//
//             // RabbitMQ 관리 콘솔 인증 정보
//             connectHeaders: {
//                 login: 'guest',
//                 passcode: 'guest',
//             },
//             debug: (str: string) => {
//                 console.log(str);
//             },
//         });
//
//         // Stomp Client Header - AutoConfirm, Message TTL 옵션 추가
//         const connectHeadersWithAutoConfirm: StompHeaders = {
//             ...client.connectHeaders,
//             'x-queue-type': 'quorum',
//             'x-message-ttl': 200000,
//             autoConfirm: true,
//         };
//
//         // Quorum Queue Subscribe
//         client.onConnect = () => {
//             console.log('Socket Connected');
//             // 1번째 파라미터로 Queue 이름, 2번째는 콜백 함수
//             client.subscribe('q.frame', (frame) => {
//                     const newMessage = `Test - Message: ${frame.body}`;
//                     setMessages((prevMessages) => [...prevMessages, newMessage]);
//                 },
//                 {
//                     id: 'Test-Subscribe',
//                     ...connectHeadersWithAutoConfirm,
//                 });
//             setSubscribed(true);
//         };
//
//         // 오류 메시지의 세부 정보 출력
//         client.onStompError = (frame) => {
//             console.error('STOMP error', frame.headers['message']);
//             console.log('Error Details:', frame.body);
//         };
//
//         setClient(client);
//         client.activate();
//     };
//
//     // 구독 해제 함수, 버튼을 클릭하면 구독을 해제함
//     const unSubscribeFromQueue = () => {
//         if (client) {
//             client.unsubscribe('Test-Subscribe');
//             setClient(null);
//             setSubscribed(false);
//         }
//     };
//
//     return (
//         <div>
//             <h2>RabbitMQ Listener</h2>
//             <ul>
//                 {messages.map((message, index) => (
//                     <li key={index}>
//                         <p>{message}</p>
//                     </li>
//                 ))}
//             </ul>
//             {!subscribed ? (
//                 <button onClick={subscribeToQueue}>Subscribe</button>
//             ) : (
//                 // 구독 중일 때 해지 버튼
//                 <button onClick={unSubscribeFromQueue}>Unsubscribe</button>
//             )}
//         </div>
//     );
// };
//
// export default RabbitMqWebSocketHandler;