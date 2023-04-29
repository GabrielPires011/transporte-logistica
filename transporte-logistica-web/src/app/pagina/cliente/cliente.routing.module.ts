import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClienteBuscaComponent } from './busca/cliente-busca.component';
import { ClienteFormularioComponent } from './formulario/cliente-formulario.component';

const routes: Routes = [
  { path: 'cliente', component: ClienteBuscaComponent },
  { path: 'cliente/novo', component: ClienteFormularioComponent },
  { path: 'cliente/editar/:id', component: ClienteFormularioComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClienteRoutingModule { }
