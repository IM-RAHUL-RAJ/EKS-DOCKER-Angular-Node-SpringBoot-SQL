import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';

import { TradeComponent } from './components/trade/trade.component';
import { OrdersComponent } from './components/orders/orders.component';
import { BuyFormComponent } from './form/buy-form/buy-form.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SellFormComponent } from './form/sell-form/sell-form.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { LoginComponent } from './components/login/login.component';
import { InvestmentPreferencesComponent } from './components/investment-preferences/investment-preferences.component';
import { NavigationBarComponent } from './components/landing/navigation-bar/navigation-bar.component';
import { FooterComponent } from './components/landing/footer/footer.component';
import { HomepageComponent } from './components/landing/homepage/homepage.component';
import { CarouselComponent } from './components/landing/carousel/carousel.component';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material.module';

@NgModule({
  declarations: [
    AppComponent,
    TradeComponent,
    OrdersComponent,
    BuyFormComponent,
    SellFormComponent,
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
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
