import React, { useEffect, useState } from "react";
import {apiDelete, apiGet} from "../utils/api";

import InvoiceTable from "./InvoiceTable";
import InvoiceFilter from "./InvoiceFilter";

const InvoiceIndex = () => {
    const [nonHiddenPersonsList, setNonHiddenPersonsList] = useState([]);    
    const [invoicesState, setInvoices] = useState([]);    
    const [filterState, setFilter] = useState({
        buyerID: undefined,
        sellerID: undefined,
        product: "",
        minPrice: undefined,
        maxPrice: undefined,
        limit: undefined,
    })

    useEffect(() => {
        apiGet("/api/invoices").then((data) => setInvoices(data));
        apiGet("/api/persons", {includeHidden: false}).then((data) => setNonHiddenPersonsList(data));        
    }, []);

    const deleteInvoice = async (id) => {
        try {
            await apiDelete("/api/invoices/" + id);
        } catch (error) {
            console.log(error.message);
            alert(error.message)
        }
        setInvoices(invoicesState.filter((item) => item._id !== id));
    };
    
    const handleChange = (e) => {
        // pokud vybereme prázdnou hodnotu (máme definováno jako true/false/'' v komponentách), nastavíme na undefined
        if (e.target.value === "false" || 
            e.target.value === "true" ||
            e.target.value === '') {
            setFilter(prevState => {
                return {...prevState, [e.target.name]: undefined}
            });
        } else {
            setFilter(prevState => {
                return { ...prevState, [e.target.name]: e.target.value}
            });
        }
    };    
    
    const handleReset = () => {
        setFilter({
            buyerID: undefined,
            sellerID: undefined,
            product: "",
            minPrice: undefined,
            maxPrice: undefined,
            limit: undefined,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const params = filterState;
    
        const data = await apiGet("/api/invoices", params);
        setInvoices(data);
    };        
    
    return (
        <div>
            <h1>Seznam faktur</h1>
            <hr />
            <InvoiceFilter
                handleChange={handleChange}
                handleSubmit={handleSubmit}
                handleReset={handleReset}                               
                personList={nonHiddenPersonsList}
                filter={filterState}
                confirm="Filtrovat faktury"                               
            />    
            <InvoiceTable
                deleteInvoice={deleteInvoice}
                items={invoicesState}
                label="Počet faktur:"
            />
        </div>
    );
};
export default InvoiceIndex;