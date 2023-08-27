import React from "react";
import Reactive from "../models/data";
import ReactiveFCItem from "./ReactiveFCItem";

const Item: React.FC<{ items: Reactive[]; onRemoveItem: (itemId: string) => void }> = (props) => {
    return (
        <ul>
            {props.items.map((item) =>
                <ReactiveFCItem
                    key={item.id}
                    text={item.text}
                    onRemoveItem={() => props.onRemoveItem(item.id)}
                />
            )}
        </ul>
    )
}

export default Item;