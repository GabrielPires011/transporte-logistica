package br.com.transporte.serviceimpl;

import br.com.transporte.domain.Cliente;
import br.com.transporte.domain.Endereco;
import br.com.transporte.exception.NotFoundException;
import br.com.transporte.model.ClienteDTO;
import br.com.transporte.model.EnderecoDTO;
import br.com.transporte.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTeste {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private ClienteDTO clienteDTO;

    private  EnderecoDTO enderecoDTO;

    private Cliente cliente;

    private Endereco endereco;

    @BeforeEach
    void preparar() {
        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("Nome do Cliente");
        clienteDTO.setCnpj(123456789L);

        enderecoDTO = new EnderecoDTO();
        enderecoDTO.setId(1L);
        enderecoDTO.setRua("Rua do Cliente");
        enderecoDTO.setNumero("123");
        enderecoDTO.setBairro("Bairro do Cliente");
        enderecoDTO.setCidade("Cidade do Cliente");
        enderecoDTO.setEstado("Estado do Cliente");
        enderecoDTO.setCep("12345-678");
        clienteDTO.setEndereco(enderecoDTO);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Nome do Cliente");
        cliente.setCnpj(123456789L);

        endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua do Cliente");
        endereco.setNumero("123");
        endereco.setBairro("Bairro do Cliente");
        endereco.setCidade("Cidade do Cliente");
        endereco.setEstado("Estado do Cliente");
        endereco.setCep("12345-678");
        cliente.setEndereco(endereco);
    }

    @Test
    void testeSalvarCliente() throws NotFoundException {
        when(clienteRepository.buscarPorCNPJ(clienteDTO.getCnpj())).thenReturn(Optional.empty());
        when(modelMapper.map(clienteDTO, Cliente.class)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(modelMapper.map(cliente, ClienteDTO.class)).thenReturn(clienteDTO);

        var resultado = clienteService.criarCliente(clienteDTO);

        assertNotNull(resultado);
        assertEquals(clienteDTO.getId(), resultado.getId());
        assertEquals(clienteDTO.getNome(), resultado.getNome());
        assertEquals(clienteDTO.getCnpj(), resultado.getCnpj());

        verify(clienteRepository, times(1)).buscarPorCNPJ(clienteDTO.getCnpj());
        verify(modelMapper, times(1)).map(clienteDTO, Cliente.class);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(modelMapper, times(1)).map(cliente, ClienteDTO.class);
    }

    @Test
    void testeSalvarClienteCnpjJaCadastrado() {
        when(clienteRepository.buscarPorCNPJ(clienteDTO.getCnpj())).thenReturn(Optional.of(cliente));
        assertThrows(NotFoundException.class, () -> clienteService.criarCliente(clienteDTO));
    }

    @Test
    void testeAtualizarClienteNaoEncontrado() {
        when(clienteRepository.findById(clienteDTO.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> clienteService.atualizarCliente(clienteDTO));
    }

    @Test
    void testeAtualizarCliente() {
        var clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("Novo Nome");
        var enderecoDTO = new EnderecoDTO();
        enderecoDTO.setRua("Nova Rua");
        enderecoDTO.setNumero("123");
        clienteDTO.setEndereco(enderecoDTO);

        var endereco = new Endereco();
        endereco.setRua("Rua Antiga");
        endereco.setNumero("123");

        var cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Nome Antigo");
        cliente.setEndereco(endereco);

        when(clienteRepository.findById(clienteDTO.getId())).thenReturn(Optional.ofNullable(cliente));
        when(modelMapper.map(clienteDTO.getEndereco(), Endereco.class)).thenReturn(endereco);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(modelMapper.map(cliente, ClienteDTO.class)).thenReturn(clienteDTO);

        var resultado = clienteService.atualizarCliente(clienteDTO);

        assertNotNull(resultado);
        assertEquals(clienteDTO.getId(), resultado.getId());
        assertEquals(clienteDTO.getNome(), resultado.getNome());
        assertEquals(clienteDTO.getEndereco().getRua(), resultado.getEndereco().getRua());
        assertEquals(clienteDTO.getEndereco().getNumero(), resultado.getEndereco().getNumero());

        verify(clienteRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(enderecoDTO, Endereco.class);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(modelMapper, times(1)).map(cliente, ClienteDTO.class);
    }

    @Test
    public void testeDeletarCliente() throws NotFoundException {
        var idCliente = 1L;
        var cliente = new Cliente();
        cliente.setId(1L);

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));

        clienteService.deletarCliente(idCliente);

        verify(clienteRepository).delete(cliente);
    }

    @Test
    public void testeBuscarPeloId() throws NotFoundException {
        var idCliente = 1L;
        var cliente = new Cliente();
        cliente.setId(idCliente);

        var clienteDTO = new ClienteDTO();
        clienteDTO.setId(idCliente);

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));
        when(modelMapper.map(cliente, ClienteDTO.class)).thenReturn(clienteDTO);

        var resultado = clienteService.buscarPeloId(idCliente);

        assertNotNull(resultado);
        assertEquals(clienteDTO, resultado);

        verify(clienteRepository, times(1)).findById(idCliente);
        verify(modelMapper, times(1)).map(cliente, ClienteDTO.class);
    }

    @Test
    public void testeBuscarFiltroPaginacao() {
        var nome = "Nome";
        var id = 1L;
        var cnpj = 123456789L;
        var pageable = PageRequest.of(0, 10);

        List<ClienteDTO> clientes = new ArrayList<>();
        clientes.add(clienteDTO);

        when(clienteRepository.buscarFiltroPaginacao(nome, id, cnpj, pageable))
                .thenReturn(new PageImpl<>(clientes));

        var resultado = clienteService.buscarFiltroPaginacao(nome, id, cnpj, pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getContent().size());
        assertEquals(clienteDTO, resultado.getContent().get(0));

        verify(clienteRepository, times(1)).buscarFiltroPaginacao(nome, id, cnpj, pageable);
    }
}