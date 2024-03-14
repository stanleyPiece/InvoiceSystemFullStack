import React, {useEffect, useState} from "react";

import {apiDelete, apiGet} from "../utils/api";

import PersonTable from "./PersonTable";

const PersonIndex = () => {    
    const [nonHiddenPersons, setNonHiddenPersons] = useState([]);        
    const [hiddenPersons, setHiddenPersons] = useState([]);        

    const deletePerson = async (id) => {
        try {
            await apiDelete("/api/persons/" + id);            
        } catch (error) {
            console.log("Chyba při odstraňování osoby:", error);
            alert(error.message)
        }
        setNonHiddenPersons(nonHiddenPersons.filter((item) => item._id !== id));        
        apiGet("/api/persons", { includeHidden: true }).then((data) => setHiddenPersons(data));
    };

    useEffect(() => {
        apiGet("/api/persons", {includeHidden: false}).then((data) => setNonHiddenPersons(data));        
        apiGet("/api/persons", {includeHidden: true}).then((data) => setHiddenPersons(data));        
    }, []);        

    return (
        <div>
            <div>
                <h1>Seznam osob</h1>
                <hr />
                <PersonTable 
                    deletePerson={deletePerson}
                    items={nonHiddenPersons}
                    label="Počet osob:"
                    hideButtons={false}
                />
            </div>           
            <div className="column mt-5">
                <h2>Seznam archivovaných osob:</h2>
                <hr />
                <PersonTable
                    items={hiddenPersons}
                    label="Počet archivovaných osob:"
                    hideButtons={true}
                />
            </div>
        </div>
    );
};
export default PersonIndex;
