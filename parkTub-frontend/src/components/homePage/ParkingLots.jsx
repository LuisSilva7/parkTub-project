import React, { useState, useEffect } from "react";
import styles from "./parkingLots.module.css"; // Importa o CSS

const ParkingLots = () => {
  const [parkingLots, setParkingLots] = useState([]);
  const [licensePlate, setLicensePlate] = useState(""); // Estado para armazenar a matrícula
  const [confirmLicensePlate, setConfirmLicensePlate] = useState(""); // Estado para armazenar a confirmação da matrícula
  const [selectedLot, setSelectedLot] = useState(null); // Estado para armazenar o estacionamento selecionado

  useEffect(() => {
    const fetchParkingLots = async () => {
      const response = await fetch("http://localhost:8080/parking");
      const jsonResponse = await response.json();
      setParkingLots(jsonResponse.data);
    };

    fetchParkingLots();
  }, []);

  const handleCheckIn = async () => {
    // Verifica se as matrículas são iguais antes de continuar
    if (!licensePlate || !selectedLot) {
      alert("Por favor, forneça a matrícula e escolha um estacionamento.");
      return;
    }

    if (licensePlate !== confirmLicensePlate) {
      alert("As matrículas não coincidem. Por favor, tente novamente.");
      return;
    }

    try {
      const now = new Date().toISOString();
      const parkingSessionRequest = {
        name: selectedLot.name,
        licensePlate: licensePlate,
        latitude: -82.55012,
        longitude: -19.632308,
        checkInTime: now,
      };

      const response = await fetch("http://localhost:8080/parking", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(parkingSessionRequest),
      });

      if (!response.ok) {
        throw new Error("Erro ao realizar o Check-In");
      }

      // Limpar estados após o check-in bem sucedido
      setLicensePlate("");
      setConfirmLicensePlate("");
      setSelectedLot(null);
      alert("Check-In realizado com sucesso!");
      window.location.reload();
    } catch (error) {
      console.error("Erro ao realizar o Check-In:", error);
      alert("Erro ao realizar o Check-In");
    }
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.parkingLotsContainer}>
        <h2>Estacionamentos Disponíveis</h2>
        <div className={styles.parkingLots}>
          {parkingLots.length > 0 ? (
            parkingLots.map((lot, index) => (
              <div key={index} className={styles.parkingLot}>
                <div className={styles.parkingLotLeft}>
                  <h3 className={styles.parkingLotName}>{lot.name}</h3>
                  <p className={styles.availableSpots}>
                    Vagas Disponíveis: {lot.availableSpots}
                  </p>
                </div>
                <div className={styles.parkingLotRight}>
                  {/* Botão "Check-In" */}
                  <button
                    className={styles.checkInButton}
                    onClick={() => setSelectedLot(lot)}
                  >
                    Check-In
                  </button>
                </div>
              </div>
            ))
          ) : (
            <p className={styles.loadData}>Carregando estacionamentos...</p>
          )}
        </div>

        {/* Se um estacionamento foi selecionado, exibe os campos para a matrícula */}
        {selectedLot && (
          <div className={styles.checkInForm}>
            <h2 className={styles.lotName}>{selectedLot.name}</h2>
            <input
              type="text"
              value={licensePlate}
              onChange={(e) => setLicensePlate(e.target.value)}
              placeholder="Digite a matrícula"
              className={styles.licensePlateInput}
            />
            <input
              type="text"
              value={confirmLicensePlate}
              onChange={(e) => setConfirmLicensePlate(e.target.value)}
              placeholder="Confirme a matrícula"
              className={styles.licensePlateInput}
            />
            <button
              className={styles.confirmCheckInButton}
              onClick={handleCheckIn}
            >
              Confirmar Check-In
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default ParkingLots;
