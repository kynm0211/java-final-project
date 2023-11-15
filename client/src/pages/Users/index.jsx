import UserItem from './user';
import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import LoadingImg from '../../components/Layout/components/LoadingImg';
import DetailModal from './FunctionModal/DetailModal';
import DeleteModal from './FunctionModal/DeleteModal';
import EditModal from './FunctionModal/EditModal';
import Pagination from '../../components/Pagination';

function UserList() {

    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const page = queryParams.get('page');


    const [users, setUsers] = useState(null);
    const [divider, setDivider] = useState(1);
    const [role, setRole] = useState("");
    const [status, setStatus] = useState("");
    const [search, setSearch] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    useEffect(() => {
        fetchUsers();

    }, [role, search, status, location]);

    const fetchUsers = async () => {
        setLoading(false);
        setError(null);
        axios.get('/api/users?page='+page, {
            headers: {
                'Authorization': localStorage.getItem('token')
            }
        })
        .then(response => {
            const res = response.data;
            if (res.code === 0) {
                setUsers(res.data.users);
                setDivider(res.data.divider);
            }else{
                setError(res.message);
            
            }
            setLoading(true);
        })
        .catch(error => {
            setError(error.message)
            setLoading(false);
        });
    }

    const refreshUsers = async () => fetchUsers();
    return ( 
        <div>
            <div className="card rounded">
                <div className="card-header bg-success text-white text-center">
                    <h3>Manage user lists</h3>
                </div>
                <div className="card-body">
                    <div className="row my-3">
                        <div className="col-sm-12 col-md-12 col-lg-7">
                            <div className="form-outline mb-4">
                                <label>Enter the name or email for searching</label>
                                <input onChange={e => setSearch(e.target.value)} type="search" className="form-control" id="datatable-search-input" placeholder="Search"/>
                            </div>
                        </div>
                        {/* Following by user's role */}
                        <div className="col-sm-12 col-md-12 col-lg-2">
                            <div className="form-group">
                                <label>Filter</label>
                                <select
                                    className="form-control"
                                    id="exampleFormControlSelect1"
                                    onChange={e => setRole(e.target.value)}
                                >
                                    <option value="">All users</option>
                                    <option value="Administrator">Administrator</option>
                                    <option value="Sale person">Sale person</option>
                                </select>
                            </div>
                        </div>
                        {/* Following by user's status */}
                        <div className="col-sm-12 col-md-12 col-lg-2">
                            <div className="form-group">
                                <label>Status</label>
                                <select
                                    className="form-control"
                                    id="exampleFormControlSelect1"
                                    onChange={e => setStatus(e.target.value)}
                                >
                                    <option value="">All status</option>
                                    <option value="Active">Active</option>
                                    <option value="InActive">InActive</option>
                                    <option value="Lock">Lock</option>
                                </select>
                            </div>
                        </div>
                        <div className="col-sm-12 col-md-12 col-lg-1">
                            <button onClick={refreshUsers} className="btn btn-sm btn-primary">
                                <i className="fa-solid fa-rotate-right mr-1"></i>
                                Refresh
                            </button>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-12 center-table">
                            <table className="table table-bordered table-hover table-responsive-sm table-responsive-md table-responsive-lg table-striped rounded text-center">
                                <thead className="thead-dark rounded">
                                    <tr>
                                        <th scope="col">Order</th>
                                        <th scope="col">Fullname</th>
                                        <th scope="col">Email</th>
                                        <th scope="col">Role</th>
                                        <th scope="col">Status</th>
                                        <th scope="col">Options</th>

                                    </tr>
                                </thead>
                                <tbody>
                                {loading === false && <tr className='text-center'>
                                    <td colSpan={6}><LoadingImg /></td>
                                </tr>}
                                {loading && users && users
                                    .filter(user => user.role.includes(role) && status == user.status || status == ""
                                        && (user.name.toLowerCase().includes(search.toLowerCase())
                                        || user.email.toLowerCase().includes(search.toLowerCase())))
                                    .map((user, index) => (
                                        <UserItem key={index} index={index + 1} user={user}/>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div className="row">
                        <Pagination root='users' divider={divider}/>
                    </div>
                </div>
                {error && (
                    <div className="card-footer">
                        <div class="alert alert-success">
                            <strong>Error!</strong> {error}
                        </div>
                    </div>
                )}
            </div>
            <DetailModal />
            <EditModal refreshUsers= {refreshUsers}/>
            <DeleteModal refreshUsers= {refreshUsers}/>
        </div>
    );
}

export default UserList;