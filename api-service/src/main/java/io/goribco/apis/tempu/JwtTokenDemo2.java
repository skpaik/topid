package io.goribco.apis.tempu;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

public class JwtTokenDemo2 {

    private static final String secretKey = "jxgEQe.XHuPq8VdbyYFNkAN.dudQ0903YUn4";

    private static final long jwtExpiration = 36000;

    private static final long refreshExpiration = 36000;

    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();
        jws = Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject("user25")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        //jws="";

        System.out.println("tokens : " + jws);

        System.out.println("========AFTER============");
        //   assert .getSubject().equals("Joe");

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws)
                .getBody();

        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
    }

    private static Key getSignInKey() {
        // SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

        // byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}