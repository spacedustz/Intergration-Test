import {create} from 'zustand';

// Item
interface Item {
    id: string
    description: string
    completed: boolean
}

// ItemList
interface ItemList {
    items: Item[]
    addItem: (description: string) => void
}


export const useItem = create<ItemList>((set) => ({
    items: [],
    addItem: (newItems: Item[]) => set({items: newItems})
}))