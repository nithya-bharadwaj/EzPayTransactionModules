/**
 * Layout component that includes a Navbar and an Outlet for rendering routed components.
 *
 * @component
 * @example
 * return (
 *   <Layout />
 * )
 *
 * @returns {JSX.Element} The rendered Layout component.
 *
 * @author Nithya Bharadwaj P
 */
// Layout.js
import { Outlet } from "react-router-dom";
import Navbar from './Navbar'; // Import your Navbar component

const Layout = () => {
  return (
    <div>
      <Navbar /> {/* This will display the Navbar always */}
      <Outlet />  {/* This is where the routed components will render */}
    </div>
  );
};

export default Layout;
