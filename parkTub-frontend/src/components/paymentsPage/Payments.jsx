import React, { useState, useEffect } from "react";
import styles from "./payments.module.css";

const Payments = () => {
  const [payments, setPayments] = useState([]);
  const [discount, setDiscount] = useState(0.0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchPayments = async () => {
      try {
        const response = await fetch(
          "http://localhost:8222/api/v1/payments/customer/1"
        );
        const jsonResponse = await response.json();
        setPayments(jsonResponse.data);
      } catch (error) {
        console.error("Erro:", error);
      } finally {
        setLoading(false);
      }
    };

    const fetchDiscount = async () => {
      try {
        const response = await fetch("http://localhost:8222/api/v1/bonus/1");
        const jsonResponse = await response.json();
        setDiscount(jsonResponse.data.discountPercentage);
      } catch (error) {
        console.error("Erro:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchPayments();
    fetchDiscount();
  }, []);

  if (loading) {
    return <div className={styles.loading}>Carregando pagamentos...</div>;
  }

  if (payments.length === 0) {
    return (
      <div className={styles.noPayments}>Nenhum pagamento encontrado.</div>
    );
  }

  const sortedPayments = [...payments].sort((a, b) => {
    if (a.isPaid === b.isPaid) return 0;
    return a.isPaid ? 1 : -1;
  });

  // Função para lidar com o pagamento
  const handlePayment = async (paymentId) => {
    try {
      const response = await fetch(
        `http://localhost:8222/api/v1/payments/${paymentId}/checkout-session/customer/1`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ discount: discount }),
        }
      );

      if (!response.ok) {
        throw new Error("Erro ao realizar o pagamento");
      }

      //abrir a pagina stripe
      const data = await response.text();
      console.log(data);
      const jsonObject = JSON.parse(data);
      console.log(jsonObject);

      window.open(jsonObject.data);
    } catch (error) {
      console.error("Erro ao processar o pagamento:", error);
    }
  };

  return (
    <div className={styles.paymentsContainer}>
      <h2 className={styles.title}>Lista de Pagamentos</h2>
      <div className={styles.paymentsList}>
        {sortedPayments.map((payment, index) => (
          <div key={index} className={styles.paymentItem}>
            <p>
              <strong className={styles.bigger}>
                Pagamento Nº {index + 1}
              </strong>
            </p>
            <p>
              <strong>Montante: </strong> {payment.amount} €
            </p>
            <p>
              <strong>Local: </strong> {payment.address}
            </p>
            <p>
              <strong>Tempo no local: </strong> {payment.totalTime} minutos
            </p>

            {/* Verificando o status de pagamento */}
            {payment.isPaid ? (
              <div>
                <p>
                  <strong>Status: </strong> Pago
                </p>
                <p>
                  <strong>Data de Pagamento: </strong>
                  {new Date(payment.paymentDate).toLocaleDateString()}
                </p>
              </div>
            ) : (
              <div>
                <p>
                  <strong>Status: </strong> Pendente
                </p>
                <p>
                  <strong>Prazo para pagar: </strong>
                  {payment.paymentTimeFrame}
                </p>
                <p>
                  <strong>Desconto a aplicar: </strong> {discount}%
                </p>
                <button
                  className={styles.payButton}
                  onClick={() => handlePayment(payment.id)}
                >
                  Pagar
                </button>
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default Payments;
