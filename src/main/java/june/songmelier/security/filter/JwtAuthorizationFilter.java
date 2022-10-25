package june.songmelier.security.filter;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import june.songmelier.security.PrincipalDetails;
import june.songmelier.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static june.songmelier.utils.JwtTokenUtils.*;

//시큐리티 필터중 BasicAuthenticationFilter가 있다
//권한이나 인증이 필요한 특정 주소를 입력했을 때 위 필터를 무조건 탄다
//만약 권한이나 인증이 필요한 주소가 아니라면 이 필터를 타지 않는다.
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtTokenUtils jwtTokenUtils;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  AuthenticationEntryPoint authenticationEntryPoint,
                                  JwtTokenUtils jwtTokenUtils) {
        super(authenticationManager, authenticationEntryPoint);
        this.jwtTokenUtils = jwtTokenUtils;
    }


    /**
     * 인증이나 권한이 필요할 시 아래의 필터를 탄다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("인증 시도중...");
        try {
            String bearerToken = request.getHeader(TOKEN_HEADER_NAME);

            PrincipalDetails principalDetails = jwtTokenUtils.jwtToPrincipalDetails(bearerToken);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            //강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            request.setAttribute("error", e);
            request.setAttribute("type", "exception");

        } finally { //에러가 발생시 authenticationentrypoint로
            chain.doFilter(request, response);
        }
    }
}
