import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardFichajesComponent } from './dashboard-fichajes.component';

describe('DashboardFichajesComponent', () => {
  let component: DashboardFichajesComponent;
  let fixture: ComponentFixture<DashboardFichajesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardFichajesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardFichajesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
