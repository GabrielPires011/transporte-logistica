import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ClienteModel} from "../model/cliente.model";

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private BASE_URL = 'http://localhost:8080/cliente';

  constructor(private http: HttpClient) { }

  buscarFiltroPaginacao(nome: string, id?: number, cnpj?: number, pagina?: number, tamanho?: number): Observable<any> {
    let params = new HttpParams();
    if (nome) {
      params = params.set('nome', nome);
    }
    if (id) {
      params = params.set('id', id.toString());
    }
    if (cnpj) {
      params = params.set('cnpj', cnpj.toString());
    }
    if (pagina !== undefined && tamanho !== undefined) {
      params = params.set('page', pagina.toString()).set('size', tamanho.toString());
    }
    return this.http.get<any>(`${this.BASE_URL}/filtro-paginacao`, { params });
  }

  criarCliente(cliente: ClienteModel): Observable<ClienteModel> {
    return this.http.post<ClienteModel>(this.BASE_URL, cliente);
  }

  atualizarCliente(id: number, cliente: ClienteModel): Observable<ClienteModel> {
    return this.http.put<ClienteModel>(`${this.BASE_URL}`, cliente);
  }

  excluirCliente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.BASE_URL}/${id}`);
  }

}
