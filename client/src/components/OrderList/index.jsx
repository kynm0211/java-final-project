import OrderItem from "./OrderItem";
import { useState } from 'react';
import LoadingImg from '../LoadingScreen/';
function OrderList({orders, fetch}) {
    const [search, setSearch] = useState("");
    return (
        <div>
            <div className="row my-3">
                <div className="col-sm-12 col-md-12 col-lg-8">
                    <div className="form-outline mb-4">
                        <label>Enter the name or barcode for searching</label>
                        <input onChange={e => setSearch(e.target.value)} type="search" className="form-control" id="datatable-search-input" placeholder="Search"/>
                    </div>
                </div>
                <div className="col-sm-12 col-md-12 col-lg-3">
                    <div className="form-group">
                        <label>Sort by <i className="fa-solid fa-arrow-down-a-z"></i></label>
                        <select
                            className="form-control"
                            id="exampleFormControlSelect1"
                            // onChange={e => setCategory(e.target.value)}
                        >
                            <option value="1">a-z</option>
                            <option value="2">z-a</option>
                            <option value="3">Highest Price</option>
                            <option value="4">Lowest Price</option>
                        </select>
                    </div>
                </div>
                <div className="col-sm-12 col-md-12 col-lg-1">
                    <button className="btn btn-sm btn-primary" onClick={()=> fetch()}>
                        <i class="fa-solid fa-rotate-right mr-1"></i>
                        Refresh
                    </button>
                </div>
            </div>
            <div className="row">
                <div className="col-12 center-table">
                    <table className="table table-responsive-sm table-responsive-md table-striped rounded text-center">
                        <thead className="thead-dark rounded">
                            <tr>
                                <th scope="col">Order</th>
                                <th scope="col">Order Number</th>
                                <th scope="col">Total</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Creation Date</th>
                                <th scope="col">Option</th>

                            </tr>
                        </thead>
                        <tbody>
                        {orders && orders
                            .filter(order => order.order_number && order.order_number.toLowerCase().includes(search.toLowerCase()))
                            .map((order, index) => (
                                <OrderItem key={index} index={index + 1} item={order} />
                        ))}

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}

export default OrderList;