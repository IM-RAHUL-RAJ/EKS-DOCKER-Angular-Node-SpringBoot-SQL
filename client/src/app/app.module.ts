import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { LoginComponent } from './components/login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { InvestmentPreferencesComponent } from './components/investment-preferences/investment-preferences.component';
import { CarouselComponent } from './components/landing/carousel/carousel.component';
import { FooterComponent } from './components/landing/footer/footer.component';
import { HomepageComponent } from './components/landing/homepage/homepage.component';
import { NavigationBarComponent } from './components/landing/navigation-bar/navigation-bar.component';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    InvestmentPreferencesComponent,
    NavigationBarComponent,
    FooterComponent,
    HomepageComponent,
    CarouselComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
