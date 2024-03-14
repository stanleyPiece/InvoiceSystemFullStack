import React from "react";
import {Link} from "react-router-dom";
import { formatDate } from "../utils/formatDate";
import "../styles.css";

import { useSession } from "../contexts/session";


const InvoiceTable = ({label, items, deleteInvoice}) => {
    
    const {session} = useSession();
    const isAdmin = session.data?.isAdmin === true;

    return (
        <div>
            <p>
                {label} {items.length}
            </p>

            <table className="table table-bordered border-success table-striped table-hover">
                <thead className="table-dark"> 
                <tr>
                    <th>#</th>
                    <th>Číslo faktury</th>
                    <th>Dodavatel</th>
                    <th>Odběratel</th>
                    <th>Datum vystavení</th>
                    <th>Datum splatnosti</th>
                    <th>Produkt</th>                    
                    <th>Cena</th>
                    <th>VAT</th>
                    <th>Poznámka</th>
                    <th colSpan={3}>Akce</th>
                </tr>
                </thead>
                <tbody>
                {items.map((item, index) => (
                    <tr key={index + 1}>
                        <td>{index + 1}</td>
                        <td>{item.invoiceNumber}</td>
                        <td>{item.seller.name}</td>
                        <td>{item.buyer.name}</td>
                        <td>{formatDate(item.issued)}</td>
                        <td>{formatDate(item.dueDate)}</td>
                        <td>{item.product}</td>
                        <td>{item.price} Kč</td>
                        <td>{item.vat}%</td>
                        <td>{item.note}</td>
                        <td>
                            <div className="btn-group">
                                <Link
                                    to={"/invoices/show/" + item._id}
                                    className="btn btn-sm btn-info"
                                >
                                    Zobrazit
                                </Link>
                            {isAdmin ? (
                                <Link
                                    to={"/invoices/edit/" + item._id}
                                    className="btn btn-sm btn-warning"
                                >
                                    Upravit
                                </Link>
                            ) : null}
                            {isAdmin ? (    
                                <button
                                    onClick={() => deleteInvoice(item._id)}
                                    className="btn btn-sm btn-danger"
                                >
                                    Odstranit
                                </button>
                            ) : null}    
                            </div>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            {isAdmin ? (
                <Link to={"/invoices/create"} className="btn btn-success">
                    Nová faktura
                </Link>
            ) : null}    
        </div>
    );
};

export default InvoiceTable;