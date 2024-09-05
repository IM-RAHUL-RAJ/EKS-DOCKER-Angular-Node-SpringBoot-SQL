import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { InvestmentPreferencesComponent } from './investment-preferences.component';
import { InvestmentPurposeService } from 'src/app/services/investment-purpose.service';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { InvestmentPreferences } from 'src/app/models/investment-preferences';
import { MaterialModule } from 'src/app/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('InvestmentPreferencesComponent', () => {
  let component: InvestmentPreferencesComponent;
  let fixture: ComponentFixture<InvestmentPreferencesComponent>;
  let investmentPreferenceService: jasmine.SpyObj<InvestmentPurposeService>;
  let router: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    const investmentPurposeServiceSpy = jasmine.createSpyObj('InvestmentPurposeService', ['getInvestmentPreference', 'updateInvestmentPreference']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      declarations: [ InvestmentPreferencesComponent ],
      imports: [ ReactiveFormsModule , MaterialModule, BrowserAnimationsModule],
      providers: [
        { provide: InvestmentPurposeService, useValue: investmentPurposeServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InvestmentPreferencesComponent);
    component = fixture.componentInstance;
    investmentPreferenceService = TestBed.inject(InvestmentPurposeService) as jasmine.SpyObj<InvestmentPurposeService>;
    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;

    // Mock the getInvestmentPreference call
    investmentPreferenceService.getInvestmentPreference.and.returnValue(of({
      cliendID: '12345',
      investmentPurpose: 'Retirement',
      riskTolerance: 'AVERAGE',
      incomeCategory: '60,001 - 80,000',
      investmentYears: '5-7 years',
      isRoboAdviserTermsAccepted: true
    }));
  });

  it('should submit the form if it is valid', () => {
    fixture.detectChanges(); // Trigger ngOnInit

    // Set form values
    component.investmentPreferencesForm.setValue({
      investmentPurpose: 'Retirement',
      riskTolerance: 'AVERAGE',
      incomeCategory: '60,001 - 80,000',
      investmentYears: '5-7 years',
      isRoboAdviserTermsAccepted: true
    });

    component.onSubmit(); // Trigger form submission

    expect(investmentPreferenceService.updateInvestmentPreference).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/home']);
  });

  it('should not submit the form if terms are not accepted', () => {
    fixture.detectChanges();

    // Set form values with isRoboAdviserTermsAccepted as false
    component.investmentPreferencesForm.setValue({
      investmentPurpose: 'Retirement',
      riskTolerance: 'AVERAGE',
      incomeCategory: '60,001 - 80,000',
      investmentYears: '5-7 years',
      isRoboAdviserTermsAccepted: false
    });

    component.onSubmit();

    expect(investmentPreferenceService.updateInvestmentPreference).not.toHaveBeenCalled();
    expect(component.showSubmissionAlert).toBeTrue(); // Check if alert popup is shown
  });

  it('should mark the form as submitted after submit', () => {
    fixture.detectChanges();

    // Simulate valid form submission
    component.investmentPreferencesForm.setValue({
      investmentPurpose: 'Retirement',
      riskTolerance: 'AVERAGE',
      incomeCategory: '60,001 - 80,000',
      investmentYears: '5-7 years',
      isRoboAdviserTermsAccepted: true
    });

    component.onSubmit();

    expect(component.isSubmitted).toBeFalse(); // isSubmitted should reset after successful submission
  });

  it('should mark the form as invalid when required fields are missing', () => {
    fixture.detectChanges();

    // Simulate form submission with missing required fields
    component.investmentPreferencesForm.setValue({
      investmentPurpose: '',
      riskTolerance: '',
      incomeCategory: '',
      investmentYears: '',
      isRoboAdviserTermsAccepted: false
    });

    component.onSubmit();

    expect(component.isSubmitted).toBeTrue();
    expect(component.investmentPreferencesForm.invalid).toBeTrue(); // Form should be invalid
    expect(investmentPreferenceService.updateInvestmentPreference).not.toHaveBeenCalled(); // No submission
  });
});
