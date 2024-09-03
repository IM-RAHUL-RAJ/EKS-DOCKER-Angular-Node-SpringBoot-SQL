import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InvestmentPreferencesComponent } from './investment-preferences.component';

describe('InvestmentPreferencesComponent', () => {
  let component: InvestmentPreferencesComponent;
  let fixture: ComponentFixture<InvestmentPreferencesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InvestmentPreferencesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InvestmentPreferencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
