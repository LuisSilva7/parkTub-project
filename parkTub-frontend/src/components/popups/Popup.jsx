import React from "react";
import styles from "./popup.module.css";

const Popup = ({ message, color, onClose }) => {
  return (
    <div className={styles.popup} style={{ backgroundColor: color }}>
      <p className={styles.message}>{message}</p>
      <button className={styles.closeButton} onClick={onClose}>
        ×
      </button>
    </div>
  );
};

export default Popup;
