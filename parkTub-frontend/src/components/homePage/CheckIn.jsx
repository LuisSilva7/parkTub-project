import React, { useState, useEffect } from "react";
import styles from "./checkIn.module.css";

const CheckIn = () => {
  const [activeSession, setActiveSession] = useState(null);
  const [elapsedTime, setElapsedTime] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchActiveSession = async () => {
      try {
        const response = await fetch(
          "http://localhost:8222/api/v1/parking-sessions/active-session/customers/1"
        );
        const jsonResponse = await response.json();

        setActiveSession(jsonResponse.data);
      } catch (error) {
        console.error("Erro:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchActiveSession();
  }, []);

  useEffect(() => {
    if (activeSession) {
      const checkInTime = new Date(activeSession.checkInTime);
      const currentTime = new Date();

      const timeDifference = currentTime - checkInTime;

      const minutesElapsed = Math.floor(timeDifference / 1000 / 60);
      setElapsedTime(minutesElapsed);
    }
  }, [activeSession]);

  if (loading) {
    return <div className={styles.loading}>Carregando...</div>;
  }

  if (!activeSession) {
    return (
      <div className={styles.noSession}>Nenhuma sessão ativa encontrada.</div>
    );
  }

  const handleCheckout = async (sessionId) => {
    try {
      const now = new Date();
      const response = await fetch(
        `http://localhost:8222/api/v1/parking-sessions/inactive-session/customers/1`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ checkOutTime: now }),
        }
      );

      if (!response.ok) {
        throw new Error("Erro ao finalizar sessão");
      }

      setActiveSession(null);
      window.location.reload();
    } catch (error) {
      console.error("Erro ao finalizar sessão:", error);
    }
  };

  return (
    <div className={styles.checkInContainer}>
      <h2 className={styles.title}>Sessão Ativa</h2>
      <div className={styles.info}>
        <p>
          <strong>Matrícula:</strong> {activeSession.licensePlate}
        </p>
        <p>
          <strong>Check-In:</strong>{" "}
          {activeSession.checkInTime.replace("T", " ").slice(0, -4)}
        </p>
        <p>
          <strong>Tempo Decorrido:</strong> {elapsedTime} minutos
        </p>
      </div>
      <button
        className={styles.checkoutButton}
        onClick={() => handleCheckout(activeSession.id)}
      >
        CheckOut
      </button>
    </div>
  );
};

export default CheckIn;
