import { useEffect, useState } from "react"
import { apiGet } from "../utils/api";
import BackButton from "../utils/BackButton";

const PersonStatistics = () => {
    const [personsStats, setPersonsStats] = useState([]);

    useEffect(() => {
        apiGet("/api/persons/statistics").then((data) => {
            setPersonsStats(data);
        });
    }, [])

    return (
        <div>
            <h1>Statistika osob</h1>
            <hr />
            <table className="table table-bordered border-success table-striped">
                <thead className="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Jméno osoby</th>
                        <th>Fakturované příjmy</th>
                    </tr>
                </thead>
                <tbody>
                    {personsStats.map((person, index) => (
                        <tr key={index}>                            
                            <td>{person.personId}</td>
                            <td><a href={`/persons/show/${person.personId}`}>{person.personName}</a></td>
                            <td>{person.revenue} Kč</td>
                        </tr> 
                    ))}
                </tbody>
            </table>
            <BackButton />            
        </div>
    )

};

export default PersonStatistics;