import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";

const MyCart = () => {
  let navigate = useNavigate();
  const user = JSON.parse(sessionStorage.getItem("active-user"));
  const [totatPrice, setTotalPrice] = useState("");
  const [myCartData, setMyCartData] = useState([]);
  useEffect(() => {
    const getMyCart = async () => {
      try {
        const myCart = await retrieveMyCart();
        if (myCart) {
          console.log("cart data is present :)");
          console.log(myCart.totalCartPrice);
          console.log(myCart.cartData);
          setTotalPrice(myCart.totalCartPrice);
          setMyCartData(myCart.cartData);
        }
      } catch (error) {
        console.error("Error fetching cart data:", error);
      }
    };


    getMyCart();
  }, []);



  const retrieveMyCart = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/user/mycart?userId=${user.id}`
      );
      console.log("Cart data retrieved:", response.data);
      return response.data;
    } catch (error) {
      console.error("Error fetching cart data:", error);
    }
  };

  // const clearCart = async () => {
  //   try {
  //     // axios.post(`http://localhost:8080/api/user/order?userId=${user.id}`);
  //     await axios.delete(`http://localhost:8080/api/user/clearcart?userId=${user.id}`);
  //     // Update the cart data after clearing
  //     getMyCart();
  //   } catch (error) {
  //     console.error("Error clearing cart:", error);
  //   }
  // };

  const loadScript = (src) => {
    return new Promise((resolve) => {
      const script = document.createElement("script");
      script.src = src;

      script.onload = () => {
        resolve(true);
      };

      script.onerror = () => {
        resolve(false);
      };

      document.body.appendChild(script);
    });
  };

  const displayRazorPay = async (amount) => {
    try {
      const res = await loadScript("https://checkout.razorpay.com/v1/checkout.js");

      if (!res) {
        alert("Failed to load Razorpay script. Please check your internet connection.");
        return;
      }

      const options = {
        key: "rzp_test_hRHaEIhkpd6odG",
        currency: "INR",
        amount: amount * 100,
        name: "Market",
        description: "Congratulations",
        handler: async function (response) {
          // Payment successful
          // await clearCart();


          fetch("http://localhost:8080/api/user/order?userId=" + user.id, {
            method: "POST",
            headers: {
              Accept: "application/json",
              "Content-Type": "application/json",
            },
          }).then((result) => {
            console.log("result", result);
            result.json().then((res) => {
              console.log(res);


            });
          });

          alert("Payment successful and cart cleared");
          navigate("/");
        },
        prefill: {
          name: "Fresh Market",
        },
      };

      const paymentObject = new window.Razorpay(options);
      paymentObject.open();
    } catch (error) {
      console.error("An error occurred while displaying Razorpay:", error);
      alert("An error occurred while displaying Razorpay. Please try again.");
    }
  };





  const deleteProductFromCart = async (cartId, e) => {
    try {
      const response = await axios.delete(
        `http://localhost:8080/api/user/mycart/remove?cartId=${cartId}`
      );

      console.log(response);
      // Fetch the updated cart data and set the state
      const updatedCart = await retrieveMyCart();
      setMyCartData(updatedCart.cartData);

    } catch (error) {
      console.error(error);
    }
  };





  // const checkout = (e) => {
  //   e.preventDefault();
  //   console.log("CHECKOUT PAGE REQUEST");
  //   navigate("/user/order/payment", { state: { priceToPay: totatPrice } });
  // }

  if (myCartData.length === 0) {
    return (<>
      <h1>cart is empty</h1>
    </>)
  }
  return (
    <div className="mt-3">
      <div
        className="card form-card ms-2 me-2 mb-5 custom-bg border-color"
        style={{
          height: "45rem",
        }}
      >
        <div className="card-header text-center bg-color custom-bg-text">
          <h2>My Cart</h2>
        </div>
        <div
          className="card-body"
          style={{
            overflowY: "auto",
          }}
        >
          <div className="table-responsive">
            <table className="table table-hover custom-bg-text text-center">
              <thead className="bg-color table-bordered border-color">
                <tr>
                  <th scope="col">Product</th>
                  <th scope="col">Name</th>
                  <th scope="col">Description</th>
                  <th scope="col">Quantity</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody className="text-color">
                {myCartData.map((cartData) => {
                  return (
                    <tr>
                      <td>
                        <img
                          src={
                            "http://localhost:8080/api/product/" +
                            cartData.productImage
                          }
                          class="img-fluid"
                          alt="product_pic"
                          style={{
                            maxWidth: "90px",
                          }}
                        />
                      </td>
                      <td>
                        <b>{cartData.productName}</b>
                      </td>
                      <td>
                        <b>{cartData.productDescription}</b>
                      </td>
                      <td>
                        <b>{cartData.quantity}</b>
                      </td>
                      <td>
                        <button
                          className="btn bg-color custom-bg-text btn-sm"
                          onClick={() => deleteProductFromCart(cartData.cartId)}
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </div>
        <div className="card-footer custom-bg">
          <div className="float-right">
            <div
              className="text-color me-2"
              style={{
                textAlign: "right",
              }}
            >
              <h5>Total Price: &#8377; {totatPrice}/-</h5>
            </div>

            <div className="float-end me-2">
              <button
                type="submit"
                className="btn bg-color custom-bg-text mb-3"
                onClick={() => displayRazorPay(totatPrice)}
              >
                Checkout
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyCart;
