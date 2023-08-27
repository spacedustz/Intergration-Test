import axios from 'axios';

/* Import CSV Data from Backend Rest API*/
export const fetchFrame = async (): Promise<any[]> => {
    try {
        const response = await axios.get('http://localhost:8080/csv');
        return response.data;
    } catch (error) {
        console.error('Error Fetching Frame Data: ', error);
        return [];
    }
}