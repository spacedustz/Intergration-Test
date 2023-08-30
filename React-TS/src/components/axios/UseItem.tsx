import React, { useState, useEffect } from 'react';
import axios from 'axios';

interface User {
    id: number;
    username: string;
    name: string;
}

const UseItem: React.FC = () => {

    // 초기 상태 설정
    const [users, setUsers] = useState<User[] | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        const fetchUsers = async (): Promise<void> => {
            try {
                // 요청이 시작할 때 error와 users를 초기화하고, loading 상태를 true로 바꿉니다.
                setError(null);
                setUsers(null);
                setLoading(true);

                // API 요청
                const response = await axios.get<User[]>(
                    'https://jsonplaceholder.typicode.com/users'
                );
                setUsers(response.data); // 데이터는 response.data 안에 들어있습니다.
            } catch (e) {
                setError(e as Error);
            }
            setLoading(false);
        };

        fetchUsers();
    }, []);

    if (loading) return <div>로딩중..</div>;
    if (error) return <div>에러가 발생했습니다</div>;
    if (!users) return null;

    return (
        <ul>
            {users.map((user: User) => (
                <li key={user.id}>
                    {user.username} ({user.name})
                </li>
            ))}
        </ul>
    );
}

export default UseItem;