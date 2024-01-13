package com.aaredo.app.usuarios.models.repository;

import com.aaredo.app.usuarios.models.entity.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "usuarios")
public interface IUsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}
