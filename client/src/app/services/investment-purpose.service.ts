import { Injectable } from '@angular/core';
import { InvestmentPreferences } from '../models/investment-preferences';
import { Observable, of } from 'rxjs';
import { ClientService } from './client.service';

@Injectable({
  providedIn: 'root'
})
export class InvestmentPurposeService {

  private investmentPreference = new InvestmentPreferences("DPQR100","College Fund","AVERAGE","60,001 - 80,000","0-5 years",false)

  constructor(private clientService:ClientService) { }

  getInvestmentPreference() : Observable<InvestmentPreferences> {
    return of(this.investmentPreference)
  }

  updateInvestmentPreference(investmentPreference : InvestmentPreferences) {
    this.investmentPreference = investmentPreference
    this.clientService.getCurrentUser()
  }
}
