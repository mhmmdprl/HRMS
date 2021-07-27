package com.kodlamaio.hrms.util;

import java.io.Serializable;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.kodlamaio.hrms.entities.Operation;
import com.kodlamaio.hrms.entities.Role;
import com.kodlamaio.hrms.entities.User;
import com.kodlamaio.hrms.enums.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	private Claims getAllClaimsFromToken(String token) {

		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public String getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		return claims.get("id").toString();
	}

	public String getUsernameFromToken(String token) {

		return getClaimFromToken(token, Claims::getSubject);
	}

	public Long getUserIdFromRequest(HttpServletRequest request) {
		String header = request.getHeader(JwtConfig.HEADER_STRING.getValue());
		if (header == null) {

			return 0L;
		}

		String token = header.replace(JwtConfig.TOKEN_PREFIX.getValue() + " ", "");
		Claims claims = getAllClaimsFromToken(token);
		Long userId = Long.parseLong(claims.get(JwtConfig.USER_ID.getValue()).toString());
		return userId;
	}

	public List<String> getAuthoritiesFromToken(String token) {

		List<String> authorities = new ArrayList<String>();
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		authorities = Arrays.asList(claims.get(JwtConfig.AUTHORITIES.getValue()).toString().split(","));
		return authorities;
	}

	public Boolean isTokenExpired(String token) {

		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Date getExpirationDateFromToken(String token) {

		return (Date) getClaimFromToken(token, Claims::getExpiration);
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	public String genareteToken(Authentication authentication, User user) {

		final String roles = user.getRoles().stream().map(Role::getCode).collect(Collectors.joining(","));
		List<Operation> operations = new ArrayList<Operation>();
		user.getRoles().forEach(role -> {
			role.getOperations().forEach(operation -> {
				operations.add(operation);
			});

		});
		final String authorities = operations.stream().map(Operation::getCode).collect(Collectors.joining(","));

		return Jwts.builder().setSubject(authentication.getName()).claim(JwtConfig.AUTHORITIES.getValue(), authorities)
				.claim(JwtConfig.ROLES.getValue(), roles)
				.claim(JwtConfig.USER_NAME.getValue(), user.getEmail())
				.claim(JwtConfig.USER_ID.getValue(), user.getId()).signWith(key)
				.setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()
						+ Integer.parseInt(JwtConfig.ACCESS_TOKEN_VALIDITY_SECONDS.getValue()) * 1000))
				.compact();

	}
	
	public UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth,
			final UserDetails userDetails) {
		final JwtParser jwtParser=Jwts.parser().setSigningKey(key);
		
		final Jws<Claims> jwsClaims=jwtParser.parseClaimsJws(token);
		final Claims claims=jwsClaims.getBody();
		
		final Collection<? extends GrantedAuthority> authorities = claims.get(JwtConfig.AUTHORITIES.getValue())
				.toString().length() > 0
						? Arrays.stream(claims.get(JwtConfig.AUTHORITIES.getValue()).toString().split(","))
								.map(SimpleGrantedAuthority::new).collect(Collectors.toList())
						: null;
		
		return new UsernamePasswordAuthenticationToken(userDetails, "",authorities);
		
	}

}
