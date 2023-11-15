function Search() {
    return ( 
        <div className="input-group">
            <div className="form-outline w-100">
                <label className="form-label">Search or type barcode</label>
                <div className="d-flex w-100">
                    <input type="search" className="form-control w-100" />
                    <button type="button" className="btn btn-primary">
                        <i className="fas fa-search"></i>
                    </button>
                </div>
            </div>
        </div>
    );
}

export default Search;