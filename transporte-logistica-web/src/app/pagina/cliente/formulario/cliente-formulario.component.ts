import {Component, OnInit} from '@angular/core';
import {ClienteModel} from "../../../model/cliente.model";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ClienteService} from "../../../service/cliente.service";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-cliente-formulario',
  templateUrl: './cliente-formulario.component.html',
  styleUrls: ['./cliente-formulario.component.css']
})
export class ClienteFormularioComponent implements OnInit {
  formGroup: FormGroup;
  titulo = "";
  idCliente: any = null;

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService,
    private toastr: ToastrService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.formGroup = this.fb.group({
      id: undefined,
      nome: ['', [Validators.required]],
      cnpj: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
      endereco: this.fb.group({
        id: undefined,
        rua: ['', [Validators.required, Validators.maxLength(255)]],
        numero: ['', [Validators.required, Validators.maxLength(10)]],
        bairro: ['', [Validators.required, Validators.maxLength(255)]],
        cidade: ['', [Validators.required, Validators.maxLength(255)]],
        estado: ['', [Validators.required, Validators.maxLength(2)]],
        cep: ['', [Validators.required, Validators.maxLength(8)]],
        latitude: ['', [Validators.required, Validators.min(-90), Validators.max(90)]],
        longitude: ['', [Validators.required, Validators.min(-180), Validators.max(180)]],
      })
    });
  }

  ngOnInit(): void {
    this.idCliente = this.activatedRoute.snapshot.paramMap.get('id');
    if (this.idCliente) {
      this.titulo = "Editar";
      this.buscarCliente();
      this.formGroup.valueChanges.subscribe((values) => {
        // atualiza valores de entrada do componente filho
        this.latitude.setValue(values.latitude);
        this.longitude.setValue(values.longitude);
      });
    } else {
      this.titulo = "Novo";
    }
  }

  buscarCliente() {
    this.clienteService.buscarPeloId(this.idCliente)
      .subscribe(
        (cliente: ClienteModel) => {
          this.preencherForm(cliente)
        },
        () => {
          this.toastr.error('Erro em buscar Cliente');
        }
      );
  }

  preencherForm(cliente: ClienteModel) {
    this.formGroup.setValue({
      id: cliente.id,
      nome: cliente.nome,
      cnpj: cliente.cnpj,
      endereco: {
        id: cliente.endereco?.id,
        rua: cliente.endereco?.rua,
        numero: cliente.endereco?.numero,
        bairro: cliente.endereco?.bairro,
        cidade: cliente.endereco?.cidade,
        estado: cliente.endereco?.estado,
        cep: cliente.endereco?.cep,
        latitude: cliente.endereco?.latitude,
        longitude: cliente.endereco?.longitude
      }
    });
    this.formGroup.get('cnpj')?.disable();
  }

  salvar(): void {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }
    const cliente: ClienteModel = this.formGroup.value;
    if (this.idCliente) {
      this.clienteService.atualizarCliente(cliente)
        .subscribe(
          () => {
            this.toastr.success('Cliente salvo com sucesso');
            this.router.navigate(['/cliente']);
          },
          () => {
            this.toastr.error('Erro em salvo Cliente');
          }
        );
    } else {
      this.clienteService.criarCliente(cliente)
        .subscribe(
          () => {
            this.toastr.success('Cliente salvo com sucesso');
            this.router.navigate(['/cliente']);
          },
          () => {
            this.toastr.error('Erro em salvo Cliente');
          }
        );
    }
  }

  get latitude(): FormControl {
    return this.formGroup.get('latitude') as FormControl;
  }

  get longitude(): FormControl {
    return this.formGroup.get('longitude') as FormControl;
  }
}
