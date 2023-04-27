package br.com.transporte.repository;

import br.com.transporte.domain.Cliente;
import br.com.transporte.model.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE c.cnpj = :cnpj")
    Optional<Cliente> buscarPorCNPJ(@Param("cnpj") Long cnpj);

    @Query("select new br.com.transporte.model.ClienteDTO(c.id, c.nome, c.cnpj, c.endereco.id, c.endereco.rua, c.endereco.numero, c.endereco.bairro, " +
            "                      c.endereco.cidade, c.endereco.estado, c.endereco.cep, c.endereco.latitude, c.endereco.longitude) from Cliente c " +
            "where (:nome is null or lower(c.nome) like %:nome%) " +
            "and (:id is null or c.id = :id) " +
            "and (:cnpj is null or c.cnpj = :cnpj)")
    Page<ClienteDTO> buscarFiltroPaginacao(@Param("nome") String nome,
                                           @Param("id") Long id,
                                           @Param("cnpj") Long cnpj,
                                           Pageable pageable);
}
