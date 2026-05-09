package com.cards4play.security;

import com.cards4play.models.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {


    private static final String SECRET_KEY = "Cards4PlaySecretKeyAvemariaPapaDiosQueSeguridadTanAsperaPorDiosBendito";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


    public String generarToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", usuario.getRol().name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraerIdUsuario(String token) {
        return extraerTodosLosClaims(token).getSubject();
    }

    public String extraerRol(String token) {
        return extraerTodosLosClaims(token).get("rol", String.class);
    }


    public boolean isTokenValido(String token) {
        return extraerTodosLosClaims(token).getExpiration().after(new Date());
    }

    private Claims extraerTodosLosClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}