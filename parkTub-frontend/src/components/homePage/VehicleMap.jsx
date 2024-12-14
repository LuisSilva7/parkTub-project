import React, { useState, useEffect, useRef } from "react";
import styles from "./vehicleMap.module.css";
import logoParking from "../../assets/images/parking.png";

const VehicleMap = () => {
  const [latitude, setLatitude] = useState(null);
  const [longitude, setLongitude] = useState(null);
  const mapRef = useRef(null);

  useEffect(() => {
    const fetchActiveSession = async () => {
      try {
        const response = await fetch(
          "http://localhost:8222/api/v1/parking-sessions/active-session/customers/1"
        );
        const jsonResponse = await response.json();

        setLatitude(jsonResponse.data.latitude);
        setLongitude(jsonResponse.data.longitude);
      } catch (error) {
        console.error("Erro:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchActiveSession();
  }, []);

  useEffect(() => {
    const googleMapsApiKey = import.meta.env.VITE_google_maps_api_key;
    const script = document.createElement("script");
    script.src = `https://maps.googleapis.com/maps/api/js?key=${googleMapsApiKey}`;
    script.async = true;
    script.defer = true;

    script.onload = () => {
      const map = new google.maps.Map(mapRef.current, {
        center: { lat: latitude, lng: longitude },
        zoom: 17,
      });

      new google.maps.Marker({
        position: { lat: latitude, lng: longitude },
        map: map,
        title: "Meu Carro",
        icon: {
          url: "https://cdn-icons-png.flaticon.com/512/3156/3156200.png",
          scaledSize: new google.maps.Size(50, 50),
          anchor: new google.maps.Point(25, 25),
        },
      });
    };

    document.body.appendChild(script);

    return () => {
      document.body.removeChild(script);
    };
  }, [longitude]);

  return (
    <div className={styles.vehicleMapWrapper}>
      <img className={styles.vehicleLogo} src={logoParking} />
      <h2 className={styles.vehicleMapTitle}>Local do Estacionamento</h2>
      <div ref={mapRef} className={styles.mapContainer} />
    </div>
  );
};

export default VehicleMap;
