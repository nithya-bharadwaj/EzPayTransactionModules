//import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {
  Route,
  createBrowserRouter,
  RouterProvider,
  createRoutesFromElements,
} from "react-router-dom";
import NavBar from "./components/NavBar";
import Filter from './components/Filter';
import Review from './components/Review';

function App() {
  const route = createBrowserRouter(
    createRoutesFromElements(
      <Route>
        <Route path="/nav" element={<NavBar />} />
        <Route path="/transactions" element={<Filter />} />
        <Route path="/transactions/review/:id" element ={<Review />} />

      </Route>
    )
  );
  return (
    <main>
      <RouterProvider router={route}></RouterProvider>
      <a href="https://reactjs.org">learn react</a>
    </main>
  );
}

export default App;
