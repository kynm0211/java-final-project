import HeaderPOS from '../components/HeaderPOS';
function POSLayout({children, user}) {
    return (
        <div>
            <HeaderPOS user={user}/>
            <container>
                <div className='content'>
                    {children}
                </div>
            </container>
        </div>
    );
}

export default POSLayout;