import React from 'react';
import './FAQ.css';

const FAQ = () => {
  return (
    <div className="faq-container">
      <h2 className="faq-title">Frequently Asked Questions</h2>
      <div className="faq-item">
        <h3>What is your return policy?</h3>
        <p>We offer a 30-day money-back guarantee on all purchases.</p>
      </div>
      <div className="faq-item">
        <h3>Do you ship internationally?</h3>
        <p>Yes, we ship to most countries around the world.</p>
      </div>
      <div className="faq-item">
        <h3>How can I track my order?</h3>
        <p>Once your order has shipped, you will receive a tracking number by email.</p>
      </div>
      <div className="faq-item">
        <h3>What payment methods do you accept?</h3>
        <p>We accept all major credit cards, PayPal, and bank transfers.</p>
      </div>
      <div className="faq-item">
        <h3>Can I cancel or change my order?</h3>
        <p>You can cancel or change your order before it has shipped by contacting customer service.</p>
      </div>
    </div>
  );
};

export default FAQ;
