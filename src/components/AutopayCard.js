import React, { useState } from "react";
import { Card, Button, Badge, Modal, Form } from "react-bootstrap";
import moment from "moment"; // For date handling

const AutopayCard = ({ autopay, onModify, onCancel }) => {
  const {
    transactionId,
    transactionType,
    nextPaymentDate,
    startDate,
    endDate,
    frequency,
    amount,
    isAutoPayEnabled,
    sourceAccount,
    destinationAccount
  } = autopay;

  const currentDate = moment();
  const isExpired = currentDate.isAfter(moment(endDate));
  const isCancelled = !isAutoPayEnabled;

  // Local state for handling modal visibility
  const [showModal, setShowModal] = useState(false);

  // Local state for form inputs
  const [newEndDate, setNewEndDate] = useState(endDate);
  const [newFrequency, setNewFrequency] = useState(frequency);
  const [newAmount, setNewAmount] = useState(amount);

  const handleModify = () => {
    setShowModal(true); // Open the modal
  };

  const handleSaveChanges = () => {
    // Handle saving the modified payment
    const updatedPayment = {
      ...autopay,
      transactionType,
      endDate: newEndDate,
      frequency: newFrequency,
      amount: newAmount,
    };

    console.log("Updated Payment: ", updatedPayment);
    onModify(updatedPayment); // Call the modify function passed in props
    setShowModal(false); // Close the modal
  };

  return (
    <>
      {/* Autopay Catalogue/Card */}
      <Card
        style={{
          backgroundColor: isAutoPayEnabled ? (isExpired ? "#eab676" :"#d4edda"):"#f8f9fa", // Green for active, Grey for inactive
          border: "1px solid #ccc",
          marginBottom: "1rem"
        }}
      >
        <Card.Body>
          <Card.Title>
            Autopay for {destinationAccount}{" "}
            {isExpired && (
              <Badge bg="warning" className="float-end">
                Expired
              </Badge>
            )}
            {isCancelled && (
              <Badge bg="danger" className="float-end">
                Cancelled
              </Badge>
            )}
          </Card.Title>
          <Card.Text>
            <strong>Source Account:</strong> {sourceAccount} <br />
            <strong>Next Payment Date:</strong> {moment(nextPaymentDate).format("DD-MM-YYYY")} <br />
            <strong>Frequency:</strong> {frequency} <br />
            <strong>Amount:</strong> ${amount} <br />
            <strong>Start Date:</strong> {moment(startDate).format("DD-MM-YYYY")} <br />
            <strong>End Date:</strong> {moment(endDate).format("DD-MM-YYYY")}
          </Card.Text>

          <div className="d-flex justify-content-between">
            <Button
              variant="primary"
              onClick={handleModify}
              disabled={isCancelled}
            >
              {isExpired ? "Review" : "Modify"}
            </Button>

            <Button
              variant="danger"
              onClick={() => onCancel(transactionId)}
              disabled={isExpired || isCancelled}
            >
              Cancel
            </Button>
          </div>
        </Card.Body>
      </Card>

      {/* Modify Modal */}
      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Modify Scheduled Payment</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            {/* End Date Input */}
            <Form.Group className="mb-3">
              <Form.Label>End Date</Form.Label>
              <Form.Control
                type="date"
                value={moment(newEndDate).format("YYYY-MM-DD")}
                onChange={(e) => setNewEndDate(e.target.value+"T00:00:00")}
              />
            </Form.Group>

            {/* Frequency Dropdown */}
            <Form.Group className="mb-3">
              <Form.Label>Frequency</Form.Label>
              <Form.Select
                value={newFrequency}
                onChange={(e) => setNewFrequency(e.target.value)}
              >
                <option value="MONTHLY">Monthly</option>
                <option value="WEEKLY">Weekly</option>
                <option value="YEARLY">Yearly</option>
              </Form.Select>
            </Form.Group>

            {/* Amount Input */}
            <Form.Group className="mb-3">
              <Form.Label>Amount</Form.Label>
              <Form.Control
                type="number"
                value={newAmount}
                onChange={(e) => setNewAmount(e.target.value)}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowModal(false)}>
            Close
          </Button>
          <Button variant="primary" onClick={handleSaveChanges}>
            Save Changes
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default AutopayCard;
