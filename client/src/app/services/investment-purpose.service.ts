import { Injectable } from '@angular/core';
import { InvestmentPreferences } from '../models/investment-preferences';
import { map, Observable, of } from 'rxjs';
import { ClientService } from './client.service';
import { Client } from '../models/client';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class InvestmentPurposeService {

  private investmentPreference! : InvestmentPreferences
  private clientID!: string
  private baseUrl : string = "http://localhost:8080/stock_stream/investment_preference"

  constructor(private clientService: ClientService,private http : HttpClient) {
    const currentUser = clientService.getCurrentUser();
    const clientId = currentUser ? currentUser.clientId : null; // Handle the case where currentUser might be null
    this.clientID = clientId    
   this.investmentPreference = new InvestmentPreferences("C001","HOME_PURCHASE","College Fund","AVERAGE","60,001 - 80,000","0-5 years",false)
  }

  getInvestmentPreference(): Observable<InvestmentPreferences> {
    const url = `${this.baseUrl}/${this.clientID}`;
    console.log("url", url);
    
    return this.http.get(url).pipe(
      map((data: any) => {
        return new InvestmentPreferences(
          data.clientId,
          data.investmentPurpose,
          data.investmentPurposeDescription,
          data.riskTolerance,
          data.incomeCategory,
          data.investmentYear, // this should match 'investmentYears' in the class constructor
          data.isRoboAdvisorTermsAccepted // this should match 'isRoboAdviserTermsAccepted' in the class constructor
        );
      })
    );
  }

  updateInvestmentPreference(investmentPreference: InvestmentPreferences): Observable<any> {
   
    // Prepare the payload
    const payload = {
      clientId: investmentPreference.cliendID,
      investmentPurpose: investmentPreference.investmentPurpose,
      investmentPurposeDescription: investmentPreference.investmentPurposeDescription,
      riskTolerance: investmentPreference.riskTolerance,
      incomeCategory: investmentPreference.incomeCategory,
      investmentYear: investmentPreference.investmentYear,
      isRoboAdvisorTermsAccepted: investmentPreference.isRoboAdviserTermsAccepted
    };

    // Send the PUT request
    const response = this.http.put(this.baseUrl, payload);
    console.log("service",response)
    return response
  }

  addInvestmentPreference(investmentPreference: InvestmentPreferences): Observable<any> {
   
    // Prepare the payload
    const payload = {
      clientId: investmentPreference.cliendID,
      investmentPurpose: investmentPreference.investmentPurpose,
      investmentPurposeDescription: investmentPreference.investmentPurposeDescription,
      riskTolerance: investmentPreference.riskTolerance,
      incomeCategory: investmentPreference.incomeCategory,
      investmentYear: investmentPreference.investmentYear,
      isRoboAdvisorTermsAccepted: investmentPreference.isRoboAdviserTermsAccepted
    };

    // Send the PUT request
    const response = this.http.post(this.baseUrl, payload);
    console.log("service",response)
    return response
  }
}

