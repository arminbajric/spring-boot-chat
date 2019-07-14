package com.realtime.chat.service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import static com.realtime.chat.service.common.constants.SIGNING_KEY;

public class JwtTokenUtility {


    public String generateJwt(String email)
    {
        return Jwts.builder().setSubject(email).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY).compact();
    }
    public String extractEmail(String token) throws JwtException {

        Claims claims = Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();

        return  claims.getSubject();

    }
}
