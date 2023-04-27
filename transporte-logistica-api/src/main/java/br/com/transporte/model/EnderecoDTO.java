package br.com.transporte.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1532326849085859559L;

    private Long id;

    @NotBlank(message = "A rua não pode estar em branco")
    @Size(max = 255, message = "A rua deve ter no máximo 255 caracteres")
    private String rua;

    @NotBlank(message = "O número não pode estar em branco")
    @Size(max = 10, message = "O número deve ter no máximo 10 caracteres")
    private String numero;

    @NotBlank(message = "O bairro não pode estar em branco")
    @Size(max = 255, message = "O bairro deve ter no máximo 255 caracteres")
    private String bairro;

    @NotBlank(message = "A cidade não pode estar em branco")
    @Size(max = 255, message = "A cidade deve ter no máximo 255 caracteres")
    private String cidade;

    @NotBlank(message = "O estado não pode estar em branco")
    @Size(min = 2, max = 2, message = "O estado deve ter 2 caracteres")
    private String estado;

    @NotBlank(message = "O CEP não pode estar em branco")
    @Size(min = 8, max = 8, message = "O CEP deve ter 8 caracteres")
    private String cep;

    @NotNull(message = "A latitude não pode estar em branco")
    private BigDecimal latitude;

    @NotNull(message = "A longitude não pode estar em branco")
    private BigDecimal longitude;
}
