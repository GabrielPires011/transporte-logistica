import {EnderecoModel} from './endereco.model';

export class ClienteModel {
  id?: any;
  nome?: string;
  cnpj?: number;
  endereco?: EnderecoModel;
}
