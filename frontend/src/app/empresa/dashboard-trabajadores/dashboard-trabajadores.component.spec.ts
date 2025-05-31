import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardTrabajadoresComponent } from './dashboard-trabajadores.component';

describe('DashboardTrabajadoresComponent', () => {
  let component: DashboardTrabajadoresComponent;
  let fixture: ComponentFixture<DashboardTrabajadoresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardTrabajadoresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardTrabajadoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
