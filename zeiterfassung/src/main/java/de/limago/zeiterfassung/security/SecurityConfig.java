package de.limago.zeiterfassung.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.limago.zeiterfassung.repositories.MitarbeiterRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	private final MitarbeiterRepository mitarbeiterRepository;

	public SecurityConfig(MitarbeiterRepository mitarbeiterRepository) {
		this.mitarbeiterRepository = mitarbeiterRepository;
	}

	@Bean 
    @Override
    public UserDetailsService userDetailsService() {
        return username -> mitarbeiterRepository.findUserWithRolesByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// .antMatchers( "/monteure/**" ).hasRole( User.USER_ROLENNAME )
				// .antMatchers( "/secure/**" ).permitAll()
				//.antMatchers("/secure/**").hasRole("CUSTOMER").antMatchers("/auth/login").permitAll().anyRequest()
		.anyRequest().authenticated().and().formLogin();// .loginPage( "/auth/login" ).failureUrl( "/auth/login?error=true"
													// );
		// http.csrf().disable();
	}
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


}
