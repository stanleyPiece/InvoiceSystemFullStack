import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";

import {apiGet} from "../utils/api";
import { formatDate } from "../utils/formatDate";
import BackButton from "../utils/BackButton";

const InvoiceDetail = () => {
    const navigate = useNavigate();
    const {id} = useParams();
    const [invoice, setInvoice] = useState({});

    useEffect(() => {
        apiGet("/api/invoices/" + id).then((data) => setInvoice(data));
    }, [id]);           

    return (
        <>
            <div>
                <h1>Detail faktury</h1>
                <hr/>                
                <p>
                    <strong>Číslo faktury:</strong>
                    <br/>
                    {invoice.invoiceNumber}
                </p>
                <p>
                    <strong>Dodavatel:</strong>
                    <br/>
                    <a href={`/persons/show/${invoice.seller?._id}`}>{invoice.seller?.name}</a>
                </p>
                <p>
                    <strong>Odběratel:</strong>
                    <br/>
                    <a href={`/persons/show/${invoice.buyer?._id}`}>{invoice.buyer?.name}</a>
                </p>
                <p>
                    <strong>Datum vystavení:</strong>
                    <br/>
                    {formatDate(invoice.issued)}
                </p>
                <p>
                    <strong>Datum splatnosti:</strong>
                    <br/>
                    {formatDate(invoice.dueDate)}
                </p>
                <p>
                    <strong>Produkt:</strong>
                    <br/>
                    {invoice.product}
                </p>
                <p>
                    <strong>Cena:</strong>
                    <br/>
                    {invoice.price} Kč
                </p>
                <p>
                    <strong>VAT:</strong>
                    <br/>
                    {invoice.vat}%
                </p>
                <p>
                    <strong>Poznámka:</strong>
                    <br/>
                    {invoice.note}
                </p>
                
                <BackButton />                                
                
            </div>
        </>
    );
};

export default InvoiceDetail;
