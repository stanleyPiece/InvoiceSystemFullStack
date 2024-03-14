import { useEffect, useState } from "react"
import { apiGet } from "../utils/api";
import BackButton from "../utils/BackButton";

const InvoiceStatistics = () => {
    const [invoiceStats, setInvoiceStats] = useState("");

    useEffect(() => {
        apiGet("/api/invoices/statistics").then((data) => {
            setInvoiceStats(data);
        });
    }, [])

    return (
        <div>
            <h1>Statistika faktur</h1>
            <hr />
            <p>
                <b>Součet cen za letošní rok:</b> {invoiceStats.currentYearSum} Kč
            </p>
            <p>
                <b>Součet cen za všechny roky:</b> {invoiceStats.allTimeSum} Kč
            </p>
            <p>
                <b>Počet faktur v databázi:</b> {invoiceStats.invoicesCount}
            </p>
            <BackButton />
        </div>
    )

};

export default InvoiceStatistics;