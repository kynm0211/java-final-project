import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import Num2VND from '../../../components/Num2VND';
function Product() {
  const { barcode } = useParams();
  const [product, setProduct] = useState(null);

  useEffect(() => {
    fetchProduct();
  }, []);

  const fetchProduct = async () => {
    axios.get(`/api/product/${barcode}`, {
      headers: {
        'Authorization': localStorage.getItem('token')
      }
    })
      .then(response => {
        const res = response.data;
        if (res.code === 0) {
          setProduct(res.data);
        }
      })
      .catch(error => {
        console.log(error);
      });
  }

  return (

      <div className="card">
        <div className="card-header bg-info text-white text-center">
          <h3>PRODUCT INFORMATION</h3>
        </div>
        {product && (
          <div className="card-body">
            <div className="row mt-4">
              <div className="col-md-3">
                <img src={product.image} alt={product.name} className="img-fluid product-img-preview" />
              </div>
              <div className="col-md-9">
                <h4>{product.name}</h4>
                <div className="form-group">
                  <label>Category:</label>
                  <p>{product.category}</p>
                </div>
                <div className="form-group">
                  <label>Price:</label>
                  <p>{Num2VND(product.retail_price)}</p>
                </div>
                <div className="form-group">
                  <label>Quantity:</label>
                  <p>{product.quantity}</p>
                </div>
                <div className="form-group">
                  <label>Creation Date:</label>
                  <p>{new Date(product.creation_date).toLocaleDateString()}</p>
                </div>
                <div className="form-group">
                  <label>Barcode:</label>
                  <p>{product.barcode}</p>
                </div>
                <div className="form-group">
                  <label>Description:</label>
                  <p>{product.description}</p>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
  );
}

export default Product;
