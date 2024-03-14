import React from "react";
import { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { NavDropdown } from 'react-bootstrap';
import "./styles.css";
import {useSession} from "./contexts/session";
import {apiDelete} from "./utils/api";

import {
  BrowserRouter as Router,
  Link,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";

import PersonIndex from "./persons/PersonIndex";
import PersonDetail from "./persons/PersonDetail";
import PersonForm from "./persons/PersonForm";

import InvoiceIndex from "./invoices/InvoiceIndex";
import InvoiceDetail from "./invoices/InvoiceDetail";
import InvoiceForm from "./invoices/InvoiceForm";

import InvoiceStatistics from "./statistics/InvoiceStatistics";
import PersonStatistics from "./statistics/PersonStatistics";

import { LoginPage } from "./login/LoginPage";
import { RegistrationPage } from "./registration/RegistrationPage";

export function App() {

  const [showDropdown, setShowDropdown] = useState(false);
  const {session, setSession} = useSession();

  const handleMouseEnter = () => {
    setShowDropdown(true);
  };

  const handleMouseLeave = () => {
    setShowDropdown(false);
  };

  const handleLogoutClick = () => {
    apiDelete("/api/auth")
        .finally(() => setSession({data: null, status: "unauthorized"}));
}

  return (    
    <Router>
      <div className="container">
        <nav className="navbar navbar-expand-lg navbar-dark bg-success justify-content-between">
          <ul className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link to={"/persons"} className="nav-link">
                Osoby
              </Link>              
            </li>
            <li className="nav-item">
              <Link to={"/invoices"} className="nav-link">
                Faktury
              </Link>              
            </li>
            <li className="nav-item dropdown" onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
              <NavDropdown title="Statistika" id="basic-nav-dropdown" show={showDropdown} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
                <NavDropdown.Item as={Link} to="/persons/statistics">
                  Osoby
                </NavDropdown.Item>
                <NavDropdown.Item as={Link} to="/invoices/statistics">
                  Faktury
                </NavDropdown.Item>
              </NavDropdown>
            </li>
          </ul>
          <ul className="navbar-nav align-items-center gap-2">
            {session.data ? <>
                <li className="nav-item">{session.data.email}</li>
                <li className="nav-item">
                    <button className="btn btn-sm btn-secondary me-2" onClick={handleLogoutClick}>Odhlásit se</button>
                </li>
            </> : session.status === "loading" ?
            <>
                <div className="spinner-border spinner-border-sm" role="status">
                    <span className="visually-hidden">Načítání...</span>
                </div>
            </>
            :<>
                <li className="nav-item">
                    <Link to={"/register"} className="text-dark text-decoration-none">Registrace</Link>
                </li>
                <li className="nav-item">
                    <Link to={"/login"} className="text-dark text-decoration-none me-2">Přihlášení</Link>
                </li>
            </>
        }
        </ul>
        </nav>

        <Routes>
          <Route index element={<Navigate to={"/persons"} />} />
            <Route path="/persons">
              <Route index element={<PersonIndex />} />
              <Route path="show/:id" element={<PersonDetail />} />
              <Route path="create" element={<PersonForm />} />
              <Route path="edit/:id" element={<PersonForm />} />
              <Route path="statistics" element={<PersonStatistics />} />
            </Route>                         
          <Route index element={<Navigate to={"/invoices"} />} />
            <Route path="/invoices">
              <Route index element={<InvoiceIndex />} />
              <Route path="show/:id" element={<InvoiceDetail />} />
              <Route path="create" element={<InvoiceForm />} />
              <Route path="edit/:id" element={<InvoiceForm />} />
              <Route path="statistics" element={<InvoiceStatistics />} />
            </Route>
            <Route path="/register" element={<RegistrationPage/>}/>
            <Route path="/login" element={<LoginPage/>}/>                      
        </Routes>
      </div>
    </Router>       
  );
};

export default App;
