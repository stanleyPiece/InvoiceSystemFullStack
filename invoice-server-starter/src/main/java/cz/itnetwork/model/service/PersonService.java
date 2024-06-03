package cz.itnetwork.model.service;

import cz.itnetwork.database.entity.PersonEntity;
import cz.itnetwork.model.dto.InvoiceDTO;
import cz.itnetwork.model.dto.PersonDTO;

import java.util.List;

public interface PersonService {

    /**
     * Creates a new person
     *
     * @param data new person's data
     * @return newly created person
     */
    PersonDTO createPerson(PersonDTO data);

    /**
     * Fetches the details of the given person
     * @param id
     * @return Person's details
     */
    PersonDTO getPersonDetails(long id);

    /**
     * "Edits" a given person
     * Hides the old person (for archiving purposes regarding invoices) and creates a new person
     *
     * @param personId ID of the person being edited
     * @param editedData edited data
     * @return edited person
     */
    PersonDTO editPerson(long personId, PersonDTO editedData);

    /**
     * <p>Sets hidden flag to true for the person with the matching [id]</p>
     * <p>In case a person with the passed [id] isn't found, the method <b>silently fails</b></p>
     *
     * @param id Person to delete
     */
     void deletePerson(long id);

    /**
     * Fetches a person
     *
     * @param id ID of the person to fetch
     * @return Fetched person
     */
     PersonEntity fetchPersonById(long id);

    /**
     * Fetches all persons
     *
     * @param includeHidden whether to include hidden persons
     * @return List of all persons (with or without hidden)
     */
    List<PersonDTO> getAllPersons(boolean includeHidden);

    /**
     * Fetches all issued invoices of a person
     *
     * @param identificationNumber identification number of a given person
     * @return list of issued invoices
     */
    List<InvoiceDTO> getSales(String identificationNumber);

    /**
     * Fetches all accepted invoices of a person
     * @param identificationNumber identification number of a given person
     * @return list of accepted invoices
     */
    List<InvoiceDTO> getPurchases(String identificationNumber);

}
