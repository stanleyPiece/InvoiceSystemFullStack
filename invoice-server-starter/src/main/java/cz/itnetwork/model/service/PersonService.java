package cz.itnetwork.model.service;

import cz.itnetwork.model.dto.InvoiceDTO;
import cz.itnetwork.model.dto.PersonDTO;

import java.util.List;

public interface PersonService {

    /**
     * Creates a new person
     *
     * @param personDTO Person to create
     * @return newly created person
     */
    PersonDTO createPerson(PersonDTO personDTO);

    /**
     * Edits a given person
     * @param personDTO Person to edit
     * @return edited person
     */
    PersonDTO editPerson(Long personId, PersonDTO personDTO);

    /**
     * <p>Sets hidden flag to true for the person with the matching [id]</p>
     * <p>In case a person with the passed [id] isn't found, the method <b>silently fails</b></p>
     *
     * @param id Person to delete
     */
     void deletePerson(long id);

    /**
     * Fetches all persons
     *
     * @param includeHidden whether to include hidden persons
     * @return List of all persons (with or without hidden)
     */
    List<PersonDTO> getAllPersons(boolean includeHidden);

    /**
     * Fetches the details of the given person
     * @param id
     * @return Person's details
     */
    PersonDTO getPersonDetails(long id);

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
