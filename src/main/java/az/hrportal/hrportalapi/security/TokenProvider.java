package az.hrportal.hrportalapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

@Service
public class TokenProvider {
    @Value("${security.jwt.base64-secret}")
    private String base64Secret;
    @Value("${security.jwt.access-token-validity-in-milliseconds}")
    private Long accessTokenValidityInMilliseconds;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(Authentication authentication) {
//        String userUniqueKey = getUserUniqueKey();
//        Cookie cookie = SecurityUtils.getCustomAuthCookie(userUniqueKey);
//        httpServletResponse.addCookie(cookie);
//        String userUniqueKeyHash = SecurityUtils.getUserUniqueKeyHash(userUniqueKey);
        String username = (String) authentication.getPrincipal();
        Date tokenValidity = new Date(new Date().getTime() + accessTokenValidityInMilliseconds * 60);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("username", username)
//                .claim("userUniqueKeyHash", userUniqueKeyHash)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(tokenValidity)
                .compact();
    }

    public Authentication parseAuthentication(String token) {
        Claims claims = this.validateAndExtractClaim(token);
        UserDetails principal = this.getPrincipal(claims);
        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }

    private UserDetails getPrincipal(Claims claims) {
        String subject = claims.getSubject();
        String username = claims.get("username", String.class);
        return new User(username, "", new ArrayList<>());
    }

    public Claims validateAndExtractClaim(String authToken) {
        return Jwts.parser().setSigningKey(this.key).parseClaimsJws(authToken).getBody();
    }
}
