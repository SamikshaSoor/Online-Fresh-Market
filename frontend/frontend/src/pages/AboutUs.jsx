import React from 'react';
import './AboutUs.css';

function AboutUs() {
  return (
    <div className="about-us text-color ms-5 me-5 mr-5 mt-3">
      <h2>Online Fresh Mart</h2>
      <p>The Online Fresh Mart is an ecommerce website that connects customers with fresh vegetables and fruits directly from the farmers. Our mission is to provide our customers with high-quality produce while supporting locals</p>
      <p>Customers can browse our selection of fresh produce, place an order, and have it delivered right to their doorstep. We also offer a review system, so customers can share their feedback on our products and service.</p>
      <p>Thank you for choosing The Farmer's Mart. We look forward to serving you!</p>
      <div className='members'>
        <ul >
          <li>Premkumar Danav (230340120167)</li>
          <li>Samiksha Soor (230340120182)</li>
          <li>Sukanya Suralkar (230340120205)</li>
          <li>Ganesh Godse (230340120066)</li>
          <li>Pranay Sonsare (230340120160)</li>
        </ul>
      </div>

    </div>
  );
}

export default AboutUs;
