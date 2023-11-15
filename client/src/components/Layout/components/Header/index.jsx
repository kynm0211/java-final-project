import React, { useState } from 'react';
import '../../../GlobalStyle/index.css';
import ProfileMenu from './ProfileMenu';
function Header({user, layout}) {
	let tempUser = {
		name: '',
		role: '',
		image: '',
	}
	if(user) {
		tempUser = user;
	}
    const [options, setOptions] = useState(false);

    const toggleMenu = () => {
      setOptions(!options);
    };

	return (
		<nav className="navbar navbar-light bg-light nav-custom">

		<div className="custom-nav-side ml-auto" id="navbarNav">
			<ul className="navbar-nav navbar-nav-inline">
			<li className="nav-item active">
				<span className="badge badge-danger">{tempUser.role}</span>
			</li>

			<li className="nav-item" onClick={toggleMenu}>
				{tempUser.name}
				<img className="p-1 mx-2 rounded-circle border border-success" src={tempUser.image} height="40" width="40" alt="User Avatar" />
				{options && <ProfileMenu />}
			</li>
			</ul>
		</div>
		</nav>
	);
}

export default Header;
