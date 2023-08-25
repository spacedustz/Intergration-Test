import React from "react";

const Item: React.FC<{text: string}> = (props) => {

    return <li>{props.text}</li>
}

export default Item;