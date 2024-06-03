package cz.itnetwork.model.service;

import cz.itnetwork.database.entity.InvoiceEntity;
import cz.itnetwork.database.entity.PersonEntity;
import cz.itnetwork.database.repository.InvoiceRepository;
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

    @Autowired
    private InvoiceRepository invoiceRepository;

    /**
     * Method for creating a person
     *
     * @param personDTO DTO of a person to create
     * @return created person
     */
    @Override
    public PersonDTO createPerson(PersonDTO personDTO) {
        PersonEntity newPerson = personMapper.toEntity(personDTO);
        PersonEntity savedPerson = personRepository.save(newPerson);

        return personMapper.toDTO(savedPerson);
    }

    /**
     * Method for editing a person
     *
     * @param personId ID of person to edit
     * @param editedPersonDTO DTO of edited person
     * @return edited person
     */
    @Override
    public PersonDTO editPerson(Long personId, PersonDTO editedPersonDTO) {

        PersonEntity personToHide = fetchPersonById(personId);
        personToHide.setHidden(true);
        personRepository.save(personToHide);

        editedPersonDTO.setId(null);
        PersonEntity editedPerson = personMapper.toEntity(editedPersonDTO);
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

        if (!includedHidden) {
            persons = personRepository.findByHidden(false);
        } else {
            persons = personRepository.findByHidden(true);
        }

        return persons
                .stream()
                .map(i -> personMapper.toDTO(i))
                .collect(Collectors.toList());
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

    //region private method for editing and removing a person, and fetching details of a person

    /**
     * <p>Attempts to fetch a person.</p>
     * <p>In case a person with the passed [id] doesn't exist a [{@link org.webjars.NotFoundException}] is thrown.</p>
     *
     * @param id Person to fetch
     * @return Fetched entity
     * @throws EntityNotFoundException In case a person with the passed [id] isn't found
     */
    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Osoba s ID " + id + " nebyla v databázi nalezena."));
    }

    //endregion

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
     * @param identificationNumber identification number of a person
     * @return fetched person
     * @throws EntityNotFoundException, if the person is not found
     */
    private List<PersonEntity> getPersonByIdentificationNumber(String identificationNumber) {
        if (!personRepository.existsByIdentificationNumber(identificationNumber)) {
            throw new EntityNotFoundException(("Osoba s identifikačním číslem " + identificationNumber + " nebyla nalezena."));
        }
        List<PersonEntity> foundPersons = personRepository.findByIdentificationNumber(identificationNumber);
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
