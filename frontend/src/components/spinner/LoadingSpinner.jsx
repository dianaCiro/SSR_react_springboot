import React from "react";
import Spinner from "@atlaskit/spinner";
import "./LoadingSpinner.css";

const LoadingSpinner = () => {
  return (
    <div className="spinner">
      <Spinner size="large" />
    </div>
  );
};

export default LoadingSpinner;
