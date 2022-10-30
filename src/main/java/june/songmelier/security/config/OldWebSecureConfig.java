package june.songmelier.security.config;

//@EnableWebSecurity
//@Configurable
//@RequiredArgsConstructor
//public class WebSecureConfig extends WebSecurityConfigurerAdapter {
//
//    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
//    private final JwtTokenUtils jwtTokenUtils;
//
//    //이걸로 안막으면 인증이 필요없는 부분도 필터를 탐 (forbidden으로 막히지는 않음)
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/login/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().configurationSource(corsConfigurationSource())
//                .and().csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint);
//
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .formLogin().disable() //form으로 로그인 안함
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(), authenticationEntryPoint, jwtTokenUtils))
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/api/*").permitAll() //option method 허락
//                .antMatchers("/api/**").authenticated() //이 패턴 인증필요하다
//                .anyRequest().permitAll();
//    }
//
//
//    //전부 열어놓은 것에 대하여 조사 후 제한 필요
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addExposedHeader("Authorization");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return source;
//    }
//}

