package br.com.transporte.serviceimpl;

import br.com.transporte.domain.Cliente;
import br.com.transporte.domain.Endereco;
import br.com.transporte.exception.NotFoundException;
import br.com.transporte.model.ClienteDTO;
import br.com.transporte.repository.ClienteRepository;
import br.com.transporte.repository.EnderecoRepository;
import br.com.transporte.service.ClienteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    private final EnderecoRepository enderecoRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ClienteDTO salvarCliente(ClienteDTO clienteDTO) throws NotFoundException {
        if (!ObjectUtils.isEmpty(clienteRepository.buscarPorCNPJ(clienteDTO.getCnpj()))) throw new NotFoundException("CNPJ já cadastrado na base de dados.");
        return modelMapper.map(clienteRepository.save(modelMapper.map(clienteDTO, Cliente.class)), ClienteDTO.class);
    }

    @Override
    @Transactional
    public ClienteDTO atualizarCliente(ClienteDTO clienteDTO) throws NotFoundException {
        var cliente = clienteRepository.findById(clienteDTO.getId())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        if (!ObjectUtils.isEmpty(clienteDTO.getNome())) cliente.setNome(clienteDTO.getNome());
        if (!ObjectUtils.isEmpty(clienteDTO.getEndereco())) {
            var endereco = modelMapper.map(clienteDTO.getEndereco(), Endereco.class);
            cliente.setEndereco(endereco);
        }

        return modelMapper.map(clienteRepository.save(cliente), ClienteDTO.class);
    }

    @Override
    @Transactional
    public void deletarCliente(Long idCliente) throws NotFoundException{
        var cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
        clienteRepository.delete(cliente);
    }

    @Override
    public ClienteDTO buscarPeloId(Long id) throws NotFoundException {
        var user = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado na base de dados."));
        return modelMapper.map(user, ClienteDTO.class);
    }

    @Override
    public Page<ClienteDTO> buscarFiltroPaginacao(String nome, Long id, Long cnpj, Pageable pageable) {
        return clienteRepository.buscarFiltroPaginacao(nome, id, cnpj, pageable);
    }

}
