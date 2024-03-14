import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";

import {apiGet} from "../utils/api";
import Country from "./Country";
import BackButton from "../utils/BackButton";

const PersonDetail = () => {
    const {id} = useParams();    
    const [person, setPerson] = useState({});
    const [invoicesBuyer, setInvoicesBuyer] = useState([]);
    const [invoicesSeller, setInvoicesSeller] = useState([]);

    useEffect(() => {        
        apiGet("/api/persons/" + id).then((data) => {
            setPerson(data);
            if (data.identificationNumber) {
                apiGet("/api/identification/" + data.identificationNumber + "/sales/")
                      .then((invoices) => setInvoicesBuyer(invoices));
                apiGet("/api/identification/" + data.identificationNumber + "/purchases/")
                      .then((invoices) => setInvoicesSeller(invoices));  
        }
    });
    }, [id]);
    const country = Country.CZECHIA === person.country ? "Česká republika" : "Slovensko";
    
    return (
        <>  <div className="container">
                <div className="row">
                    <div className="col-6">
                        <h1>Detail osoby</h1>
                        <hr/>
                        <h3>{person.name} ({person.identificationNumber})</h3>
                        <p>
                            <strong>DIČ:</strong>
                            <br/>
                            {person.taxNumber}
                        </p>
                        <p>
                            <strong>Bankovní účet:</strong>
                            <br/>
                            {person.accountNumber}/{person.bankCode} ({person.iban})
                        </p>
                        <p>
                            <strong>Tel.:</strong>
                            <br/>
                            {person.telephone}
                        </p>
                        <p>
                            <strong>E-mail:</strong>
                            <br/>
                            {person.mail}
                        </p>
                        <p>
                            <strong>Sídlo:</strong>
                            <br/>
                            {person.street}
                            <br/>
                            {person.city} – {person.zip}
                            <br/> 
                            {country}
                        </p>
                        <p>
                            <strong>Poznámka:</strong>
                            <br/>
                            {person.note}
                        </p>

                        <BackButton />    
                    </div>            
                    <div className="col-6">
                        <h2>Vystavené faktury</h2>
                        {invoicesBuyer.length === 0 ? (<p>Žádné vystavené faktury.</p>)
                        : (<table className="table table-bordered">                
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Číslo faktury</th>                                    
                                </tr>
                            </thead>

                            <tbody>
                            {invoicesBuyer.map((invoice, index) => (
                                <tr key={index}>
                                    <td>{index + 1}</td>
                                    <td>                                    
                                        <a href={`/invoices/show/${invoice._id}`}>{invoice.invoiceNumber}</a>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                        )}
                    </div>                                        
                </div>
                <div className="row">
                    <div className="col-6 offset-6">
                            <h2>Přijaté faktury</h2>
                        {invoicesSeller.length === 0 ? (<p>Žádné přijaté faktury.</p>) 
                        : (<table className="table table-bordered">                
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Číslo faktury</th>                                    
                                    </tr>
                                </thead>

                                <tbody>
                                    {invoicesSeller.map((invoice, index) => (
                                        <tr key={index}>
                                            <td>{index + 1}</td>
                                            <td>                                    
                                                <a href={`/invoices/show/${invoice._id}`}>{invoice.invoiceNumber}</a>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                           </table> 
                           )}
                    </div>              
                </div>
            </div>            
        </>
    );
};

export default PersonDetail;
