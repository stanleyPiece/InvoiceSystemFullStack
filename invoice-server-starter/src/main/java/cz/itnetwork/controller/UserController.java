package cz.itnetwork.controller;

import cz.itnetwork.database.entity.UserEntity;
import cz.itnetwork.model.dto.UserDTO;
import cz.itnetwork.model.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint for returning information about the currently logged-in user
     *
     * @return information about currently logged-in user (sensitive information not included)
     * @throws ServletException In case the retrieval is unsuccessful
     */
    @GetMapping("/auth")
    public UserDTO getCurrentUser() throws ServletException {
        try {
            UserEntity currentUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            UserDTO currentUserInformation = new UserDTO();

            currentUserInformation.setEmail(currentUser.getEmail());
            currentUserInformation.setUserId(currentUser.getUserId());
            currentUserInformation.setAdmin(currentUser.isAdmin());

            return currentUserInformation
                    ;
        } catch (ClassCastException e) {
            throw new ServletException();
        }
    }

    /**
     * Endpoint for handling the creation of a user
     *
     * @param userDTO user DTO
     * @return newly created (added) user
     */
    @PostMapping({"/user", "/user/"})
    public UserDTO addUser(@RequestBody @Valid UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    /**
     * Endpoint for handling the login of a user
     *
     * @param userDTO user DTO
     * @param request HTTP request
     * @return user DTO with non-sensitive data (=password excluded)
     * @throws ServletException In case login is not successful (user is already logged-in)
     */
    @PostMapping({"/auth", "/auth/"})
    public UserDTO login(@RequestBody @Valid UserDTO userDTO, HttpServletRequest request) throws ServletException {
        request.login(userDTO.getEmail(), userDTO.getPassword());

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDTO model = new UserDTO();
        model.setEmail(user.getEmail());
        model.setUserId(user.getUserId());
        model.setAdmin(user.isAdmin());

        return model;
    }

    /**
     * Endpoint for handling the logout of a user
     *
     * @param request request
     * @return confirmation of user's logout
     * @throws ServletException In case logout is not successful
     */
    @DeleteMapping({"/auth", "/auth/"})
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "Uživatel byl odhlášen";
    }

    /**
     * Handler for exceptions
     */
    @ExceptionHandler(ServletException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleServletException() {
    }
}
