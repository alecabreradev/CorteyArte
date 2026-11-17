import { Routes, Route } from "react-router-dom";
import Layout from "./components/Layout.jsx";
import Home from "./pages/Home.jsx";
import Salons from "./pages/Salons.jsx";
import Booking from "./pages/Booking.jsx";
import Team from "./pages/Team.jsx";
import Admin from "./pages/Admin.jsx";
import NotFound from "./pages/NotFound.jsx";

export default function App() {
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route index element={<Home />} />
        <Route path="peluqueria" element={<Salons />} />
        <Route path="reservas" element={<Booking />} />
        <Route path="equipo" element={<Team />} />
        <Route path="admin" element={<Admin />} />
        <Route path="*" element={<NotFound />} />
      </Route>
    </Routes>
  );
}
