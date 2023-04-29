import {Component, OnInit} from '@angular/core';
import {ClienteModel} from "../../../model/cliente.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ClienteService} from "../../../service/cliente.service";

@Component({
  selector: 'app-cliente-formulario',
  templateUrl: './cliente-formulario.component.html',
  styleUrls: ['./cliente-formulario.component.css']
})
export class ClienteFormularioComponent implements OnInit {
  formGroup: FormGroup;

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService
  ) {
    this.formGroup = this.fb.group({
    id: undefined,
    nome: ['', [Validators.required]],
    cnpj: ['', [Validators.required]],
    endereco: this.fb.group({
      id: undefined,
      rua: ['', [Validators.required]],
      numero: ['', [Validators.required]],
      bairro: [''],
      cidade: ['', [Validators.required]],
      estado: ['', [Validators.required]],
      cep: ['', [Validators.required]],
      latitude: ['', [Validators.required]],
      longitude: ['', [Validators.required]],
    })
  });
  }

  ngOnInit(): void {
  }

  salvar(): void {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }
    const cliente: ClienteModel = this.formGroup.value;
    this.clienteService.criarCliente(cliente)
      .subscribe(() => {
        console.log('Cliente criado com sucesso');
        this.formGroup.reset();
      }, error => {
        console.error('Erro ao criar cliente', error);
      });
  }
}
