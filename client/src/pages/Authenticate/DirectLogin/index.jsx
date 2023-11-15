import React, {useEffect, useState} from 'react';
import { useLocation } from 'react-router-dom'; 
import axios from 'axios';
import InvalidToken from '../../../components/InvalidToken';

function DirectLogin() {
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const token = queryParams.get('token');

    const [error, setError] = useState(false);
    const [alert, setAlert] = useState(null);
    const [data, setData] = useState(null);

    useEffect(()=>{
        handleVerifyToken(token);
    }, [error]);

    const handleVerifyToken = async (token) =>{

        
        axios.post('/api/auth/direct-login', {token})
        .then(response => {
            setError(false);
            const data = response.data;
            if(data.code === 12){
                window.location.href = '/renew-password';
            }else if(data.code === 0){
                window.location.href = '/';
            }else{
                setError(true);
                const errorData = data.data;
                setData(errorData.message);
                setAlert(errorData.name);
            }
        })
        .catch(error => {
            setError(true);
            setData(error.data);
            setAlert(error.message);
        });
    

    }
    return ( 
        <div>
            {error && <InvalidToken alert={alert} data={data}/>}
        </div>
    );
}

export default DirectLogin;