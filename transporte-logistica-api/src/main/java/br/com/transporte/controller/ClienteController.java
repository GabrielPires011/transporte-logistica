package br.com.transporte.controller;

import br.com.transporte.exception.NotFoundException;
import br.com.transporte.model.ClienteDTO;
import br.com.transporte.serviceimpl.ClienteServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteServiceImpl clienteService;

    @PostMapping
    @ApiOperation(value = "Cria Cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cliente cadastrato com sucesso"),
            @ApiResponse(code = 400, message = "Erro em cadastar cliente")
    })
    public ResponseEntity<ClienteDTO> criarCliente(@RequestBody @Valid ClienteDTO userDTO) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.criarCliente(userDTO));
    }

    @PutMapping
    @ApiOperation(value = "Atualiza Cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cliente atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro em atualizar cliente")
    })
    public ResponseEntity<ClienteDTO> atualizarCliente(@RequestBody @Valid ClienteDTO userDTO) throws NotFoundException {
        return ResponseEntity.ok(clienteService.atualizarCliente(userDTO));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca Cliente Pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Erro em buscar cliente")
    })
    public ResponseEntity<ClienteDTO> buscarPeloId(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(clienteService.buscarPeloId(id));
    }

    @GetMapping("/filtro-paginacao")
    @ApiOperation(value = "Busca Clientes de Acordo Com os Filtros Especificados")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Parâmetros inválidos")
    })
    public ResponseEntity<Page<ClienteDTO>> buscarFiltroPaginacao(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long cnpj,
            Pageable pageable) {
        return ResponseEntity.ok(clienteService.buscarFiltroPaginacao(nome, id, cnpj, pageable));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Exluir Cliente Pelo ID")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Erro em excluir cliente")
    })
    public ResponseEntity deletarCliente(@PathVariable Long id) throws NotFoundException {
        clienteService.deletarCliente(id);
        return ResponseEntity.ok("");
    }
}
