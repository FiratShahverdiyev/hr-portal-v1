package az.hrportal.hrportalapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
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
        User user = (User) authentication.getPrincipal();
        String authorities = "";
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (!authorities.equals(""))
                authorities = authorities.concat(",");
            authorities = authorities.concat(authority.getAuthority());
        }
        Date tokenValidity = new Date(new Date().getTime() + accessTokenValidityInMilliseconds * 60 * 24);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("username", user.getUsername())
                .claim("role", authorities)
//                .claim("userUniqueKeyHash", userUniqueKeyHash)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(tokenValidity)
                .compact();
    }

    public Authentication parseAuthentication(String token) {
        Claims claims = this.validateAndExtractClaim(token);
        UserDetails principal = this.getPrincipal(claims);
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    private UserDetails getPrincipal(Claims claims) {
        String subject = claims.getSubject();
        String username = claims.get("username", String.class);
        String roles = claims.get("role", String.class);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles.split("[,]"))
            authorities.add(new SimpleGrantedAuthority(role));
        return new User(username, "", authorities);
    }

    public Claims validateAndExtractClaim(String authToken) {
        return Jwts.parser().setSigningKey(this.key).parseClaimsJws(authToken).getBody();
    }
}
