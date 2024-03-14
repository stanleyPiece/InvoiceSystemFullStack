package cz.itnetwork.model.service;

import cz.itnetwork.database.entity.UserEntity;
import cz.itnetwork.database.repository.UserRepository;
import cz.itnetwork.model.dto.UserDTO;
import cz.itnetwork.model.exception.DuplicateEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Method for creating a user
     *
      * @param model
     * @return Created user
     */
    @Override
    public UserDTO createUser(UserDTO model) {

        try {
            UserEntity newUser = new UserEntity();
            newUser.setEmail(model.getEmail());
            newUser.setPassword(passwordEncoder.encode(model.getPassword()));

            UserEntity savedUser = userRepository.save(newUser);

            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(savedUser.getUserId());
            userDTO.setEmail(savedUser.getEmail());

            return userDTO;

        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException();
        }
    }

    /**
     * Method for loading user's details (username, password, and authorities)
     *
     * @param username
     * @return currently logged-in user's information
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Uživatel " + username + " nebyl nalezen."));
    }
}
