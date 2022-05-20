import { useState } from "react";
import { StatusEnum } from "../enums/StatusEnum";

export const useTaskCreate = () => {
  const [status, setstatus] = useState(StatusEnum[0]);
  const [description, setdescription] = useState("");
  const [startDate, setstartDate] = useState(new Date());

  const informationSuccessModal = {
    showModal: false,
    title: "",
    message: "",
    type: "",
  };

  const handleDescription = (e) => {
    setdescription(e.target.value);
  };

  const handleStatus = (status) => {
    setstatus(status);
  };

  const handleStartDate = (date) => {
    setstartDate(date);
  };

  return [
    status,
    startDate,
    description,
    informationSuccessModal,
    handleDescription,
    handleStatus,
    handleStartDate,
  ];
};
