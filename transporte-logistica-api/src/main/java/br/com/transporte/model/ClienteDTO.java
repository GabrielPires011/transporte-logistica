package br.com.transporte.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5037250243376778224L;

    private Long id;

    @NotBlank(message = "O nome não pode estar em branco.")
    private String nome;

    @NotNull(message = "O CNPJ não pode estar em branco.")
    @Positive(message = "O CNPJ deve ser um número positivo.")
    private Long cnpj;

    @Valid
    @NotNull(message = "O endereço não pode estar em branco.")
    private EnderecoDTO endereco;

    public ClienteDTO(Long id, String nome, Long cnpj, Long idEndereco, String rua, String numero, String bairro,
                      String cidade, String estado, String cep, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = new EnderecoDTO();
        this.endereco.setId(idEndereco);
        this.endereco.setRua(rua);
        this.endereco.setCep(cep);
        this.endereco.setBairro(bairro);
        this.endereco.setLatitude(latitude);
        this.endereco.setLongitude(longitude);
        this.endereco.setNumero(numero);
        this.endereco.setCep(cep);
        this.endereco.setCidade(cidade);
        this.endereco.setEstado(estado);
    }
}
