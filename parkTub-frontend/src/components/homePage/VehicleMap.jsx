import React, { useEffect, useRef } from "react";
import styles from "./vehicleMap.module.css";
import logoParking from "../../assets/images/parking.png";

const VehicleMap = () => {
  const mapRef = useRef(null);

  useEffect(() => {
    const googleMapsApiKey = import.meta.env.VITE_google_maps_api_key;
    const script = document.createElement("script");
    script.src = `https://maps.googleapis.com/maps/api/js?key=${googleMapsApiKey}`;
    script.async = true;
    script.defer = true;

    script.onload = () => {
      const map = new google.maps.Map(mapRef.current, {
        center: { lat: 41.54903627013124, lng: -8.421670659730836 },
        zoom: 17,
      });

      new google.maps.Marker({
        position: { lat: 41.54903627013124, lng: -8.421670659730836 },
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
  }, []);

  return (
    <div className={styles.vehicleMapWrapper}>
      <img className={styles.vehicleLogo} src={logoParking} />
      <h2 className={styles.vehicleMapTitle}>Local do Estacionamento</h2>
      <div ref={mapRef} className={styles.mapContainer} />
    </div>
  );
};

export default VehicleMap;
