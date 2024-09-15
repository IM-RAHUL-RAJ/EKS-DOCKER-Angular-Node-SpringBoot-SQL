import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';

import { TradeComponent } from './components/trade/trade.component';
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
import { HttpClientModule } from '@angular/common/http';
import { PortfolioComponent } from './components/portfolio/portfolio.component';
import { ReportsComponent } from './components/reports/reports.component';
import { WatchlistComponent } from './components/watchlist/watchlist.component';
import { PDFService } from './services/pdf.service';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material.module';
import { SearchFilterPipe } from './pipes/search-filter.pipe';
import { TradingHistoryComponent } from './components/trading-history/trading-history.component';
import { RoboAdvisorComponent } from './components/robo-advisor/robo-advisor/robo-advisor.component';

@NgModule({
  declarations: [
    AppComponent,
    TradeComponent,
    BuyFormComponent,
    SellFormComponent,
    RegistrationComponent,
    LoginComponent,
    PortfolioComponent,
    WatchlistComponent,
    ReportsComponent,
    TradingHistoryComponent,
    SearchFilterPipe,
    InvestmentPreferencesComponent,
    NavigationBarComponent,
    FooterComponent,
    HomepageComponent,
    RoboAdvisorComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule
  ],
  providers: [PDFService],
  bootstrap: [AppComponent]
})
export class AppModule { }
