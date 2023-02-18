package ch.fhnw.webec.happyeat.controller;

import ch.fhnw.webec.happyeat.service.user.UserService;
import ch.fhnw.webec.happyeat.util.ModelUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController extends ExceptionController {
    private static final String REDIRECT_PROFILE = "redirect:/profile";
    private final UserService userService;

    ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("user", userService.getUserByUsername(userService.getUsernameOfCurrentUser()));
        model.addAttribute("userPhoto", "/profile-picture/" + userService.getUsernameOfCurrentUser());
        return "/page/profile/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model, HttpServletRequest request) {
        ModelUtil.addToken(model, request);
        model.addAttribute("user", userService.getUserByUsername(userService.getUsernameOfCurrentUser()));
        return "/page/profile/profile-edit";
    }

    @PostMapping("/profile/edit")
    public String editProfile(Model model, @RequestParam String emailAddress, @RequestParam String username, HttpServletRequest request) {
        if (!userService.editUser(userService.getUsernameOfCurrentUser(), emailAddress, username)) {
            model.addAttribute("errorWithChanges",
                "Es gab ein Problem. Entweder wird der username oder die email schon verwendet." +
                    " Oder du hast falsche Daten eingegeben");
            ModelUtil.addToken(model, request);
            model.addAttribute("user", userService.getUserByUsername(userService.getUsernameOfCurrentUser()));
            return "/page/profile/profile-edit";
        }
        return REDIRECT_PROFILE;
    }

    @PostMapping("/profile/delete")
    public String deleteProfile() {
        userService.deleteUserAccount(userService.getUsernameOfCurrentUser());
        return "redirect:/login";
    }

    @PostMapping("/profile/edit/password")
    public String changePassword(@RequestParam String password) {
        userService.changePasswordOfUser(userService.getUsernameOfCurrentUser(), password);
        return REDIRECT_PROFILE;
    }

    @GetMapping("/profile-picture/{username}")
    public ResponseEntity<?> getProfilePicture(@PathVariable("username") String username) {
        byte[] image = userService.getImageData(username);
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.valueOf("image/png"))
            .body(image);
    }

    @PostMapping(value = "/{username}/add-photo")
    public String saveProfilePicture(@PathVariable String username, @RequestParam("image") MultipartFile file) {
        userService.uploadProfileImage(file, username);
        return REDIRECT_PROFILE;
    }

}
