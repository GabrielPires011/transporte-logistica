import {Component, OnInit} from '@angular/core';
import {ClienteModel} from '../../../model/cliente.model';
import {ClienteService} from "../../../service/cliente.service";
import {PageEvent} from '@angular/material/paginator';
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-cliente-busca',
  templateUrl: './cliente-busca.component.html',
  styleUrls: ['./cliente-busca.component.css']
})
export class ClienteBuscaComponent implements OnInit {

  nome: string = '';
  id: number | undefined;
  cnpj: number | undefined;
  clientes: ClienteModel[] = [];
  totalClientes: number = 0;
  pageSize: number = 10;
  currentPage: number = 0;
  pageSizeOptions: number[] = [5, 10, 25, 50];

  constructor(private clienteService: ClienteService, private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.buscarClientes();
  }

  buscarClientes(): void {
    this.clienteService.buscarFiltroPaginacao(this.nome, this.id, this.cnpj, this.currentPage, this.pageSize)
      .subscribe((page: any) => {
        this.clientes = page.content;
        this.totalClientes = page.totalElements;
      });
  }

  excluirCliente(id: any): void {
    this.clienteService.excluirCliente(id)
      .subscribe(
        () => {
          this.toastr.success('Cliente excluido com sucesso');
          this.buscarClientes();
        },
        () => {
          this.toastr.error('Erro em excluir Cliente');
        });
  }

  paginar(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.buscarClientes();
  }
}
