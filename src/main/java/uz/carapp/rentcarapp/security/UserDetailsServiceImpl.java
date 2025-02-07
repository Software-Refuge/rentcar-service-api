package uz.carapp.rentcarapp.security;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.Authority;
import uz.carapp.rentcarapp.domain.User;
import uz.carapp.rentcarapp.repository.AuthorityRepository;
import uz.carapp.rentcarapp.repository.UserRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.service.dto.UserAccountDTO;
import uz.carapp.rentcarapp.service.dto.UserRegDTO;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UserNotActivatedException{
        log.info("Authenticating {}", login);

        return userRepository
                .findOneWithAuthoritiesByLogin(login)
                .map(user -> createSpringSecurityUser(login, user))
                .orElseThrow(() -> new UsernameNotFoundException("User " + login + " was not found in the database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User: '" + lowercaseLogin + "' was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map(Authority::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), grantedAuthorities);
    }

    public Long getUserIdByLogin() {
        return userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }


    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin);
    }


    private UserAccountDTO getUserAccountData() {
        return getUserWithAuthorities().map(UserAccountDTO::new)
                .orElseThrow(() -> new BadRequestCustomException("User could not be found","",""));
    }

    public Boolean changePassword(String currentClearTextPassword, String newPassword, String repeatPassword) {
        if(!newPassword.equals(repeatPassword)) {
            throw new BadRequestCustomException("password and repeat password is not the same", null,null);
        }
        SecurityUtils
                .getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new BadRequestCustomException("Current password is wrong!!", null,null);
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    userRepository.save(user);
                    log.info("Changed password for User: {}", user);
                });
        return true;
    }

    public void createUser(@Valid UserRegDTO userRegDTO) {

        User user = new User();

        user.setLogin(user.getLogin());
        user.setLastName(userRegDTO.getLastName());
        user.setFirstName(userRegDTO.getFirstName());
        user.setPassword(passwordEncoder.encode(userRegDTO.getPassword()));
        user.setLogin(userRegDTO.getEmail());
        user.setEmail(userRegDTO.getEmail());
        user.setActivated(userRegDTO.isStatus());
        user.setGender(userRegDTO.isGender());
        user.setBirthdate(userRegDTO.getBirthDate());
        user.setStatus(userRegDTO.isStatus());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        Set<Authority> authorities;
        authorities = new HashSet<>(Collections.singletonList(authorityRepository.findByName(AuthoritiesConstants.USER)));
        user.setAuthorities(authorities);

        userRepository.save(user);
    }

    public Page<UserRegDTO> findAll(Pageable pageable) {
        log.info("Request to get all users list");
        List<UserRegDTO> list = userRepository.findAll(pageable).map(user -> {
            UserRegDTO userRegDTO = new UserRegDTO();
            userRegDTO.setId(user.getId());
            userRegDTO.setLastName(user.getLastName());
            userRegDTO.setFirstName(user.getFirstName());
            userRegDTO.setBirthDate(user.getBirthdate());
            userRegDTO.setEmail(user.getEmail());
            userRegDTO.setGender(user.isGender());
            userRegDTO.setStatus(user.isStatus());
            userRegDTO.setPhone(user.getPhoneNumber());
            userRegDTO.setRoles(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
            return userRegDTO;
        }).stream().toList();
        return new PageImpl<>(list);
    }

}
