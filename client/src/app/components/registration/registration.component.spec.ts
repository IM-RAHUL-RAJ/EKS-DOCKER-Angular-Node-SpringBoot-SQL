import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { RegistrationComponent } from './registration.component';
import { ClientService } from 'src/app/services/client.service';
import { By } from '@angular/platform-browser';
import { MaterialModule } from 'src/app/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('RegistrationComponent', () => {
  let component: RegistrationComponent;
  let fixture: ComponentFixture<RegistrationComponent>;
  let mockClientService: jasmine.SpyObj<ClientService>;
  let mockRouter: jasmine.SpyObj<Router>;

  beforeEach(() => {
    const clientServiceSpy = jasmine.createSpyObj('ClientService', ['getClientIdFromFmts', 'register']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, MaterialModule, BrowserAnimationsModule],
      declarations: [RegistrationComponent],
      providers: [
        FormBuilder,
        { provide: ClientService, useValue: clientServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(RegistrationComponent);
    component = fixture.componentInstance;
    mockClientService = TestBed.inject(ClientService) as jasmine.SpyObj<ClientService>;
    mockRouter = TestBed.inject(Router) as jasmine.SpyObj<Router>;

    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  describe('form validation', () => {
    it('should make the form invalid if required fields are empty', () => {
      component.registrationForm.controls['email'].setValue('');
      component.registrationForm.controls['password'].setValue('');
      component.registrationForm.controls['confirmPassword'].setValue('');
      component.registrationForm.controls['fullName'].setValue('');
      component.registrationForm.controls['dateOfBirth'].setValue('');
      component.registrationForm.controls['country'].setValue('');
      component.registrationForm.controls['postalCode'].setValue('');
      component.registrationForm.controls['identificationValue'].setValue('');

      expect(component.registrationForm.valid).toBeFalse();
    });

    it('should make the form valid when all fields are correctly filled', () => {
      component.registrationForm.controls['email'].setValue('test@example.com');
      component.registrationForm.controls['password'].setValue('password123');
      component.registrationForm.controls['confirmPassword'].setValue('password123');
      component.registrationForm.controls['fullName'].setValue('John Doe');
      component.registrationForm.controls['dateOfBirth'].setValue('1990-01-01');
      component.registrationForm.controls['country'].setValue('United States');
      component.registrationForm.controls['postalCode'].setValue('12345');
      component.registrationForm.controls['identificationValue'].setValue('123-45-6789');

      expect(component.registrationForm.valid).toBeTrue();
    });
  });


  describe('register', () => {
    it('should show an alert if the form is invalid', () => {
      spyOn(window, 'alert');
      component.register();
      expect(window.alert).toHaveBeenCalledWith('Please fill in all required fields correctly.');
    });

    it('should call clientService.getClientIdFromFmts and clientService.register on successful validation', () => {
      component.registrationForm.controls['email'].setValue('test@example.com');
      component.registrationForm.controls['password'].setValue('password123');
      component.registrationForm.controls['confirmPassword'].setValue('password123');
      component.registrationForm.controls['fullName'].setValue('John Doe');
      component.registrationForm.controls['dateOfBirth'].setValue('1990-01-01');
      component.registrationForm.controls['country'].setValue('United States');
      component.registrationForm.controls['postalCode'].setValue('12345');
      component.registrationForm.controls['identificationValue'].setValue('123-45-6789');

      mockClientService?.getClientIdFromFmts?.and.returnValue(of({
        clientId: 'Client123',
        token: '12121',
        dateOfBirth: '1990-01-01',
        country: 'India', 
        postalCode: '2321',
        identification: {
          type: 'PAN',
          value: 'fasfsdf'
        }
      }));
      mockClientService.register.and.returnValue(of({}));

      component.register();

      expect(mockClientService.getClientIdFromFmts).toHaveBeenCalled();
      expect(mockClientService.register).toHaveBeenCalled();
      expect(mockRouter.navigate).toHaveBeenCalledWith(['/preferences']);
    });

    it('should show an alert if getClientIdFromFmts fails', () => {
      component.registrationForm.controls['email'].setValue('test@example.com');
      component.registrationForm.controls['password'].setValue('password123');
      component.registrationForm.controls['confirmPassword'].setValue('password123');
      component.registrationForm.controls['fullName'].setValue('John Doe');
      component.registrationForm.controls['dateOfBirth'].setValue('1990-01-01');
      component.registrationForm.controls['country'].setValue('United States');
      component.registrationForm.controls['postalCode'].setValue('12345');
      component.registrationForm.controls['identificationValue'].setValue('123-45-6789');

      mockClientService.getClientIdFromFmts.and.returnValue(throwError(() => new Error('Failed to get clientId from Fmts')));

      spyOn(window, 'alert');
      component.register();
      expect(window.alert).toHaveBeenCalledWith('Failed to get clientId from Fmts');
    });
  });
});
