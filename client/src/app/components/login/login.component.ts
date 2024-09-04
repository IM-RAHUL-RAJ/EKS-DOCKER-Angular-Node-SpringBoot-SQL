import { ChangeDetectorRef, Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { ClientService } from 'src/app/services/client.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private clientService: ClientService, private formBuilder: FormBuilder, private router: Router, private cdr: ChangeDetectorRef) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  login() {
    if (this.loginForm.invalid) {
      alert('Please fill in all required fields correctly.');
      return;
    }

    const formValues = this.loginForm.value;

    this.clientService.login(formValues.email, formValues.password).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        sessionStorage.setItem('currentUser', JSON.stringify(response));
        this.cdr.detectChanges()
        this.router.navigate(['/home']).then(() => {
          window.location.reload();
        });

      },
      error: (error) => {
        console.error('Login failed', error);
        alert('Login failed: ' + error.error.message);
      }
    });
  }
}
