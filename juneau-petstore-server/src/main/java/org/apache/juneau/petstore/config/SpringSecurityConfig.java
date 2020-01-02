package org.apache.juneau.petstore.config;

import static org.springframework.http.HttpMethod.*;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Sets up BASIC authentication for our app.
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user").password("{noop}password").roles("USER")
			.and()
			.withUser("admin").password("{noop}password").roles("USER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic()
			.and()
			.authorizeRequests()
			.anyRequest().authenticated()
			.antMatchers(GET).anonymous() // Allow anonymous read-only access.
			.antMatchers(POST).anonymous() // TEMPORARY.
			.and()
			.csrf().disable()
			.formLogin().disable();
	}
}