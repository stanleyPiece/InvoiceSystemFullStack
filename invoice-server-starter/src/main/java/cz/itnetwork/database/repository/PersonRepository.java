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
package cz.itnetwork.database.repository;

import cz.itnetwork.database.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    /**
     * Method for finding persons by their hidden status
     *
     * @param hidden
     * @return list of found persons
     */
    List<PersonEntity> findByHidden(boolean hidden);


    /**
     * Method for finding persons by their identification number
     *
     * @param identificationNumber
     * @return list of found persons
     */
    List<PersonEntity> findByIdentificationNumber(String identificationNumber);

    /**
     * Method for checking whether a person with a given identification number exists
     *
     * @param identificationNumber
     * @return
     */
    boolean existsByIdentificationNumber(String identificationNumber);
}
