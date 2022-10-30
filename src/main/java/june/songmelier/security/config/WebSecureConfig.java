package june.songmelier.security.config;

import june.songmelier.security.filter.CustomAuthenticationEntryPoint;
import june.songmelier.security.filter.JwtAuthorizationFilter;
import june.songmelier.security.filter.PrincipalDetailsService;
import june.songmelier.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configurable
@RequiredArgsConstructor
public class WebSecureConfig {


    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtTokenUtils jwtTokenUtils;
    private final PrincipalDetailsService principalDetailsService;

//    web으로 안막으면 인증이 필요없는 부분도 필터를 탐 (forbidden으로 막히지는 않음) ---> 해당 Config에 구현 안되어 있음 | 필요하다면 구현 필요
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/login/**");
//    }

    /**
     * Spring security에 관한 빈 등록 -> WebSecurityConfigurerAdapter 가 deprecated 되면서
     * 방식이 override 에서 bean 등록으로 바뀌었다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(principalDetailsService).passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .authorizeRequests()
                .antMatchers("/kakao").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/api/*").permitAll()
                .antMatchers("/api/**").authenticated() //이 패턴 인증필요하다
                .anyRequest().permitAll()
                .and()
                .formLogin().disable()
                .csrf().disable()
                .httpBasic().disable()
//                .headers().disable()
//                .rememberMe().disable()
//                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager, authenticationEntryPoint, jwtTokenUtils))
                .authenticationManager(authenticationManager)
                .cors().configurationSource(corsConfigurationSource());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }


    /**
     * spring security 에 대한 cors config
     * 전부 열어놓은 것에 대하여 조사 후 제한 필요
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
