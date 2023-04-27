package br.com.transporte.service;

import br.com.transporte.exception.NotFoundException;
import br.com.transporte.model.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {


    public ClienteDTO salvarCliente(ClienteDTO clienteDTO) throws NotFoundException;

    public ClienteDTO atualizarCliente(ClienteDTO clienteDTO) throws NotFoundException;

    public void deletarCliente(Long idCliente) throws NotFoundException;

    public ClienteDTO buscarPeloId(Long id) throws NotFoundException;

    public Page<ClienteDTO> buscarFiltroPaginacao(String nome, Long id, Long cnpj, Pageable pageable);
}
