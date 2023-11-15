import axios from "axios";
import { useEffect, useState, useRef, Fragment } from "react";
import ModalDialog from "../../../components/Layout/components/ModalDialog";

function Profile() {
  	const token = window.localStorage.getItem("token");
  	const [isUpdated, setIsUpdated] = useState(false);
  	const [fileImage, setFileImage] = useState(null);
	const [avatarUpdated, setAvatarUpdated] = useState(false);
	const [updateState, setUpdateState] = useState(null);

  	const [rsUser, setRsUser] = useState({});
  	const [user, setUser] = useState({
		username: "",
		name: "",
		role: "",
		email: "",
		status: "",
		image: "",
  	});
  	
	const [isModalOpen, setIsModalOpen] = useState(false);

	const handleModalOpen = (value) => setIsModalOpen(value);


  	// Handle for hover on text of fullname
  	const [isHovered, setIsHovered] = useState(false);
  	const handleMouseEnter = () => setIsHovered(true);
  	const handleMouseLeave = () => setIsHovered(false);

  	// Handle for get fullname value
  	const handleFullnameChange = (event) => {
    const newName = event.target.value;
    setIsUpdated(newName !== user.name);
    setUser((prevState) => ({ ...prevState, name: newName }));
  	};

  	// Select the image for the avatar
  	const fileInputRef = useRef(null);

  	const handleFileChange = () => {
    	fileInputRef.current.click();
  	};

  	const handleFileSelected = (e) => {
		const selectedFile = e.target.files[0];
		if (selectedFile) {
		console.log(selectedFile);
		const imageUrl = URL.createObjectURL(selectedFile);
		setUser((prevState) => ({ ...prevState, image: imageUrl }));
		setIsUpdated(true);
		setFileImage(selectedFile);
		}
  	};
  	const handleUpdateAvatar = async () => {
		const imageData = new FormData();
		imageData.append("file", fileImage);
	
		if (!fileImage) return null;
	
		try {
			const response = await axios.post('/api/users/upload', imageData, {
				headers: { 'Authorization': token },
			});
		
			if (response.status !== 200) {
				throw new Error('Network response was not ok');
			}
	
			return response.data.data;
		} catch (error) {
			console.error(error);
			return null;
		}
  	};
  
  const updateUserProfile = async () => {
	setUpdateState(true);
	if(fileImage){
		const avatarUrl = await handleUpdateAvatar();
		if (avatarUrl === null) {
			console.error('Error updating avatar');
			return;
		}
		setUser((prevState) => ({ ...prevState, image: avatarUrl }));
		setAvatarUpdated(true);
	}else{
		update();
	}
  };
  
  const update = () => {
	axios.put('/api/users/update', user, { headers: { 'Authorization': token } })
		.then((response) => {
			setIsUpdated(false);
			setIsModalOpen(true);
			setUpdateState(false);
		})
		.catch((error) => {
			console.error('Error updating user profile:', error);
		});
  }
	
  // Fetch user for first time
  useEffect(() => {
    axios
      .get("/api/current_user/", { headers: { Authorization: token } })
      .then((response) => {
        const data = response.data;
        setUser({
			username: data.username,
        	name: data.name,
        	role: data.role,
        	email: data.email,
        	status: data.status,
        	image: data.image,
        });
        setRsUser(user);
      })
      .catch((error) => {
        console.log(error);
      });

    if (!isUpdated) {
      setUser(rsUser);
      setFileImage(null);
    }

	if(avatarUpdated) {
		// Thực hiện cập nhật thông tin người dùng
		update();
	}
  }, [avatarUpdated]);



  return (
	<Fragment>
		<div className="card">
			<div className="card-header">Profile Information</div>
			<div className="card-body">
				<div className="mt-2">
				<div className="row p-5 ">
					<div className="col-md-4 d-flex flex-column rounded justify-content-center border border-primary p-5">
					<div className="text-center mb-4 ">
						<img
						src={user.image}
						alt="Avatar"
						className="img-fluid rounded-circle "
						width="150"
						height="150"
						/>
					</div>
					<input
						name="file"
						type="file"
						accept="image/*"
						style={{ display: "none" }}
						ref={fileInputRef}
						onChange={handleFileSelected}
					/>
					<button
						type="button"
						className="btn btn-warning"
						onClick={handleFileChange}
					>
						<i className="fa-regular fa-pen-to-square"></i>
						Update Avatar
					</button>
					</div>
					<div className="col-md-8 px-5">
					<div className="d-flex align-items-center mb-4">
						<input
						value={user.name}
						className="mb-0 p-1 pr-3 input-cus-name"
						onMouseEnter={handleMouseEnter}
						onMouseLeave={handleMouseLeave}
						onInput={handleFullnameChange}
						/>
						{isHovered && (
						<i
							className="mx-2 fa-regular fa-pen-to-square ms-2 fa-2x"
							style={{ color: "green" }}
						></i>
						)}
					</div>
					<h5>
						<span className="p-2 mr-2 badge badge-secondary">Username</span>
						<span className="p-2 badge badge-info">{user.username}</span>
					</h5>
					<h5>
						<span className="p-2 mr-2 badge badge-secondary">Email</span>
						<span className="p-2 badge badge-info">{user.email}</span>
					</h5>
					<h5>
						<span className="p-2 mr-2 badge badge-secondary">Role</span>
						<span className="p-2 badge badge-info">{user.role}</span>
					</h5>
					<h5>
						<span className="p-2 mr-2 badge badge-secondary">Status</span>
						<span className="p-2 badge badge-info">{user.status}</span>
					</h5>
					</div>
				</div>
				</div>
			</div>
			<div className="card-footer">
				<div className="row">
				{isUpdated && (
					<Fragment>
					<div className="col">
						{/* Empty column to push buttons to the right */}
					</div>
					<div className="col-auto">
						<button
							onClick={updateUserProfile}
							type="button"
							className="btn btn-warning mr-2 my-1"
							disabled={updateState}
						>
							{updateState ? <i class="fa-solid fa-spinner mr-1"></i> : <i className="fa-solid fa-check mr-1"></i>}
							<span>Update</span>
						</button>
						<button
							onClick={() => setIsUpdated(false)}
							type="button"
							className="btn btn-primary my-1"
							disabled={updateState}
						>
						<i className="fa-solid fa-ban mr-1"></i>Cancel
						</button>
					</div>
					</Fragment>
				)}
				</div>
			</div>
		</div>
		{/* <!-- The Modal --> */}
		{isModalOpen &&
			<ModalDialog
				onValueChange={handleModalOpen}
				title="Notification"
				button_title="Logout"
				>
					Your account has been updated successfully!<br/>
					<strong>Please logout right now and re-login to the system for updating of newest information!!!</strong>
			</ModalDialog>
		}
	</Fragment>

    
  );
}

export default Profile;
