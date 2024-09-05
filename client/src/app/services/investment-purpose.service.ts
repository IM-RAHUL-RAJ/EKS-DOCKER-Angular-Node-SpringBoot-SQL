import { Injectable } from '@angular/core';
import { InvestmentPreferences } from '../models/investment-preferences';
import { Observable, of } from 'rxjs';
import { ClientService } from './client.service';
import { Client } from '../models/client';

@Injectable({
  providedIn: 'root'
})
export class InvestmentPurposeService {

  private investmentPreference! : InvestmentPreferences
  private cliendID!: string
  constructor(private clientService: ClientService) {
   this.cliendID = '10001'
   }

  getInvestmentPreference() : Observable<InvestmentPreferences> {
    this.investmentPreference = new InvestmentPreferences(this.cliendID,"College Fund","AVERAGE","60,001 - 80,000","0-5 years",false)
    return of(this.investmentPreference)
  }

  updateInvestmentPreference(investmentPreference : InvestmentPreferences) {
    this.investmentPreference = investmentPreference
  }
}
