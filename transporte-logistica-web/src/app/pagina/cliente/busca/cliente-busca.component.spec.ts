import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClienteBuscaComponent } from './cliente-busca.component';

describe('ClienteBuscaComponent', () => {
  let component: ClienteBuscaComponent;
  let fixture: ComponentFixture<ClienteBuscaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClienteBuscaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClienteBuscaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
