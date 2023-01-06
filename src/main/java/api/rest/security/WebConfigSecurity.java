package api.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import api.rest.service.ImplementacaoUserDetailsService;



//mapeaia url, enderecoes, autoriza ou bloqueia acesso a url
@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	//configura as solicitacoes de acesso por http
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//ativando a protecao contra usuario que nao estao validados por token
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		
		//ativando a permicao para acessso a pagina inicial do sistema exemplo index
		.disable().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		
		//URL de Logout, redireciona apos o user deslogar do sistema
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		//mapeia URL de logout e invalida o usuario
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
		//filtra requisicoes de login para autenticacao
		
		
		//filtra demais requisicoes para verificar a presenca do token jwt no header http
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		///service que ira consultar o usuario no banco de dados
		auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());//padrao de codificacao de senha 
	}
}
