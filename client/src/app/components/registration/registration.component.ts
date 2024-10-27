import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ClientService } from 'src/app/services/client.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  registrationForm: FormGroup;
  identificationLabel: string = 'Identification Value';

  private ssnPattern = /^d{3}-\d{2}-\d{4}/;
  private ppsPattern = /^d{7}[A-W]{1,2}$/;
  private panPattern = /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/;

  constructor(private clientService: ClientService, private formBuilder: FormBuilder, private router: Router) {
    this.registrationForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]],
      fullName: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      country: ['', Validators.required],
      postalCode: ['', Validators.required],
      identificationValue: ['', Validators.required]
    });
  }

  onCountryChange(val: any) {
    let selectedCountry = val.target.value
    let pattern: RegExp;
    switch (selectedCountry) {
      case 'United States':
        this.identificationLabel = 'Social Security Number (SSN)';
        pattern = this.ssnPattern;
        break;
      case 'Ireland':
        this.identificationLabel = 'Personal Public Service Number (PPS)';
        pattern = this.ppsPattern;
        break;
      case 'India':
        this.identificationLabel = 'Permanent Account Number (PAN)';
        pattern = this.panPattern;
        break;
      default:
        pattern = this.ssnPattern;
    }

    this.registrationForm.get('identificationValue')?.setValidators([Validators.required, Validators.pattern(pattern)]);
    this.registrationForm.get('identificationValue')?.updateValueAndValidity();
  }

  register() {
    if (this.registrationForm.invalid) {
      alert('Please fill in all required fields correctly.');
      return;
    }

    const formValues = this.registrationForm.value;

    if (formValues.password !== formValues.confirmPassword) {
      alert('Passwords do not match');
      return;
    }
    
    let identificationTypeDeduced = "SSN";
    if(formValues.country != "United States") 
      identificationTypeDeduced = (formValues.country == 'Ireland') ? "PPS" : "PAN";
    
    const client = {
      email: formValues.email,
      password: formValues.password,
      fullName: formValues.fullName,
      dateOfBirth: formValues.dateOfBirth,
      country: formValues.country,
      postalCode: formValues.postalCode,
      identificationType: identificationTypeDeduced,
      identificationNumber: formValues.identificationValue,
      clientId: "",
      profileStatus: "PENDING"
    };

    // Get clientId from Fmts and proceed with registration
    this.clientService.getClientIdFromFmts(client).subscribe({
      next: (response) => {
        client['clientId'] = response.clientId;

        // Proceed with registration on Fmts
        this.clientService.register(client).subscribe({
          next: (registrationResponse) => {
            console.log('Registration successful', registrationResponse);
            this.router.navigate(['/preferences'])?.then(() => {
              window.location.reload();
            })
          },
          error: (registrationError) => {
            console.error('Registration failed', registrationError);
            alert('Registration failed: ' + registrationError.error.message);
          }
        });
      },
      error: (error) => {
        console.error('Failed to get clientId from Fmts', error);
        alert('Failed to get clientId from Fmts');
      }
    });
  }
}