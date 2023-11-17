import Header from '../components/Header';
import Sidebar from '../components/Sidebar';
import { Fragment } from 'react';
function DefaultLayout({children, user}) {
    return (
        <Fragment>
            <Sidebar role={user.role}/>
            <div id="container"  style={{ width: 'calc(100% - 78px)', position: 'relative', left: '78px', display: 'block' }}>
                <Header user={user}/>
                <div className='content'>
                    <div className="card shadow-sm p-4 mb-4 bg-white">
                        <div className="card-body">
                            {children}
                        </div>
                    </div>
                </div>
            </div>
        </Fragment>
    );
}

export default DefaultLayout;