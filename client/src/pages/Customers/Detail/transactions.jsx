import axios from 'axios';
import { useState, useEffect } from 'react';
import OrderList from '../../../components/OrderList';
function Transactions({customer_id}) {

    const [transactions, setTransactions] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);


    
    const handleFetchTransactions = async () => {
        axios.get('/api/customers/' + customer_id + '/transactions', {
            headers: { 
                'Authorization': localStorage.getItem('token')
            }
        })
            .then(response => {
                const res = response.data;
                if(res.code === 0){
                    const trans = res.data.transactions;
                    setTransactions(trans);
                }else{
                    setError(res.message);
                }
            })
            .catch(error => {
                setError(error.message);
            });
    }

    useEffect(() => {
        handleFetchTransactions();
    }, []);



    return ( 
        <div>
            <OrderList orders={transactions} fetch={handleFetchTransactions}/>
        </div>
    );
}

export default Transactions;