import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { LoginComponent } from './login.component';
import { ClientService } from 'src/app/services/client.service';
import { By } from '@angular/platform-browser';
import { ChangeDetectorRef } from '@angular/core';
import { MaterialModule } from 'src/app/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let mockClientService: jasmine.SpyObj<ClientService>;
  let mockRouter: jasmine.SpyObj<Router>;
  let mockChangeDetectorRef: jasmine.SpyObj<ChangeDetectorRef>;

  beforeEach(() => {
    const clientServiceSpy = jasmine.createSpyObj('ClientService', ['login']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const changeDetectorRefSpy = jasmine.createSpyObj('ChangeDetectorRef', ['detectChanges']);

    TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, MaterialModule, BrowserAnimationsModule],
      declarations: [LoginComponent],
      providers: [
        FormBuilder,
        { provide: ClientService, useValue: clientServiceSpy },
        { provide: Router, useValue: routerSpy },
        { provide: ChangeDetectorRef, useValue: changeDetectorRefSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    mockClientService = TestBed.inject(ClientService) as jasmine.SpyObj<ClientService>;
    mockRouter = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    mockChangeDetectorRef = TestBed.inject(ChangeDetectorRef) as jasmine.SpyObj<ChangeDetectorRef>;

    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  describe('form validation', () => {
    it('should make the form invalid if required fields are empty', () => {
      component.loginForm.controls['email'].setValue('');
      component.loginForm.controls['password'].setValue('');

      expect(component.loginForm.valid).toBeFalse();
    });

    it('should make the form valid when all fields are correctly filled', () => {
      component.loginForm.controls['email'].setValue('test@example.com');
      component.loginForm.controls['password'].setValue('password123');

      expect(component.loginForm.valid).toBeTrue();
    });
  });

  describe('login', () => {
    it('should show an alert if the form is invalid', () => {
      spyOn(window, 'alert');
      component.login();
      expect(window.alert).toHaveBeenCalledWith('Please fill in all required fields correctly.');
    });

    it('should call clientService.login and handle successful login', () => {
      component.loginForm.controls['email'].setValue('test@example.com');
      component.loginForm.controls['password'].setValue('password123');

      const loginResponse = { userId: '123', token: 'abc' };
      mockClientService.login.and.returnValue(of(loginResponse));

      spyOn(window, 'alert');
      component.login();

      expect(mockClientService.login).toHaveBeenCalledWith('test@example.com', 'password123');
      expect(mockRouter.navigate).toHaveBeenCalledWith(['/home']);
    });

    it('should show an alert if login fails', () => {
      component.loginForm.controls['email'].setValue('test@example.com');
      component.loginForm.controls['password'].setValue('password123');

      mockClientService.login.and.returnValue(throwError(() => new Error('Login failed')));

      spyOn(window, 'alert');
      component.login();
      expect(window.alert).toHaveBeenCalledWith('Login failed: undefined');
    });
  });
});
