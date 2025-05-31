import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardPermisosComponent } from './dashboard-permisos.component';

describe('DashboardPermisosComponent', () => {
  let component: DashboardPermisosComponent;
  let fixture: ComponentFixture<DashboardPermisosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardPermisosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardPermisosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
