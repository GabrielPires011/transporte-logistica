import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { ClienteBuscaComponent } from './busca/cliente-busca.component';
import { ClienteFormularioComponent } from './formulario/cliente-formulario.component';
import {RouterLink} from "@angular/router";
import {ClienteRoutingModule} from "./cliente.routing.module";
import {MatCardModule} from "@angular/material/card";

@NgModule({
  declarations: [
    ClienteBuscaComponent,
    ClienteFormularioComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatPaginatorModule,
    MatTableModule,
    MatSortModule,
    MatIconModule,
    MatButtonModule,
    RouterLink,
    ClienteRoutingModule,
    MatCardModule,
  ],
  exports: [
    ClienteBuscaComponent,
    ClienteFormularioComponent
  ]
})
export class ClienteModule {}
