/**
 * Author: Nithya Bharadwaj P , Preethi R
 * Date: 20/09/2024
 *
 * Description:
 * This component sets up the main application structure for the EzPay application.
 * It uses React Router for routing, with defined routes for Home, About Us, Contact Us, Transactions and Scheduled Payments
 * The Navbar component is included for navigation across the application.
 */

import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import {
  Route,
  createBrowserRouter,
  RouterProvider,
  createRoutesFromElements,
  Outlet
} from "react-router-dom";

import Filter from './components/Filter';
import Home from './Pages/Home'; 
import AboutUs from './Pages/AboutUs';
import Navbar from './components/Navbar';
import AutopayManager from './components/AutopayManager';

function App() {
  // Create the routes using createBrowserRouter and createRoutesFromElements
  const router = createBrowserRouter(
    createRoutesFromElements(
      <Route path="/" element={<Layout />}>
        <Route index element={<Home />} />
        <Route path="about" element={<AboutUs />} />
        <Route path="transactions" element={<Filter />} />
        <Route path="schedule" element={<AutopayManager/>}/>
      </Route>
    )
  );

  return (
    <RouterProvider router={router} />
  );
}

function Layout() {
  return (
    <div>
      <Navbar /> {/* Use Navbar component for navigation */}
      <Outlet /> {/* Renders the routed page content */}
    </div>
  );
}

export default App;
