import React from 'react';
import { useNavigate } from 'react-router-dom';

const BackButton = () => {
    const navigate = useNavigate();

    const goBack = () => {
        navigate(-1);
    };

    return (
        <button className="btn btn-secondary btn-sm me-1" onClick={goBack}>
            Zpět
        </button>
    );
};

export default BackButton;