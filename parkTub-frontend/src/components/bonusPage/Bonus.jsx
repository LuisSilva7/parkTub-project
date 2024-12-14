import React, { useState, useEffect } from "react";
import styles from "./bonus.module.css";

const Bonus = () => {
  const [bonus, setBonus] = useState([]);
  const [activeBonus, setActiveBonus] = useState(0);
  const [userPoints, setUserPoints] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchBonus = async () => {
      try {
        const response = await fetch("http://localhost:8222/api/v1/bonus");
        const jsonResponse = await response.json();
        setBonus(jsonResponse.data);
      } catch (error) {
        console.error("Erro:", error);
      } finally {
        setLoading(false);
      }
    };

    const fetchActiveBonus = async () => {
      try {
        const response = await fetch("http://localhost:8222/api/v1/bonus/1");
        const jsonResponse = await response.json();
        setActiveBonus(jsonResponse.data.bonusId);
      } catch (error) {
        console.error("Erro:", error);
      } finally {
        setLoading(false);
      }
    };

    const fetchUserPoints = async () => {
      try {
        const response = await fetch(
          "http://localhost:8222/api/v1/customers/1"
        );
        const jsonResponse = await response.json();
        setUserPoints(jsonResponse.data.points);
      } catch (error) {
        console.error("Erro:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchBonus();
    fetchActiveBonus();
    fetchUserPoints();
  }, []);

  if (loading) {
    return <div className={styles.loading}>Carregando bonificações...</div>;
  }

  if (bonus.length === 0) {
    return (
      <div className={styles.noBonus}>Nenhuma bonificação encontrada.</div>
    );
  }

  const sortedBonus = [...bonus].sort((a, b) => {
    if (a.isActive === b.isActive) return 0;
    return a.isActive ? 1 : -1;
  });

  const handleBonus = async (bonusId, pointsRequired) => {
    try {
      const now = new Date().toISOString();
      const response = await fetch(
        `http://localhost:8222/api/v1/bonus/${bonusId}/customer/1`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (!response.ok) {
        throw new Error("Erro ao ativar o bonus");
      }

      window.location.reload();
    } catch (error) {
      console.error("Erro ao processar o pagamento:", error);
    }
  };

  return (
    <div className={styles.bonusContainer}>
      <div className={styles.headerWrapper}>
        <h2 className={styles.title}>Lista de Bonificações</h2>
        <h5 className={styles.points}>{userPoints} pontos</h5>
      </div>
      <div className={styles.bonusList}>
        {sortedBonus.map((bonus, index) => (
          <div key={index} className={styles.bonusItem}>
            <p>
              <strong className={styles.bigger}>
                Bonificação Nº {index + 1}
              </strong>
            </p>
            <p>
              <strong>Descrição: </strong> {bonus.description}
            </p>
            <p>
              <strong>Desconto: </strong> {bonus.discountPercentage}%
            </p>
            <p>
              <strong>Pontos necessários: </strong> {bonus.pointsRequired}
            </p>

            {bonus.id === activeBonus ? (
              <div>
                <p>
                  <strong>Status: </strong> Ativo
                </p>
              </div>
            ) : (
              <div>
                <p>
                  <strong>Status: </strong> Inativo
                </p>
                <button
                  className={styles.bonusButton}
                  onClick={() => handleBonus(bonus.id, bonus.pointsRequired)}
                >
                  Ativar
                </button>
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default Bonus;
