import React from "react";

const Item: React.FC<{ items: string[] }> = (props) => {
    return (
        <ul>
            {props.items.map((item, index) =>
                <li key={index}>{item}</li>,
            )}
        </ul>
    )
}

export default Item;