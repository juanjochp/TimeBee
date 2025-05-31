import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardFichajesTrabajadorComponent } from './dashboard-fichajes-trabajador.component';

describe('DashboardFichajesTrabajadorComponent', () => {
  let component: DashboardFichajesTrabajadorComponent;
  let fixture: ComponentFixture<DashboardFichajesTrabajadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardFichajesTrabajadorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardFichajesTrabajadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
