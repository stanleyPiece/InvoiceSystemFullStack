package cz.itnetwork.model.service;

import cz.itnetwork.database.entity.InvoiceEntity;
import cz.itnetwork.database.entity.PersonEntity;
import cz.itnetwork.database.repository.PersonRepository;
import cz.itnetwork.model.dto.InvoiceDTO;
import cz.itnetwork.model.dto.PersonDTO;
import cz.itnetwork.model.dto.mapper.InvoiceMapper;
import cz.itnetwork.model.dto.mapper.PersonMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonServiceImplementation implements PersonService {

    @Autowired //mapper injection
    private PersonMapper personMapper;

    @Autowired //mapper injection
    private PersonRepository personRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    /**
     * Method for creating a person
     *
     * @param data new person's data
     * @return created person
     */
    @Override
    public PersonDTO createPerson(PersonDTO data) {

        PersonEntity newPerson = personMapper.toEntity(data);
        PersonEntity savedPerson = personRepository.save(newPerson);

        return personMapper.toDTO(savedPerson);

    }

    /**
     * Method for retrieving a person's details
     *
     * @param personId ID of person for which to retrieve details
     * @return person's details
     */
    @Override
    public PersonDTO getPersonDetails(long personId) {

        PersonEntity fetchedPerson = fetchPersonById(personId);

        return personMapper.toDTO(fetchedPerson);

    }

    /**
     * Method for "editing" a person
     * Hides the old person (for archiving purposes regarding invoices) and creates a new person
     *
     * @param personId ID of the person to hide
     * @param data data of the new person
     * @return "edited" person
     */
    @Override
    public PersonDTO editPerson(long personId, PersonDTO data) {

        PersonEntity personToHide = fetchPersonById(personId);
        personToHide.setHidden(true);
        personRepository.save(personToHide);

        data.setId(null);
        PersonEntity editedPerson = personMapper.toEntity(data);
        personRepository.save(editedPerson);

        return personMapper.toDTO(editedPerson);

    }

    /**
     * Method for deleting a person
     *
     * @param personId ID of the person to delete
     */
    @Override
    public void deletePerson(long personId) {

        try {
            PersonEntity fetchedPerson = fetchPersonById(personId);
            fetchedPerson.setHidden(true);
            personRepository.save(fetchedPerson);
        } catch (NotFoundException ignored) {
            // The contract in the interface states, that no exception is thrown, if the entity is not found.
        }

    }

    /**
     * Method for retrieving every person
     *
     * @param includedHidden whether to include hidden or non-hidden persons
     * @return list of persons
     */
    @Override
    public List<PersonDTO> getAllPersons(boolean includedHidden) {

        List<PersonEntity> persons = new ArrayList<>();
        persons = includedHidden ? personRepository.findByHidden(true) : personRepository.findByHidden(false);

        return persons
                .stream()
                .map(i -> personMapper.toDTO(i))
                .collect(Collectors.toList());

    }

    /**
     * <p>Attempts to fetch a person.</p>
     * <p>In case a person with the passed [id] doesn't exist a [{@link org.webjars.NotFoundException}] is thrown.</p>
     *
     * @param personId Person to fetch
     * @return Fetched entity
     * @throws EntityNotFoundException In case a person with the passed [id] isn't found
     */
    @Override
    public PersonEntity fetchPersonById(long personId) {

        return personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person with " + personId + " was not found in the database."));

    }

    /**
     * Method for retrieving all issued invoices of a person
     *
     * @param personIdentificationNumber identification number of a given person
     * @return list of issued invoices
     */
    @Override
    public List<InvoiceDTO> getSales(String personIdentificationNumber) {

        Stream<PersonEntity> sellers = getPersonByIdentificationNumber(personIdentificationNumber).stream();
        List<InvoiceDTO> sales = mapToDTO(sellers, this::extractSales);
        return sales;

    }

    /**
     * Method for retrieving all accepted invoices of a person
     *
     * @param personIdentificationNumber identification number of a given person
     * @return list of accepted invoices
     */
    @Override
    public List<InvoiceDTO> getPurchases(String personIdentificationNumber) {

        Stream<PersonEntity> buyers = getPersonByIdentificationNumber(personIdentificationNumber).stream();
        List<InvoiceDTO> purchases = mapToDTO(buyers, this::extractPurchases);
        return purchases;

    }

    //region: private methods for sales and purchases

    /**
     * Tries to fetch a person by their identification number
     * If not found, an exception is thrown (see EntityNotFoundExceptionAdvice in cz.itnetwork/model/exception/advice for handling)
     *
     * @param personIdentificationNumber identification number of a person
     * @return fetched person
     * @throws EntityNotFoundException, if the person is not found
     */
    private List<PersonEntity> getPersonByIdentificationNumber(String personIdentificationNumber) {

        if (!personRepository.existsByIdentificationNumber(personIdentificationNumber)) {
            throw new EntityNotFoundException(("Person with identification number " + personIdentificationNumber + " was not found in the database."));
        }
        List<PersonEntity> foundPersons = personRepository.findByIdentificationNumber(personIdentificationNumber);
        return foundPersons;

    }

    /**
     * /**
     * Converts a stream of persons' invoices to a List of DTOs
     *
     * @param persons stream of persons
     * @param purchasesOrSalesExtractor extractor for extracting sales/purchases
     * @return list of DTOs
     */
    private List<InvoiceDTO> mapToDTO(Stream<PersonEntity> persons, Function<PersonEntity, List<InvoiceEntity>> purchasesOrSalesExtractor) { //we're using a Function. from java.util.function, where we provide one element to get a different element

        return persons.map(purchasesOrSalesExtractor.andThen(transactionList -> transactionList.stream()
                        .map(invoiceMapper::toDTO) //maps each person's invoices to a list of invoices
                        .collect(Collectors.toList()))) //saves to a List
                        .flatMap(List::stream) //flattens the resulting lists into a stream
                        .collect(Collectors.toList()); //collects all the invoices into a List

    }

    /**
     * Method for extracting sales from a person
     *
     * @param personEntity Person to extract sales from
     * @return sales invoices of a person
     */
    private List<InvoiceEntity> extractSales(PersonEntity personEntity) {

        return personEntity.getSales();

    }

    /**
     * Method for extracting purchases from a person
     *
     * @param personEntity Person to extract purchases from
     * @return purchase invoices of a person
     */
    private List<InvoiceEntity> extractPurchases(PersonEntity personEntity) {

        return personEntity.getPurchases();

    }
    // endregion
}
