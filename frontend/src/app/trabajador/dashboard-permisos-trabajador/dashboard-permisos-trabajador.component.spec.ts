import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardPermisosTrabajadorComponent } from './dashboard-permisos-trabajador.component';

describe('DashboardPermisosTrabajadorComponent', () => {
  let component: DashboardPermisosTrabajadorComponent;
  let fixture: ComponentFixture<DashboardPermisosTrabajadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardPermisosTrabajadorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardPermisosTrabajadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
