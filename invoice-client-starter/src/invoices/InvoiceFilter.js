import React from 'react';
import InputSelect from '../components/InputSelect';
import InputField from '../components/InputField';

const InvoiceFilter = (props) => {

    const handleChange = (e) => {
        props.handleChange(e);
    };

    const handleSubmit = (e) => {
        props.handleSubmit(e);
    };    

    const handleReset = (e) => {
        props.handleReset(e);
    };

    const filter = props.filter;

    return (
        <form onSubmit={handleSubmit}>
        <div className="row">
            <div className="col">
                <InputSelect
                    name="sellerID"
                    items={props.personList}
                    handleChange={handleChange}
                    label="Dodavatel"
                    prompt="nevybrán"
                    value={filter.sellerID ? filter.sellerID : ''}
                />
            </div>
            <div className="col">
                <InputSelect
                    name="buyerID"
                    items={props.personList}
                    handleChange={handleChange}
                    label="Odběratel"
                    prompt="nevybrán"
                    value={filter.buyerID ? filter.buyerID : ''}
                />
            </div> 
            <div className="col">
                <InputField
                    type="text"
                    name="product"                    
                    handleChange={handleChange}                    
                    label="Produkt"
                    prompt="nezadán"
                    value={filter.product}                    
                />                
            </div>
        </div>

        <div className="row">
            <div className="col">
                <InputField
                    type="text"                    
                    name="minPrice"
                    handleChange={handleChange}                    
                    label="Minimální cena"
                    prompt="neuvedena"
                    value={filter.minPrice ? filter.minPrice : ''}
                />
            </div>

            <div className="col">
                <InputField
                    type="text"                    
                    name="maxPrice"
                    handleChange={handleChange}                    
                    label="Maximální cena"
                    prompt="neuvedena"
                    value={filter.maxPrice ? filter.maxPrice : ''}
                />
            </div>

            <div className="col">
                <InputField
                    type="number"
                    min="1"
                    name="limit"
                    handleChange={handleChange}
                    label="Limit počtu faktur"
                    prompt="neuveden"
                    value={filter.limit ? filter.limit : ''}
                />
            </div>
        </div>

        <div className="row">
            <div className="col">
                <input
                    type="submit"
                    className="btn btn-success float-right mt-2 me-2"
                    value={props.confirm}
                />
                <button
                    type="button"
                    className="btn btn-secondary float-right mt-2 me-2"
                    onClick={handleReset}
                >
                    Resetovat filtry
                </button>                                                                 
            </div>                        
        </div>
    </form>
    );
};

export default InvoiceFilter;