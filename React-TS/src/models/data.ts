class Reactive {
    id: string;
    text: string;

    constructor(text: string) {
        this.id = new Date().toISOString().substring(7);
        this.text = text;
    }
}

export default Reactive;