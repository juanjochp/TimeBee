import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpresaDashboardComponent } from './empresa-dashboard.component';

describe('EmpresaDashboardComponent', () => {
  let component: EmpresaDashboardComponent;
  let fixture: ComponentFixture<EmpresaDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmpresaDashboardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmpresaDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
