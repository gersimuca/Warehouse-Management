package com.gersimuca.Warehouse.Management.security.provider;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Slf4j
public class BackendAdminUsernamePasswordAuthenticationProvider extends DaoAuthenticationProvider {
    public static final String INVALID_BACKEND_ADMIN_CREDENTIALS = "Invalid Backend Admin Credentials";

    UserDetailsService systemAccountsDetails;

    public BackendAdminUsernamePasswordAuthenticationProvider(UserDetailsService systemAccountsDetails){
        this.systemAccountsDetails = systemAccountsDetails;
        setUserDetailsService(systemAccountsDetails);
    }

    @PostConstruct
    private void setup(){ setUserDetailsService(systemAccountsDetails); }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<String> username = (Optional) authentication.getPrincipal();
        Optional<String> password = (Optional) authentication.getCredentials();


        if (!username.isPresent() || !password.isPresent()) {
            throw new BadCredentialsException(INVALID_BACKEND_ADMIN_CREDENTIALS);
        }

        // Validate systems Accounts
        try {
            authentication = new UsernamePasswordAuthenticationToken(username.get(), password.get());
        } catch (UsernameNotFoundException e){
            this.logger.debug("System User '" + username + "' not found");
            if(this.hideUserNotFoundExceptions) {
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
            throw e;
        }
        return super.authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(BackendAdminUsernamePasswordAuthenticationToken.class);
    }
}

