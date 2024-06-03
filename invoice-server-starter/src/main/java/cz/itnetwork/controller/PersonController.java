package cz.itnetwork.controller;

import cz.itnetwork.model.dto.InvoiceDTO;
import cz.itnetwork.model.dto.PersonDTO;
import cz.itnetwork.model.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/persons", "/api/identification"})
public class PersonController {

    @Autowired
    private PersonService personService;

    //region /API/PERSONS endpoints

    /**
     * Endpoint for handling the creation of a person
     * Only Admin role has permission to create a person
     *
     * @param data new person's data
     * @return created person
     */
    @Secured("ROLE_ADMIN")
    @PostMapping({"", "/"})
    public PersonDTO createPerson(@RequestBody PersonDTO data) {
        return personService.createPerson(data);
    }

    /**
     * Endpoint for handling the retrieval of a person's details
     *
     * @param personId person ID
     * @return person's details
     */
    @GetMapping({"/{personId}", "/{personId}/"})
    public PersonDTO getPersonDetail(@PathVariable Long personId) {
        return personService.getPersonDetails(personId);
    }

    /**
     * Endpoint for handling the "editing" of a person
     * Hides the old person (for archiving purposes regarding invoices) and creates a new person
     * Only Admin role has permission to edit a person
     *
     * @param personId ID of the person being edited
     * @param editedData edited data
     * @return edited person
     */
    @Secured("ROLE_ADMIN")
    @PutMapping({"/{personId}", "/{personId}/"})
    public PersonDTO editPerson(@PathVariable Long personId, @RequestBody PersonDTO editedData) {
        return personService.editPerson(personId, editedData);
    }

    /**
     * Endpoint for handling the deletion of a person
     * Only Admin role has permission to delete a person
     *
     * @param personId person ID
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping({"/{personId}", "/{personId}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long personId) {
        personService.deletePerson(personId);
    }

    /**
     * Endpoint for fetching all the persons
     *
     * @param includeHidden whether to include hidden or non-hidden persons
     * @return list of hidden/non-hidden persons
     */
    @GetMapping({"", "/"})
    public List<PersonDTO> getPersons(@RequestParam(value = "includeHidden", defaultValue = "false") boolean includeHidden) {
        return personService.getAllPersons(includeHidden);
    }
    //endregion

    //region /API/IDENTIFICATION endpoints

    /**
     * Endpoint for handling the retrieval of a person's issued invoices
     *
     * @param identificationNumber identification number
     * @return list of issued invoices
     */
    @GetMapping({"/{identificationNumber}/sales", "/{identificationNumber}/sales/"})
    public List<InvoiceDTO> getPersonSales(@PathVariable String identificationNumber) {
        return personService.getSales(identificationNumber);
    }

    /**
     * Endpoint for handling the retrieval of a person's accepted invoices
     *
     * @param identificationNumber identification number
     * @return list of accepted invoices
     */
    @GetMapping({"/{identificationNumber}/purchases", "/{identificationNumber}/purchases/"})
    public List<InvoiceDTO> getPersonPurchases(@PathVariable String identificationNumber) {
        return personService.getPurchases(identificationNumber);
    }
    //endregion
}

