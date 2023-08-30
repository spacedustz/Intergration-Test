import { useItem } from "../../store/zustand/ItemStateZustand";
import React from "react";

interface TestProps {
    description: string
}

const TestItem: React.FC<TestProps> = ({description}) => {
    const { items, addItem } = useItem;

    const onClickAdd = () => {
        // 기존 아이템 배열 복사
        const copyItems = [...items];

        // 기존 배열에 Item 추가
        copyItems.push({
            id: Math.random().toString(36).substring(2, 11),
            description,
            completed: false
        })

        // 새로운 상태 지정
        addItem(copyItems)
    }

    return (
        <div>
            <button onClick={() => onClickAdd}>Add Item</button>
        </div>
    )
}

export default TestItem;