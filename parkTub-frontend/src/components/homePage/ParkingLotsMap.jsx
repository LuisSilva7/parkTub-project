import React, { useEffect, useRef } from "react";
import styles from "./parkingLotsMap.module.css";
import logoParking from "../../assets/images/parking.png";
import image1 from "../../assets/images/parque_museu.png";
import image2 from "../../assets/images/parque_radical.png";
import image3 from "../../assets/images/colinatrum.png";

const ParkingLotsMap = () => {
  const mapRef = useRef(null);

  useEffect(() => {
    const googleMapsApiKey = import.meta.env.VITE_google_maps_api_key;
    const script = document.createElement("script");
    script.src = `https://maps.googleapis.com/maps/api/js?key=${googleMapsApiKey}`;
    script.async = true;
    script.defer = true;

    script.onload = () => {
      const map = new google.maps.Map(mapRef.current, {
        center: { lat: 41.545709, lng: -8.428166 },
        zoom: 17,
      });

      // Dados dos locais
      const locations = [
        {
          position: { lat: 41.545453, lng: -8.428053 },
          title: "Bombeiros Voluntários - Museu",
          image: image1,
        },
        {
          position: { lat: 41.54534, lng: -8.428454 },
          title: "Bombeiros Voluntários - Parque Radical",
          image: image2,
        },
        {
          position: { lat: 41.546371, lng: -8.427934 },
          title: "Bombeiros Voluntários - Colinatrum",
          image: image3,
        },
      ];

      // Adiciona marcadores e InfoWindows
      locations.forEach((location) => {
        const marker = new google.maps.Marker({
          position: location.position,
          map: map,
          title: location.title,
          icon: {
            url: "https://www.eub.pt/templates/minhafreguesia/frontend/img/eub/mappark1.svg",
            scaledSize: new google.maps.Size(35, 35),
            anchor: new google.maps.Point(25, 25),
          },
        });

        const infoWindow = new google.maps.InfoWindow({
          content: `
            <div style="text-align: center;">
              <h3>${location.title}</h3>
              <img src="${location.image}" alt="${location.title}" style="width: 300px; height: 100px; border-radius: 8px; margin-top: 10px;" />
            </div>
          `,
        });

        // Exibe a InfoWindow ao clicar no marcador
        marker.addListener("click", () => {
          infoWindow.open(map, marker);
        });
      });
    };

    document.body.appendChild(script);

    return () => {
      document.body.removeChild(script);
    };
  }, []);

  return (
    <div className={styles.vehicleMapWrapper}>
      <img
        className={styles.vehicleLogo}
        src={logoParking}
        alt="Logo Parking"
      />
      <h2 className={styles.vehicleMapTitle}>Locais de Estacionamento</h2>
      <div ref={mapRef} className={styles.mapContainer} />
    </div>
  );
};

export default ParkingLotsMap;
