package uz.carapp.rentcarapp.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import uz.carapp.rentcarapp.config.Constants;
import uz.carapp.rentcarapp.domain.User;
import uz.carapp.rentcarapp.repository.UserRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.rest.vm.LoginVM;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.security.UserDetailsServiceImpl;
import uz.carapp.rentcarapp.security.jwt.JWTFilter;
import uz.carapp.rentcarapp.security.jwt.JwtProvider;
import uz.carapp.rentcarapp.service.dto.UserAccountDTO;
import uz.carapp.rentcarapp.service.dto.UserRegDTO;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
@Tag(name = "Account", description = "The User-moder API. Contains all the operations that can be performed on an account.")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private static final String ENTITY_NAME = "AdminUser";
    private final UserRepository userRepository;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtProvider jwtProvider;

    private final UserDetailsServiceImpl userService;

    /**
     * {@code GET  /me} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account/me")
    @Operation(summary = "Get the current user",
            description = "This method return current user with authorities",
            security = @SecurityRequirement(name = "bearerAuth"))
    public UserAccountDTO getAccount() {
        log.info("REQUEST to get current user dates!");
        return userService.getUserWithAuthorities().map(UserAccountDTO::new)
                .orElseThrow(() -> new BadRequestCustomException("User could not be found","",""));
    }


    @PostMapping("/account/auth")
    @Operation(summary = "Sing-in for admin page", description = "This method of request of authentication user")
    public ResponseEntity<JWTToken> signInMManager(@Valid @RequestBody LoginVM loginVM) {
        log.info("REST request to create user by admin page by the login: {}", loginVM.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        log.info("before AccountResource: ");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("after AccountResource: ");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        log.info("token generated successfully: {}",jwt);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/account/create-user")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Create user")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserRegDTO userRegDTO) {
        log.info("REST request to create user: {}", userRegDTO);
        if(userRepository.findOneByEmailIgnoreCase(userRegDTO.getEmail().toLowerCase()).isPresent()) {
            throw new BadRequestCustomException("Email address already in use","","");
        }
        userService.createUser(userRegDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/account/list")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Create user")
    public ResponseEntity<List<UserRegDTO>> getAllUsers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.info("REST request to get all users");
        Page<UserRegDTO> page = userService.findAll(pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),page);
        return ResponseEntity.ok().headers(httpHeaders).body(page.getContent());
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {return idToken;}

        void setIdToken(String idToken) { this.idToken = idToken; }
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
                StringUtils.isEmpty(password) ||
                        password.length() > Constants.PASSWORD_MAX_LENGTH ||
                        password.length() < Constants.PASSWORD_MIN_LENGTH
        );
    }


}
