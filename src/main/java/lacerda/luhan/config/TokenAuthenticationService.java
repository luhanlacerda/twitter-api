package lacerda.luhan.config;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class TokenAuthenticationService {

	// EXPIRATION_TIME = 10 dias
	static final long EXPIRATION_TIME = 860_000_000;
	static final String SECRET = "MySecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	static void addAuthentication(HttpServletResponse response, String id, String username) throws JsonProcessingException {

		String JWT = Jwts.builder().setSubject(new ObjectMapper().writeValueAsString(new Teste(id, username)))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();

		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}

	static Authentication getAuthentication(HttpServletRequest request) throws ExpiredJwtException, JsonMappingException, UnsupportedJwtException, MalformedJwtException, SignatureException, JsonProcessingException, IllegalArgumentException {
		String token = request.getHeader(HEADER_STRING);

		if (token != null) {
			// faz parse do token
			Teste teste = new ObjectMapper().readValue(Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
					.getSubject(), Teste.class);

			if (teste.getUsername() != null) {
				return new UsernamePasswordAuthenticationToken(teste.getUsername(), null, Collections.emptyList());
			}
		}
		return null;
	}

}
