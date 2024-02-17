package com.api.MeteorologicalData.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

/**
 * Service that provides functionalities related to the generation, validation and obtaining of JWT (JSON Web Tokens).
 */
@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${security.jwt.expiration-minutes}")
    private long EXPIRATION_MINUTES;

    /**
     * Generates a JWT for the user.
     *
     * @param user Details of the user for which the token is generated.
     * @return A JWT token containing the user details.
     */
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    /**
     * Generates a JWT with additional claims for the user.
     *
     * @param extraClaims Additional claims to include in the token.
     * @param user        Details of the user for which the token is generated.
     * @return A JWT token containing the additional claims and user details.
     */
    private <K, V> String getToken(HashMap<String, Object> extraClaims, UserDetails user) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    /**
     * Gets the secret key to sign and verify JWT tokens.
     *
     * @return A secret key set from the supplied key in Base64 format.
     */
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Gets the username contained in a JWT token.
     *
     * @param token The JWT token from which the username is extracted.
     * @return The username contained in the token.
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Checks if a JWT token is valid for a specific UserDetails.
     *
     * @param token       The JWT token to verify.
     * @param userDetails The details of the user for which the token is verified.
     * @return true if the token is valid for the user, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Gets a specific claim for a JWT token.
     *
     * @param token          The JWT token from which the claim is extracted.
     * @param claimsResolver The function used to extract the specific claims.
     * @return The specific claims extracted from the token.
     */
    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClamims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Gets all claims of a JWT token.
     *
     * @param token The JWT token from which all claims are extracted.
     * @return A Claims object containing all claims for the token.
     */
    private Claims getAllClamims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Gets the expiration date of a JWT token.
     *
     * @param token The JWT token from which the expiration date is extracted.
     * @return The expiration date of the token.
     */
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Checks if a JWT token has expired.
     *
     * @param token The JWT token to verify.
     * @return true if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
