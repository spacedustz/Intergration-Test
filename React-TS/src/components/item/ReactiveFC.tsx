import React, {useContext} from "react";

import ReactiveFCItem from "./ReactiveFCItem";
import {MainContext} from "../context/MainContext";

const Item: React.FC = () => {
    const context = useContext(MainContext)

    return (
        <ul>
            {context.items.map((item) =>
                <ReactiveFCItem
                    key={item.id}
                    text={item.text}
                    onRemoveItem={context.removeItem.bind(null, item.id)}
                />
            )}
        </ul>
    )
}

export default Item;