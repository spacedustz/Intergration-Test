import React, { useRef } from "react";
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

// Version2
const Item2 = () => {
    // Browser Default 방지
    const submitHandler = (event: React.FormEvent) => {
        event.preventDefault();
    };

    const inputRef = useRef<>();

    return <form onSubmit={submitHandler}>
        <label htmlFor="text">Text Here</label>
        <input type="text" id="text" ref={inputRef} />
        <button>Add Item</button>
    </form>
}

export default { Item, Item2 };