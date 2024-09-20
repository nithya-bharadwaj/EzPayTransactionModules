import React, { useState, useEffect } from "react";
import AutopayCard from "./AutopayCard";// Import the AutopayCard component

const AutopayManager = () => {
  const baseUrl2="http://localhost:9090/api/scheduled-payments"
  const [autopayList, setAutopayList] = useState([]);

  // Fetch the autopay data from the backend
  useEffect(() => {
    const fetchAutopayList = async () => {
      try {
        const response = await fetch(`${baseUrl2}/ScheduledPaymentsHistory`);
        const data = await response.json();
        setAutopayList(data); // Store the data in state
      } catch (error) {
        console.error("Error fetching autopay data:", error);
      }
    };

    fetchAutopayList();
  }, []);

  // Handler to modify a scheduled payment
  const handleModify = async (updatedPayment) => {
    const { transactionId, endDate, frequency, amount,transactionType } = updatedPayment;
    
    try {
      const response = await fetch(`${baseUrl2}/modify/${transactionId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ ...updatedPayment }),
      });

      if (response.ok) {
        // Update the UI with the modified payment
        const updatedList = autopayList.map(payment =>
          payment.transactionId === transactionId ? { ...payment, endDate, frequency, amount } : payment
        );
        setAutopayList(updatedList);
      }
    } catch (error) {
      console.error("Error modifying payment:", error);
    }
  };

  // Handler to cancel an autopay
  const handleCancel = async (transactionId) => {
    try {
      const response = await fetch(`${baseUrl2}/cancel/${transactionId}`, {
        method: 'DELETE',
      });

      if (response.ok) {
        // Update the UI to reflect the cancelled autopay
        const updatedList = autopayList.map(payment =>
          payment.transactionId === transactionId ? { ...payment, isAutoPayEnabled: false } : payment
        );
        setAutopayList(updatedList);
      }
    } catch (error) {
      console.error("Error cancelling autopay:", error);
    }
  };

  return (
    <div>
      <h2>Scheduled Payments</h2>
      {autopayList.map((autopay) => (
        <AutopayCard
          key={autopay.transactionId}
          autopay={autopay} // autopay prop is the individual autopay object
          onModify={handleModify} // Function to modify autopay
          onCancel={handleCancel} // Function to cancel autopay
        />
      ))}
    </div>
  );
};

export default AutopayManager;
