/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */
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
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * Endpoint for fetching all the persons
     *
     * @param includeHidden whether to include hidden or non-hidden persons
     * @return list of hidden/non-hidden persons
     */
    @GetMapping({"/persons", "/persons/"})
    public List<PersonDTO> getPersons(@RequestParam(value = "includeHidden", defaultValue = "false") boolean includeHidden) {
        return personService.getAllPersons(includeHidden);
    }

    /**
     * Endpoint for handling the creation of a person
     * Only Admin role has permission to create a person
     *
     * @param personDTO person DTO
     * @return created person
     */
    @Secured("ROLE_ADMIN")
    @PostMapping({"/persons", "/persons/"})
    public PersonDTO createPerson(@RequestBody PersonDTO personDTO) {
        return personService.createPerson(personDTO);
    }

    /**
     * Endpoint for handling the retrieval of a person's details
     *
     * @param personId person ID
     * @return person's details
     */
    @GetMapping({"/persons/{personId}", "/persons/{personId}/"})
    public PersonDTO getPersonDetail(@PathVariable Long personId) {
        return personService.getPersonDetails(personId);
    }

    /**
     * Endpoint for handling the editing of a person
     * Only Admin role has permission to edit a person
     *
     * @param personDTO person DTO
     * @param personId person ID
     * @return edited person
     */
    @Secured("ROLE_ADMIN")
    @PutMapping({"/persons/{personId}", "/persons/{personId}/"})
    public PersonDTO editPerson(@RequestBody PersonDTO personDTO, @PathVariable Long personId) {
        return personService.editPerson(personId, personDTO);
    }

    /**
     * Endpoint for handling the deletion of a person
     * Only Admin role has permission to delete a person
     *
     * @param personId person ID
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping({"/persons/{personId}", "/persons/{personId}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long personId) {
        personService.deletePerson(personId);
    }

    /**
     * Endpoint for handling the retrieval of a person's issued invoices
     *
     * @param identificationNumber identification number
     * @return list of issued invoices
     */
    @GetMapping({"/identification/{identificationNumber}/sales", "/identification/{identificationNumber}/sales/"})
    public List<InvoiceDTO> getPersonSales(@PathVariable String identificationNumber) {
        return personService.getSales(identificationNumber);
    }

    /**
     * Endpoint for handling the retrieval of a person's accepted invoices
     *
     * @param identificationNumber identification number
     * @return list of accepted invoices
     */
    @GetMapping({"/identification/{identificationNumber}/purchases", "/identification/{identificationNumber}/purchases/"})
    public List<InvoiceDTO> getPersonPurchases(@PathVariable String identificationNumber) {
        return personService.getPurchases(identificationNumber);
    }
}

