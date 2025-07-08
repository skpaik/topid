package io.goribco.apis.controller;

import io.goribco.apis.configs.AppConstants;
import io.goribco.apis.configs.security.annotate.IsAdmin;
import io.goribco.apis.configs.security.annotate.IsUserCanCreate;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = AppConstants.crossOriginUrl, maxAge = 3600)
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private UserDataService userDataService;

//    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//    public ResponseEntity<?> generateToken(@RequestBody LoginEvent loginEvent) throws AuthenticationException {
//
//        final Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginEvent.getUsername(),
//                        loginEvent.getPassword()
//                )
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        final String token = jwtTokenUtil.generateToken(authentication);
//        return ResponseEntity.ok(new AuthToken(token));
//    }

//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public UserData saveUser(@RequestBody UserEvent user) {
//        return userDataService.save(user);
//    }

    @IsAdmin
    @RequestMapping(value = "/admin-ping", method = RequestMethod.GET)
    public String adminPing() {
        return "Only Admins Can Read This";
    }

    @IsUserCanCreate
    @GetMapping(value = "/user-ping")
    public String userPing() {
        return "Any UserData Can Read This 2";
    }
}