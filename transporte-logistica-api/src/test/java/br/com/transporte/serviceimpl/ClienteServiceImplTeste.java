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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

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
    void setUp() {
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
    void testSalvarCliente() throws NotFoundException {
        when(clienteRepository.buscarPorCNPJ(clienteDTO.getCnpj())).thenReturn(Optional.empty());
        when(modelMapper.map(clienteDTO, Cliente.class)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(modelMapper.map(cliente, ClienteDTO.class)).thenReturn(clienteDTO);
        ClienteDTO clienteSalvo = clienteService.salvarCliente(clienteDTO);
        assertEquals(clienteDTO.getId(), clienteSalvo.getId());
        assertEquals(clienteDTO.getNome(), clienteSalvo.getNome());
        assertEquals(clienteDTO.getCnpj(), clienteSalvo.getCnpj());
    }

    @Test
    void testSalvarClienteCnpjJaCadastrado() {
        when(clienteRepository.buscarPorCNPJ(clienteDTO.getCnpj())).thenReturn(Optional.of(cliente));
        assertThrows(NotFoundException.class, () -> clienteService.salvarCliente(clienteDTO));
    }

    @Test
    void testAtualizarClienteNaoEncontrado() {
        when(clienteRepository.findById(clienteDTO.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> clienteService.atualizarCliente(clienteDTO));
    }

    @Test
    public void testDeletarCliente() throws NotFoundException {
        Long idCliente = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));

        clienteService.deletarCliente(idCliente);

        verify(clienteRepository).delete(cliente);
    }

    @Test
    public void testBuscarPeloId() throws NotFoundException {
        Long idCliente = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(idCliente);

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(idCliente);

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));
        when(modelMapper.map(cliente, ClienteDTO.class)).thenReturn(clienteDTO);

        ClienteDTO result = clienteService.buscarPeloId(idCliente);

        assertEquals(clienteDTO, result);
    }

    @Test
    public void testBuscarFiltroPaginacao() {
        String nome = "Nome";
        Long id = 1L;
        Long cnpj = 123456789L;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        List<ClienteDTO> clientes = new ArrayList<>();
        clientes.add(clienteDTO);

        when(clienteRepository.buscarFiltroPaginacao(nome, id, cnpj, pageable))
                .thenReturn(new PageImpl<>(clientes));

        Page<ClienteDTO> resultado = clienteService.buscarFiltroPaginacao(nome, id, cnpj, pageable);

        assertEquals(1, resultado.getContent().size());
        assertEquals(clienteDTO, resultado.getContent().get(0));
    }
}