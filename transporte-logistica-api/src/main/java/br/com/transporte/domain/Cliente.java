package br.com.transporte.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_cliente")
public class Cliente implements Serializable {

    @Serial
    private static final long serialVersionUID = 6721223319380550112L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "nome", nullable = false)
    public String nome;

    @Column(name = "cnpj", nullable = false)
    public Long cnpj;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_endereco", nullable = false)
    public Endereco endereco;
}
