package api.rest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.rest.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	@Query("select u from Usuario u where u.login = ?1") //seleciona usuario da tabela Usuario onde o login for igual ao parametro passado
	Usuario finduserbylogin(String login);
	
}
