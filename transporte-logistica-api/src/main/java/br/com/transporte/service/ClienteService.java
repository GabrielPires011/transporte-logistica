package br.com.transporte.service;

import br.com.transporte.exception.NotFoundException;
import br.com.transporte.model.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {


    ClienteDTO criarCliente(ClienteDTO clienteDTO) throws NotFoundException;

    ClienteDTO atualizarCliente(ClienteDTO clienteDTO) throws NotFoundException;

    void deletarCliente(Long idCliente) throws NotFoundException;

    ClienteDTO buscarPeloId(Long id) throws NotFoundException;

    Page<ClienteDTO> buscarFiltroPaginacao(String nome, Long id, Long cnpj, Pageable pageable);
}
