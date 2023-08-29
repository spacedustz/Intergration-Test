import React, {useState} from "react";

type FormProps = {
    onSubmit: (form: { name: string; description: string }) => void;
};

function CounterForm({ onSubmit }: FormProps) {
    const [form, setForm] = useState({ name: '', description: '' });

    const { name, description } = form;

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setForm({
            ...form,
            [name]: value
        });
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault(); // 브라우저 기본값 방지
        onSubmit(form);
        setForm({
            name: '',
            description: ''
        });
    };

    return (
        <form onSubmit={handleSubmit}>
            <input name="name" value={name} onChange={onChange} placeholder="이름" />
            <input name="description" value={description} onChange={onChange} placeholder="설명" />
            <button type="submit">등록</button>
        </form>
    );
}

export default CounterForm;