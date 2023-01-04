package api.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import api.rest.model.Usuario;
import api.rest.repository.UsuarioRepository;

@Service 
public class ImplementaçãoUserDetailsService  implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//consulta no banco usuario
		
		Usuario usuario = usuarioRepository.finduserbylogin(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Username nao encontrado");
		}
		return new User(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
	}

}
