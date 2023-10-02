import React from 'react';
import './ContactUs.css';
import logo from "../images/e_logo.jpg";

function ContactUs() {
  return (
    <section className="contact-us">
      <div className="contact-info">
        <h2>Contact Us</h2>
        <p>Have any questions or comments? We'd love to hear from you!</p>
        <p><b>Phone: </b><a href="tel:+919044127435">+919044127435</a></p>
        <p><b>Email: </b><a href="mailto:martfarmers@gmail.com">freshmart@gmail.com</a></p>
        <p><b>Address: </b> CDAC-ACTS, Panchwai Pashan,Pune  Maharashtra 410018</p>
      </div>
      <div className="contact-image">
        <img src={logo} alt="Contact Us" />
      </div>
    </section>
  );
}

export default ContactUs;
