import React from "react";
import Header from "../components/header/Header";
import ParkingLots from "../components/homePage/ParkingLots";
import CheckIn from "../components/homePage/CheckIn";
import ParkingLotsMap from "../components/homePage/ParkingLotsMap";
import VehicleMap from "../components/homePage/VehicleMap";
import { useState, useEffect } from "react";

const HomePage = () => {
  const [checkIn, setCheckIn] = useState(false);

  useEffect(() => {
    const fetchCheckIn = async () => {
      const response = await fetch(
        "http://localhost:8222/api/v1/parking-sessions/active-session/customers/1"
      );
      const jsonResponse = await response.json();
      if (jsonResponse.data != null) {
        setCheckIn(true);
      }
    };

    fetchCheckIn();
  }, []);

  return (
    <>
      <Header />

      {checkIn ? (
        <>
          <CheckIn />
          <VehicleMap />
        </>
      ) : (
        <>
          <ParkingLots />
          <ParkingLotsMap />
        </>
      )}
    </>
  );
};

export default HomePage;
