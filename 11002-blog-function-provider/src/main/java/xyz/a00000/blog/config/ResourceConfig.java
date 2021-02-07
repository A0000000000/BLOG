package xyz.a00000.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import xyz.a00000.blog.component.EntryPointUnauthorized;
import xyz.a00000.blog.component.RestAccessDenied;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String secret;
    @Value("${security.oauth2.authorization.check-token-access}")
    private String checkTokenEndpointUrl;

    @Bean
    public RemoteTokenServices tokenService() {
        RemoteTokenServices tokenService = new RemoteTokenServicesProxy();
        tokenService.setClientId(clientId);
        tokenService.setClientSecret(secret);
        tokenService.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
        return tokenService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private RemoteTokenServices remoteTokenServices;
    @Autowired
    private EntryPointUnauthorized entryPointUnauthorized;
    @Autowired
    private RestAccessDenied restAccessDenied;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .tokenServices(remoteTokenServices)
                .accessDeniedHandler(restAccessDenied)
                .authenticationEntryPoint(entryPointUnauthorized);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/blog/essay/**", "/api/blog/essayType/**", "/api/blog/essayTag/**").hasRole("CREATOR")
                .antMatchers("/api/blog/provider/**", "/api/blog/essayComment/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(entryPointUnauthorized)
                .accessDeniedHandler(restAccessDenied);
    }
}
