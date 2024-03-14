package cz.itnetwork.model.service;

import cz.itnetwork.model.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * Creates a user
     *
     * @param userDTO User to be created
     * @return created user
     */
    UserDTO createUser(UserDTO userDTO);

}
