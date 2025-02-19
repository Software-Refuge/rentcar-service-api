package uz.carapp.rentcarapp.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import uz.carapp.rentcarapp.config.Constants;
import uz.carapp.rentcarapp.domain.MerchantBranch;
import uz.carapp.rentcarapp.domain.MerchantRole;
import uz.carapp.rentcarapp.domain.User;
import uz.carapp.rentcarapp.domain.enumeration.MerchantRoleEnum;
import uz.carapp.rentcarapp.repository.MerchantBranchRepository;
import uz.carapp.rentcarapp.repository.MerchantRoleRepository;
import uz.carapp.rentcarapp.repository.UserRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.rest.vm.LoginVM;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.security.UserDetailsServiceImpl;
import uz.carapp.rentcarapp.security.jwt.JWTFilter;
import uz.carapp.rentcarapp.security.jwt.JwtProvider;
import uz.carapp.rentcarapp.security.jwt.JwtUtil;
import uz.carapp.rentcarapp.service.dto.MerchantSelectDTO;
import uz.carapp.rentcarapp.service.dto.UserAccountDTO;
import uz.carapp.rentcarapp.service.dto.UserDTO;
import uz.carapp.rentcarapp.service.dto.UserRegDTO;

import java.util.*;

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
    private final MerchantRoleRepository merchantRoleRepository;
    private final MerchantBranchRepository merchantBranchRepository;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtProvider jwtProvider;

    private final UserDetailsServiceImpl userService;

    private final JwtUtil jwtUtil;
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
        log.info("REQUEST to get current user data");
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
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserRegDTO userRegDTO) {
        log.info("REST request to create user: {}", userRegDTO);
        if(userRepository.findOneByEmailIgnoreCase(userRegDTO.getEmail().toLowerCase()).isPresent()) {
            throw new BadRequestCustomException("Email address already in use","","");
        }
        UserDTO user = userService.createUser(userRegDTO);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/account/list")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Operation(summary = "Get list of users")
    public ResponseEntity<List<UserRegDTO>> getAllUsers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.info("REST request to get all users");
        Page<UserRegDTO> page = userService.findAll(pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),page);
        return ResponseEntity.ok().headers(httpHeaders).body(page.getContent());
    }

    @PostMapping("/merchant/auth")
    @Operation(summary = "Sign-in for merchant")
    public ResponseEntity<?> authMerchant(@Valid @RequestBody LoginVM loginVM) {
        log.info("REST request to sign-in for merchant: {}", loginVM.getUsername());

        // 1. Foydalanuvchini tekshiramiz
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        // 2. Userni topamiz
        User user = userRepository.findOneByLogin(loginVM.getUsername())
                .orElseThrow(() -> new BadRequestCustomException("User could not be found","",""));

        // 3. Foydalanuvchining bog'langan Merchant va filiallarini topamiz
        List<MerchantRole> roles = merchantRoleRepository.findByUserId(user.getId());

        if(roles.isEmpty()) {
            throw new BadRequestCustomException("User has no merchant roles","","");
        }

        // OWNER bo'lsa, barcha branchlarga avtomatik kirish huquqi bo'ladi
        boolean isOwner = roles.stream().anyMatch(role->role.getMerchantRoleType().equals(MerchantRoleEnum.ROLE_OWNER));

        // 4. Merchant va Branch larni guruhlash
        Map<Long, List<MerchantRoleEnum>> merchantBranches = new HashMap<>();

        if(isOwner) {
            //Agar OWNER bo'lsa, barcha filiallarni olish
            List<Long> branchIds = merchantBranchRepository.getBranchIdsByUserId(user.getId());
            branchIds.forEach(branchId -> merchantBranches.put(branchId, List.of(MerchantRoleEnum.ROLE_OWNER)));
        } else {
            for (MerchantRole role : roles) {
                merchantBranches
                        .computeIfAbsent(role.getMerchantBranch().getId(), k -> new ArrayList<>())
                        .add(role.getMerchantRoleType());
            }
        }

        // 5. Session uchun vaqtinchalik JWT token yaratamiz
        String tempToken = jwtUtil.generateTemporaryToken(user);

        return ResponseEntity.ok(new AuthResponse(user.getId(), merchantBranches, tempToken));

    }


    @PostMapping("/merchant/select-branch")
    @Operation(summary = "get token by merchantId and branchId")
    public ResponseEntity<?> selectMerchant(@RequestBody MerchantSelectDTO request) {
        log.info("Rest request select merchant:{}",request);

        if(request == null || request.getToken() == null || request.getToken().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header is missing");
        }

        String login = jwtUtil.validateToken(request.getToken());

        if (login == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid session");
        }

        User user = userRepository.findOneByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Optional<MerchantBranch> optionalBranchMerchant = merchantBranchRepository.getMerchantBranchById(user.getId(),
                request.getMerchantBranchId());

        if(optionalBranchMerchant.isEmpty()) {
            throw new BadRequestCustomException("Branch not found","","");
        }

        List<MerchantRole> merchantRoles = merchantRoleRepository.findByUserIdAndMerchantIdAndBranchId(user.getId(),
                request.getMerchantId(), request.getMerchantBranchId());
        if(merchantRoles.isEmpty())
                merchantRoles = merchantRoleRepository.findByUserIdAndMerchantIdAndRoleType(user.getId(), request.getMerchantId(), MerchantRoleEnum.ROLE_OWNER);

        if(merchantRoles.isEmpty()) {
            throw new BadRequestCustomException("No merchant Access","","");
        }

        String finalToken = jwtUtil.generateToken(user, request.getMerchantId(), request.getMerchantBranchId(), merchantRoles);

        return ResponseEntity.ok(new JWTToken(finalToken));
    }

    /**
     * {@code GET  /me} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/merchant/me")
    @Operation(summary = "Get the current user for merchant users",
            description = "This method return current user with authorities",
            security = @SecurityRequirement(name = "bearerAuth"))
    public UserAccountDTO getAccountMerchant() {
        log.info("REQUEST to get current user data with merchant roles");
        return userService.getUserWithAuthoritiesMerchantRoles().map(UserAccountDTO::new)
                .orElseThrow(() -> new BadRequestCustomException("User could not be found","",""));
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

    static class AuthResponse {

        @JsonProperty(value = "user_id")
        Long userId;
        @JsonProperty(value = "roles")
        Map<Long, List<MerchantRoleEnum>> merchantBranches;
        @JsonProperty(value = "temp_token")
        String tempToken;

        public AuthResponse(Long id, Map<Long, List<MerchantRoleEnum>> merchantBranches, String tempToken) {
            userId = id;
            this.merchantBranches = merchantBranches;
            this.tempToken = tempToken;
        }
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
                StringUtils.isEmpty(password) ||
                        password.length() > Constants.PASSWORD_MAX_LENGTH ||
                        password.length() < Constants.PASSWORD_MIN_LENGTH
        );
    }


}
