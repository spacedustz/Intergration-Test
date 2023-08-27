import React, {useRef} from "react";

const Input = () => {
    // Form 입력 시, Browser Default 방지
    const submitHandler = (event: React.FormEvent) => {
        event.preventDefault();
    };

    // Input Ref
    const inputRef = useRef<HTMLInputElement>(null);
    const enteredText = inputRef.current?.value;

    // Input 검증
    if (enteredText.trim().length === 0) {
        // Throw an Error
        return;
    }

    return <form onSubmit={submitHandler}>
        <label htmlFor="text">Text Here</label>
        <input type="text" id="text" ref={inputRef} />
        <button>Add Item</button>
    </form>
}

export default Input;