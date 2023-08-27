import React from "react";
import Reactive from "../models/data";
import ReactiveFCItem from "./ReactiveFCItem";

// Version 1
const Item: React.FC<{ items: Reactive[] }> = (props) => {
    return (
        <ul>
            {props.items.map((item) =>
                <ReactiveFCItem key={item.id} text={item.text} />
            )}
        </ul>
    )
}

export default { Item };