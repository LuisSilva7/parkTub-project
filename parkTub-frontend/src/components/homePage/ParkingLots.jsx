import React, { useState, useEffect } from "react";
import Popup from "../popups/Popup"; // Importa o componente Popup
import styles from "./parkingLots.module.css"; // Importa o CSS

const ParkingLots = () => {
  const [parkingLots, setParkingLots] = useState([]);
  const [licensePlate, setLicensePlate] = useState(""); // Estado para armazenar a matrícula
  const [confirmLicensePlate, setConfirmLicensePlate] = useState(""); // Estado para armazenar a confirmação da matrícula
  const [selectedLot, setSelectedLot] = useState(null); // Estado para armazenar o estacionamento selecionado
  const [popup, setPopup] = useState({
    isVisible: false,
    message: "",
    color: "",
  }); // Estado para o popup

  useEffect(() => {
    const fetchParkingLots = async () => {
      const response = await fetch("http://localhost:8222/api/v1/parking-lots");
      const jsonResponse = await response.json();
      setParkingLots(jsonResponse.data);
    };

    fetchParkingLots();
  }, []);

  const showPopup = (message, color = "#f8d7da") => {
    setPopup({ isVisible: true, message, color });
    setTimeout(
      () => setPopup({ isVisible: false, message: "", color: "" }),
      3000
    ); // Esconde o popup após 3 segundos
  };

  const isValidLicensePlate = (plate) => {
    const licensePlatePattern = /^\d{2}[A-Z]{2}\d{2}$/; // Exemplo: 90KF90
    return licensePlatePattern.test(plate);
  };

  const handleCheckIn = async () => {
    // Verifica se as matrículas são iguais antes de continuar
    if (!licensePlate || !selectedLot) {
      showPopup("Por favor, forneça a matrícula.", "#ff848f");
      return;
    }

    if (licensePlate !== confirmLicensePlate) {
      showPopup(
        "As matrículas não coincidem. Por favor, tente novamente.",
        "#ff848f"
      );
      return;
    }

    if (!isValidLicensePlate(licensePlate)) {
      showPopup("A matrícula deve estar no formato 90KF90.", "#ff848f");
      return;
    }

    try {
      const now = new Date().toISOString();
      const parkingSessionRequest = {
        parkingLotName: selectedLot.name,
        licensePlate: licensePlate,
        latitude: selectedLot.latitude,
        longitude: selectedLot.longitude,
        checkInTime: now,
      };

      const response = await fetch(
        "http://localhost:8222/api/v1/parking-sessions/active-session/customers/1",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(parkingSessionRequest),
        }
      );

      if (!response.ok) {
        throw new Error("Erro ao realizar o Check-In");
      }

      // Limpar estados após o check-in bem sucedido
      setLicensePlate("");
      setConfirmLicensePlate("");
      setSelectedLot(null);
      showPopup("Check-In realizado com sucesso!", "#d4edda");
      window.location.reload();
    } catch (error) {
      console.error("Erro ao realizar o Check-In:", error);
      showPopup("Erro ao realizar o Check-In", "#f8d7da");
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
                    Vagas Disponíveis: {lot.availableSpots}/{lot.totalSpots}
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

      {/* Popup para exibição de mensagens */}
      {popup.isVisible && (
        <Popup
          message={popup.message}
          color={popup.color}
          onClose={() => setPopup({ isVisible: false, message: "", color: "" })}
        />
      )}
    </div>
  );
};

export default ParkingLots;
