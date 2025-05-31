import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrabajadorDashboardComponent } from './trabajador-dashboard.component';

describe('TrabajadorDashboardComponent', () => {
  let component: TrabajadorDashboardComponent;
  let fixture: ComponentFixture<TrabajadorDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TrabajadorDashboardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TrabajadorDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
