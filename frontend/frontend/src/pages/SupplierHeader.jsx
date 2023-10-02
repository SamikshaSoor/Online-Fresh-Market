import { Link, useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const SupplierHeader = () => {
  let navigate = useNavigate();

  const user = JSON.parse(sessionStorage.getItem("active-supplier"));
  console.log(user);

  const supplierLogout = () => {
    toast.success("logged out!!!", {
      position: "top-center",
      autoClose: 1000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
    });
    sessionStorage.removeItem("active-supplier");
    navigate("/home");
    window.location.reload(true);
  };

  return (
    <ul class="navbar-nav ms-auto mb-2 mb-lg-0 me-5">

      <li class="nav-item">
        <Link to="/addproduct" class="nav-link active" aria-current="page">
          <b className="text-color">Add Product</b>
        </Link>
      </li>

      <li class="nav-item">
        <Link to="/manageproduct" class="nav-link active" aria-current="page">
          <b className="text-color">manage Product</b>
        </Link>
      </li>

      <li class="nav-item">
        <Link to="#" class="nav-link active" aria-current="page">
          <b className="text-color" style={{ color: 'red' }}>Welcome Supplier - {user.firstName}</b>
        </Link>
      </li>

      <li class="nav-item">
        <Link
          to=""
          class="nav-link active"
          aria-current="page"
          onClick={supplierLogout}
        >
          <b className="text-color">Logout</b>
        </Link>
        <ToastContainer />
      </li>
    </ul>
  );
};

export default SupplierHeader;
