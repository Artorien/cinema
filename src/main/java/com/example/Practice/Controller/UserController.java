package com.example.Practice.Controller;

import com.example.Practice.Film;
import com.example.Practice.PaymentBody;
import com.example.Practice.Repository.UserRepository;
import com.example.Practice.Service.FilmService;
import com.example.Practice.Service.UserService;
import com.example.Practice.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/practice")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private FilmService filmService;


    @GetMapping("/registration")
    public String renderRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerNewUser(@ModelAttribute("user") User user, Model model, HttpServletRequest request) {
        if (user.getUsername().isEmpty()) {
            model.addAttribute("emptyName", "Username can not be empty");
            return "registration";
        }
        if (user.getPassword().isEmpty()) {
            model.addAttribute("emptyPassword", "Password can not be empty");
            return "registration";
        }
        if (user.getUsername().length() < 5 && !user.getUsername().isEmpty()) {
            model.addAttribute("smallName", "Username can not be less than 5 characters");
            return "registration";
        }
        if (user.getPassword().length() < 5 && !user.getPassword().isEmpty()) {
            model.addAttribute("smallPassword", "Password can not be less than 5 characters");
            return "registration";
        }
        if (userService.isTaken(user.getUsername())) {
            model.addAttribute("takenUsername", "This username is taken");
            return "registration";
        }
        userService.saveUser(user);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        System.out.println(authToken);
        Authentication authenticatedUser = authenticationManager.authenticate(authToken);
        System.out.println(authenticatedUser);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        return "redirect:/practice/films";
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if ("emptyUsername".equals(error)) {
            model.addAttribute("usernameEmpty", "Username cannot be empty.");
        } else if ("emptyPassword".equals(error)) {
            model.addAttribute("passwordEmpty", "Password cannot be empty.");
        } else if ("invalid".equals(error)) {
            model.addAttribute("error", "Invalid username or password.");
        }
        return "login";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @PostMapping("/bridge")
    public ResponseEntity<String> bridge(@RequestBody PaymentBody paymentBody,
                                         HttpSession session) {
        System.out.println("id: " + paymentBody.getId() + ", price: " + paymentBody.getPrice());
        String url = "http://localhost:8081/payment/withdraw";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentBody> requestEntity = new HttpEntity<>(paymentBody, headers);
        User user = userRepository.findByUsername(session.getAttribute("username").toString());

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            Optional<Film> optionalFilm = filmService.findFilmById(Integer.parseInt(paymentBody.getId()));
            if (optionalFilm.isPresent()) {
                Film film = optionalFilm.get();
                List<Film> films = user.getBoughtFilms();
                films.add(film);
                user.setBoughtFilms(films);
                userService.updateUser(user);

                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("no such film");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
        }
    }

    @PostMapping("/newImage")
    public String newImage(@RequestParam("imageFile") MultipartFile imageFile,
                           HttpSession session) throws IOException {
        User user = userRepository.findByUsername(session.getAttribute("username").toString());

        userService.setImage(imageFile, user);

        return "redirect:/practice/profile";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session,
                          Model model) {
        String username = (String) session.getAttribute("username");
        User user = userRepository.findByUsername(username);
        String imageBase64 = null;

        if (user.getPfp() != null) {
            imageBase64 = Base64.getEncoder().encodeToString(user.getPfp());
        }

        String imageSrc = (imageBase64 != null) ? "data:image/jpeg;base64," + imageBase64 : "/images/default_pfp.png";

        model.addAttribute("imageSrc", imageSrc);
        model.addAttribute("user", user);
        return "profile";
    }


}
