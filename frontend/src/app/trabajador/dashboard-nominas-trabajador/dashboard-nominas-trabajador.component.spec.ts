import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardNominasTrabajadorComponent } from './dashboard-nominas-trabajador.component';

describe('DashboardNominasTrabajadorComponent', () => {
  let component: DashboardNominasTrabajadorComponent;
  let fixture: ComponentFixture<DashboardNominasTrabajadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardNominasTrabajadorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardNominasTrabajadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
