import React from "react";
import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
  RouterProvider,
} from "react-router-dom";
import HomePage from "./pages/HomePage";
import PaymentsPage from "./pages/PaymentsPage";
import BonusPage from "./pages/BonusPage";
import NotFoundPage from "./pages/NotFoundPage";

const App = () => {
  const router = createBrowserRouter(
    createRoutesFromElements(
      <>
        <Route path="/" element={<HomePage />} />
        <Route path="/payments" element={<PaymentsPage />} />
        <Route path="/bonus" element={<BonusPage />} />
        <Route path="*" element={<NotFoundPage />} />
      </>
    )
  );

  return <RouterProvider router={router} />;
};

export default App;
