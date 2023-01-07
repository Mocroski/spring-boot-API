package api.rest.security;

import java.util.Date;

import org.springframework.beans.factory.support.ReplaceOverride;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import api.rest.ApplicationContextLoad;
import api.rest.model.Usuario;
import api.rest.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
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
	
	//retorna o usuario validado com token ou caso invalido retorna null
	
	public Authentication getAuthentication(HttpServletRequest request) {
		
		//pega o token eviado no cabcalho http
		String token = request.getHeader(HEADER_STRING);
		
		if(token != null) {
			
			//faz a validacao do token na requisicao
			String user = Jwts.parser().setSigningKey(SECRET)
					.parseClaimsJwt(token.replace(TOKEN_PREFIX, ""))
					.getBody().getSubject(); //retorna usuarui
			
			if(user != null) {
				
				Usuario usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class).finduserbylogin(user);
				
				//retorna  o usuario logado
				if(usuario != null) {
					
					return new UsernamePasswordAuthenticationToken(
							usuario.getLogin(), 
							usuario.getSenha(),
							usuario.getAuthorities());
				}
			}
		}
			
		
			return null; //nao autorizado
		}
	}
}
