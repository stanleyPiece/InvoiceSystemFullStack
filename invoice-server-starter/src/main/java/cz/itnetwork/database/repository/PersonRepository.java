package cz.itnetwork.database.repository;

import cz.itnetwork.database.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    /**
     * Method for finding persons by their hidden status
     *
     * @param hidden whether to include hidden or non-hidden persons
     * @return list of found persons
     */
    List<PersonEntity> findByHidden(boolean hidden);


    /**
     * Method for finding persons by their identification number
     *
     * @param identificationNumber identification number
     * @return list of found persons
     */
    List<PersonEntity> findByIdentificationNumber(String identificationNumber);

    /**
     * Method for checking whether a person with a given identification number exists
     *
     * @param identificationNumber identification number
     * @return true or false
     */
    boolean existsByIdentificationNumber(String identificationNumber);
}
