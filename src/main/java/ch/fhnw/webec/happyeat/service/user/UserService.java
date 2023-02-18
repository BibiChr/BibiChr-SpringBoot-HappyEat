package ch.fhnw.webec.happyeat.service.user;

import ch.fhnw.webec.happyeat.data.UserRepository;
import ch.fhnw.webec.happyeat.exception.wronginput.user.CantRegisterUser;
import ch.fhnw.webec.happyeat.exception.wronginput.user.NotCorrectInputForPassword;
import ch.fhnw.webec.happyeat.exception.wronginput.user.NotCorrectInputForUserChanges;
import ch.fhnw.webec.happyeat.model.Recipe;
import ch.fhnw.webec.happyeat.model.ShoppingList;
import ch.fhnw.webec.happyeat.model.User;
import ch.fhnw.webec.happyeat.util.PasswordUtil;
import ch.fhnw.webec.happyeat.util.PictureUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public List<User> getUsersNotCurrentUser() {
        return getAllUser().stream()
            .filter(u -> !getUsernameOfCurrentUser().equals(u.getUsername()))
            .toList();
    }

    public String getUsernameOfCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    private boolean registerNewUser(String firstName, String lastName, String email, String username, String password,
                                    List<ShoppingList> shoppingLists, List<Recipe> recipes) {
        if (usernameDontExists("", username) && emailDontExists("", email)) {
            var encodedPassword = PasswordUtil.encodePassword(password);
            var user =
                new User(firstName, lastName, email, username, encodedPassword, Set.of("USER"), shoppingLists, recipes);
            save(user);
            return true;
        }
        return false;
    }

    public boolean registerNewUser(String firstName, String lastName, String email, String username, String password) {
        if (firstName == null || firstName.equals("")
            || lastName == null || lastName.equals("")
            || email == null || email.equals("")
            || username == null || username.equals("")
            || password == null || password.equals("")
        ) {
            throw new CantRegisterUser();
        }
        return registerNewUser(firstName, lastName, email, username, password, Collections.emptyList(),
            Collections.emptyList());
    }

    public void registerNewUser(User user) {
        registerNewUser(user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getUsername(),
            user.getPassword(),
            user.getShoppingLists(),
            user.getRecipes()
        );
    }

    public boolean editUser(String oldUsername, String email, String newUsername) {
        if (oldUsername == null || email == null || newUsername == null
            || oldUsername.equals("") || email.equals("") || newUsername.equals("")) {
            throw new NotCorrectInputForUserChanges();
        }

        User user = getUserByUsername(oldUsername);
        if (usernameDontExists(oldUsername, newUsername) && emailDontExists(user.getEmail(), email)) {
            user.setEmail(email);
            user.setUsername(newUsername);

            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public void changePasswordOfUser(String username, String password) {
        if (password == null || password.equals("")) {
            throw new NotCorrectInputForPassword();
        }

        User user = getUserByUsername(username);
        var encodedPassword = PasswordUtil.encodePassword(password);
        user.setPassword(encodedPassword);
        save(user);
    }

    public void deleteUserAccount(String username) {
        userRepository.delete(getUserByUsername(username));
    }

    private boolean usernameDontExists(String oldUsername, String username) {
        if (oldUsername.equals(username)) {
            return true;
        }
        return userRepository.findByUsername(username).isEmpty();
    }

    private boolean emailDontExists(String oldEmail, String email) {
        if (oldEmail.equals(email)) {
            return true;
        }
        return userRepository.findByEmail(email).isEmpty();
    }

    public void uploadProfileImage(MultipartFile file, String username) {
        User user = getUserByUsername(username);
        if (!file.isEmpty()) {
            try {
                user.addImageData(PictureUtil.compressImage(file.getBytes()));
                save(user);
            } catch (Exception ignored) {
                //Exception ignored
            }
        }
    }

    public byte[] getImageData(String username) {
        User user = getUserByUsername(username);
        return PictureUtil.decompressImage(user.getImageData());
    }

}
