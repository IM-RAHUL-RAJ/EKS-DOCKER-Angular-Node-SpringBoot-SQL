import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomepageComponent } from './components/landing/homepage/homepage.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { InvestmentPreferences } from './models/investment-preferences';
import { InvestmentPreferencesComponent } from './components/investment-preferences/investment-preferences.component';

const routes: Routes = [
    {
        path: "",
        component: HomepageComponent

    },
    {
        path: "home",
        component: HomepageComponent
    },
    {
        path: "login",
        component: LoginComponent
    },
    {
        path: 'register',
        component: RegistrationComponent
    },
    {
        path: 'preferences',
        component: InvestmentPreferencesComponent
    },
    {
        path: "**",
        redirectTo: '/'
    }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
