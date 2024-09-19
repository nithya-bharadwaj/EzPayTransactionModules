/**
 * Author: Nithya Bharadwaj P
 * Date: 2024/09/09
 * 
 * Main App component that sets up routing for the application.
 * Uses React Router to handle navigation between different components.
 * Imports necessary Bootstrap styles for styling.
 */

import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import {
  Route,
  createBrowserRouter,
  RouterProvider,
  createRoutesFromElements,
} from "react-router-dom";

import Filter from './components/Filter';
import Home from './Home'; // Import Home component
import AboutUs from './AboutUs';
import TransactionHistoryPage from './Pages/TransactionHistoryPage';



/**
 * The main application component that sets up the routes using React Router.
 * Defines routes and the components to render based on the current URL.
 * @returns {JSX.Element} The rendered app component with routing.
 */
function App() {
  // Create the routes using createBrowserRouter and createRoutesFromElements
  const route = createBrowserRouter(
    createRoutesFromElements(
      <Route>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<AboutUs/>} /> Placeholder for About Us
        <Route path="/transactions" element={<Filter />} />
        <Route path="/transactionCopy" element={<TransactionHistoryPage/>} />
      </Route>
    )
  );

  // Return the main JSX structure with the RouterProvider
  return (
    <main>
      <RouterProvider router={route}></RouterProvider>
    </main>
  );
}

export default App;
