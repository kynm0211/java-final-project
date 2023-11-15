import ProductItem from "./ProductItem";
import { useEffect, useState } from "react";
import axios from "axios";

function Products({AddToCart}) {
    const [products, setProducts] = useState([]);
    

    useEffect(() => {
        handleFetchProducts();

    }, []);

    const handleFetchProducts = async () => {
        axios.get('/api/products', {
            headers: {
                'Authorization': localStorage.getItem('token')
            }
        })
        .then(response => {
            const res = response.data;
            if (res.code === 0) {
                setProducts(res.data);
            }
        })
        .catch(error => {
            console.log(error);
        });
    }

    
    return (
        <div className="row mt-5">
            <div className="col-md-12">
                <div className="card">
                    <div className="card-body">
                        <div className="row">
                            {products.map((product) => (
                                <ProductItem
                                    key={product._id}
                                    product={product}
                                    AddToCart={AddToCart}
                                />
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Products;
