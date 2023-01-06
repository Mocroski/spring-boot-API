package api.rest.security;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;

@Service
@Component
public class JWTTokenAutenticacaoService {

	private static final long EXPIRATION_TIME = 172800000;//tempo de expiracao do token
	
	private static final String SECRET = "senha123"; //senha unicoa para compor a autenticacao
	
	private static final String TOKEN_PREFIX = "Bearer";//prefixo padrao de token 
	
	private static final String HEADER_STRING = "Authorizarion";
	
	//gerando token de autenticacao e adicionando o cabecalho e reposta http
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {
		
		//montagem do token
		String JWT = Jwts.builder()//chaama oa gerador de token
				.setSubject(username)//adiciona o usuario
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))//tempo de expiracao
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();//compactacao e algoritimo de geracao e senha
		
		//junta o token com o prefixo
		String token = TOKEN_PREFIX + "" + JWT; //Bearer 4252331322423sddf1
		
		//adiciona no cabecalho http
		response.addHeader(HEADER_STRING, token);
		
		//escreve token como responsta no corpo http
		response.getWriter().write("{\"Authorization\": \""+ token + "\"}");
		
	}
}
