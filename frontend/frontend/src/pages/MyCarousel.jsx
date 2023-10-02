import React from "react";
import { Carousel } from 'react-bootstrap';
import carousel2 from "../images/carousel_1.jpg";
import carousel1 from "../images/carousel_2.jpg";

function MyCarousel() {
  return (
    <Carousel interval={3000}>
      <Carousel.Item>
        <img
          className="d-block w-100 mx-auto"
          src={carousel2}
          alt="First slide"
          style={{ objectFit: "cover", height: "60vh" }}
        />
        <Carousel.Caption style={{ background: "rgba(0, 0, 0, 0.5)", padding: "20px", borderRadius: "10px" }}>
          <h3 style={{ color: "white" }}>Online Fresh Mart</h3>
          <p style={{ color: "white" }}>Great deals on greate price.</p>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
        <img
          className="d-block w-100 mx-auto"
          src={carousel1}
          alt="Second slide"
          style={{ objectFit: "cover", height: "60vh" }}
        />

        <Carousel.Caption style={{ background: "rgba(0, 0, 0, 0.5)", padding: "20px", borderRadius: "10px" }}>
          <h3 style={{ color: "white" }}>Online Fresh Mart</h3>
          <p style={{ color: "white" }}>Great deals on greate price.</p>
        </Carousel.Caption>
      </Carousel.Item>
    </Carousel>
  );
}

export default MyCarousel;
