import ProductItem from "./ProductItem";
function ProductTab({cart, children}) {
    return ( 
        <div>
            <table className="table table-responsive-sm table-bordered table-striped text-center table-hover">
                <thead>
                    <tr>
                        <th>Order</th>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                    </tr>
                </thead>
                <tbody>
                    {cart.map((product, index) => {
                        return (
                            <ProductItem key={index} index={index + 1} product={product}/>
                        );
                    })}
                </tbody>
            </table>
            {children}
        </div>
    );
}

export default ProductTab;