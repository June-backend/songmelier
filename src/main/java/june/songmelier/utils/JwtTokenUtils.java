package june.songmelier.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import june.songmelier.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;



@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    public static final int SEC = 1000;
    public static final int MINUTE = 60 * SEC;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;

    public static final String TOKEN_HEADER_NAME = "Authorization";
    public static final String TOKEN_NAME_WITH_SPACE = "Bearer ";
    public static final String CLAIM_USERNAME = "username";
    public static final String CLAIM_ID = "id";

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public PrincipalDetails jwtToPrincipalDetails(String BearerToken) {
        String jwtToken = replaceBearer(BearerToken);
        DecodedJWT decodedJWT = verifyToken(jwtToken);
        return new PrincipalDetails(
                decodedJWT.getClaim(CLAIM_ID).asLong(),
                ((Claim) decodedJWT.getClaim(CLAIM_USERNAME)).asString()
        );
    }

    private String replaceBearer(String tokenString) {
        try {
            return tokenString.replace(TOKEN_NAME_WITH_SPACE, "");
        } catch (Exception e) {
            log.info("Bearer header 존재하지 않음");
            throw new IllegalArgumentException();
        }
    }

    private DecodedJWT verifyToken(String jwtToken) {
        try {
            return JWT
                    .require(Algorithm.HMAC512(JWT_SECRET))
                    .build()
                    .verify(jwtToken);
        } catch (AlgorithmMismatchException algorithmMismatchException){
            log.debug("토큰 알고리즘 미스매칭");
            throw new IllegalArgumentException();
        } catch (SignatureVerificationException signatureVerificationException){
            log.debug("토큰 signature verifying 에러");
            throw new IllegalArgumentException();
        } catch (TokenExpiredException tokenExpiredException) {
            log.debug("Access토큰 만료됨");
            throw new TokenExpiredException("토큰 만료됨");
        } catch (InvalidClaimException invalidClaimException) {
            log.debug("토큰 클레임 에러");
            throw new IllegalArgumentException();
        }
    }


    public String createAccessToken(Long memberId, String username) {
        String token = JWT.create()
                .withSubject("capin")
                .withExpiresAt(new Date(System.currentTimeMillis() + (7 * DAY) ))
                .withClaim(CLAIM_ID, memberId)
                .withClaim(CLAIM_USERNAME, username)
                .sign(Algorithm.HMAC512(JWT_SECRET));   //secretkey
        return TOKEN_NAME_WITH_SPACE + token;
    }

    public String createRefreshToken(Long memberId) {
        String token = JWT.create()
                .withSubject("capin")
                .withExpiresAt(new Date(System.currentTimeMillis() + (14 * DAY) ))
                .withClaim(CLAIM_ID, memberId)
                .sign(Algorithm.HMAC512(JWT_SECRET));   //secretkey
        return TOKEN_NAME_WITH_SPACE + token;
    }

    public long getRefreshTokenExpireTime(String refreshToken) {
        DecodedJWT decodedJWT = verifyToken(replaceBearer(refreshToken));
        long tokenExpireTime = decodedJWT.getExpiresAt().getTime();
        long currentTime = new Date().getTime();

        return (tokenExpireTime - currentTime) / SEC;  //milli sec -> sec
    }

}
