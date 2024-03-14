import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { apiGet, apiPost, apiPut } from "../utils/api";

import InputField from "../components/InputField";
import InputSelect from "../components/InputSelect";
import { FlashMessageOK } from "../components/FlashMessage";
import BackButton from "../utils/BackButton";
import "../styles.css";

import { useSession } from "../contexts/session";

const InvoiceForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();

    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;
    const isLoadingSession = session.status === "loading";

    const [invoice, setInvoice] = useState({
        invoiceNumber: "",
        seller: "",
        buyer: "",
        issued: "",
        dueDate: "",
        product: "",
        price: "",
        vat: "",
        note: ""
    });
    const [hiddenPersons, setHiddenPersons] = useState([]);
    const [nonHiddenPersons, setNonHiddenPersons] = useState([]);
    const [sentState, setSent] = useState(false);
    const [successState, setSuccess] = useState(false);
    const [errorState, setError] = useState(null);

    const [showSellerWarning, setShowSellerWarning] = useState(false);
    const [showBuyerWarning, setShowBuyerWarning] = useState(false);       

    useEffect(() => {
        if (!isAdmin && !isLoadingSession) {
            if (id) {
                navigate("/invoices/show/" + id);
            } else {
                navigate("/invoices");
            }
        }
    }, [isAdmin, isLoadingSession, id]);

    useEffect(() => {
        if (id) {
            apiGet("/api/invoices/" + id)
                .then((data) => {
                    setInvoice({
                        ...data,
                        buyer: data.buyer._id,
                        seller: data.seller._id
                    });
                    apiGet("/api/persons", { includeHidden: true }).then((data) => setHiddenPersons(data));                
                });
        };
        apiGet("/api/persons", { includeHidden: false }).then((data) => setNonHiddenPersons(data));
    }, [id]);

    const handleSubmit = (e) => {
        e.preventDefault();

        if (!invoice.seller) {
            setShowSellerWarning(true);
            return;
        }

        if (!invoice.buyer) {
            setShowBuyerWarning(true);
            return;
        }

        const formattedInvoice = {
            ...invoice,
            seller: { _id: invoice.seller },
            buyer: { _id: invoice.buyer }
        };

        (id ? apiPut("/api/invoices/" + id, formattedInvoice) : apiPost("/api/invoices", formattedInvoice))
            .then((data) => {
                setSent(true);
                setSuccess(true);

            })
            .catch((error) => {
                console.log(error.message);
                setError(error.message);
                setSent(true);
                setSuccess(false);
            });
    };

    const sent = sentState;
    const success = successState;

    if (!isAdmin) {
        return (
            <div className="d-flex justify-content-center mt-2">
                <div className="spinner-border spinner-border-sm" role="status">
                    <span className="visually-hidden">Načítám...</span>
                </div>
            </div>
        );
    }

    return (
        <div>
            <h1>{id ? "Upravit" : "Vytvořit"} fakturu</h1>
            <hr />
            {errorState ? (
                <div className="alert alert-danger">{errorState}</div>
            ) : null}
            <div className="flash-message-container">
                {sent && (
                    <FlashMessageOK
                        theme={success ? "success" : ""}
                        text={success ? "Uložení faktury proběhlo úspěšně." : ""}
                        onClose={() => {
                            setSent(true);
                            setSuccess(true);
                            navigate("/invoices");
                        }}
                    />
                )}
            </div>
            <form onSubmit={handleSubmit}>
                <InputField
                    required={true}
                    type="text"
                    name="invoiceNumber"
                    label="Číslo faktury"
                    prompt="Zadejte číslo faktury"
                    value={invoice.invoiceNumber}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, invoiceNumber: e.target.value });
                    }}
                />

                {id ? (
                    <div>
                        {hiddenPersons.find(person => person._id === invoice.seller) ? (
                            <div>
                                <label htmlFor="seller">Dodavatel:</label>
                                <div style={{ border: '1px solid #ced4da', borderRadius: '0.25rem', padding: '0.375rem 0.75rem', marginTop: '0.5rem' }}>
                                    {hiddenPersons.find(person => person._id === invoice.seller)?.name}
                                </div>
                                <div className="alert alert-danger">Pozor! Tento dodavatel je archivovaný a nelze jej změnit.</div>
                            </div>
                        ) : nonHiddenPersons.find(person => person._id === invoice.seller) ? (
                            <div>
                                <InputSelect
                                    name="seller"
                                    items={nonHiddenPersons}
                                    label="Dodavatel"
                                    prompt="Vyberte dodavatele"
                                    value={invoice.seller}
                                    handleChange={(e) => {
                                        setInvoice({ ...invoice, seller: e.target.value });
                                        setShowSellerWarning(false);
                                    }}
                                />
                            </div>
                        ) : (
                            <div>
                                Chyba! Tento dodavatel nebyl v databázi nalezen!
                            </div>
                        )}
                    </div>

                ) : (<InputSelect
                    name="seller"
                    items={personsListState}
                    label="Dodavatel"
                    prompt="Vyberte dodavatele"
                    value={invoice.seller}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, seller: e.target.value });
                        setShowSellerWarning(false);
                    }}
                />
                )}

                {showSellerWarning && (
                    <div className="alert alert-warning">Prosím vyberte dodavatele.</div>
                )}

                {id ? (
                    <div>
                        {hiddenPersons.find(person => person._id === invoice.buyer) ? (
                            <div>
                                <label htmlFor="seller">Odběratel:</label>
                                <div style={{ border: '1px solid #ced4da', borderRadius: '0.25rem', padding: '0.375rem 0.75rem', marginTop: '0.5rem' }}>
                                    {hiddenPersons.find(person => person._id === invoice.buyer)?.name}
                                </div>
                                <div className="alert alert-danger">Pozor! Tento odběratel je archivovaný a nelze jej změnit.</div>
                            </div>
                        ) : nonHiddenPersons.find(person => person._id === invoice.buyer) ? (
                            <div>
                                <InputSelect
                                    name="buyer"
                                    items={nonHiddenPersons}
                                    label="Odběratel"
                                    prompt="Vyberte odběratele"
                                    value={invoice.buyer}
                                    handleChange={(e) => {
                                        setInvoice({ ...invoice, buyer: e.target.value });
                                        setShowBuyerWarning(false);
                                    }}
                                />
                            </div>
                        ) : (
                            <div>
                                Chyba! Tento odběratel nebyl v databázi nalezen!
                            </div>
                        )}
                    </div>

                ) : (
                    <InputSelect
                        name="buyer"
                        items={personsListState}
                        label="Odběratel"
                        prompt="Vyberte odběratele"
                        value={invoice.buyer}
                        handleChange={(e) => {
                            setInvoice({ ...invoice, buyer: e.target.value });
                            setShowBuyerWarning(false);
                        }}
                    />
                )}

                {showBuyerWarning && (
                    <div className="alert alert-warning">Prosím vyberte odběratele.</div>
                )}

                <InputField
                    required={true}
                    type="date"
                    name="issued"
                    min="0000-01-01"
                    label="Datum vystavení"
                    value={invoice.issued}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, issued: e.target.value });
                    }}
                />

                <InputField
                    required={true}
                    type="date"
                    name="dueDate"
                    min="0000-01-01"
                    label="Datum splatnosti"
                    value={invoice.dueDate}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, dueDate: e.target.value });
                    }}
                />

                <InputField
                    required={true}
                    type="text"
                    name="product"
                    label="Produkt"
                    prompt="Zadejte produkt"
                    value={invoice.product}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, product: e.target.value });
                    }}
                />

                <InputField
                    required={true}
                    type="number"
                    name="price"
                    min="1"
                    label="Cena"
                    prompt="Zadejte cenu produktu"
                    value={invoice.price}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, price: e.target.value });
                    }}
                />

                <InputField
                    required={true}
                    type="number"
                    name="vat"
                    min="3"
                    label="VAT"
                    prompt="Zadejte VAT"
                    value={invoice.vat}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, vat: e.target.value });
                    }}
                />

                <InputField
                    type="textarea"
                    name="note"
                    label="Poznámka"
                    rows="1"
                    value={invoice.note}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, note: e.target.value });
                    }}
                />

                <div className="mt-2">
                    <input type="submit" className="btn btn-primary btn-sm me-1" value="Uložit" />
                    <BackButton />
                </div>

            </form>
        </div>
    );
};

export default InvoiceForm;