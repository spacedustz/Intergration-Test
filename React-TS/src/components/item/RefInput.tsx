import React, {useRef, useContext} from "react";
import {MainContext} from "../context/MainContext";

const Input: React.FC = () => {
    const context = useContext(MainContext);

    // Input Ref
    const inputRef = useRef<HTMLInputElement>(null);

    // Form 입력 시, Browser Default 방지
    const submitHandler = (event: React.FormEvent) => {
        event.preventDefault();

        const enteredText = inputRef.current?.value;

        // Input 검증
        if (enteredText.trim().length === 0) {
            // Throw an Error
            return;
        }

        context.addItem(enteredText);
    };

    return <form onSubmit={submitHandler}>
        <label htmlFor="text">Text Here</label>
        <input type="text" id="text" ref={inputRef} />
        <button>Add Item</button>
    </form>
}

export default Input;