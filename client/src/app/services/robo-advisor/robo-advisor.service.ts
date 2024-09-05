import { Injectable } from '@angular/core';
import { InvestmentPreferences } from 'src/app/models/investment-preferences';
import { InvestmentPurposeService } from '../investment-purpose.service';
import { Prices } from 'src/app/models/prices';

@Injectable({
  providedIn: 'root'
})
export class RoboAdvisorService {

  mockInvestmentPreferences:InvestmentPreferences|undefined
  

  mockSuggestions:Prices[] = [
    {
      askPrice: 104.75,
      bidPrice: 104.25,
      priceTimestamp: '21-AUG-19 10.00.01.042000000 AM GMT',
      instrument: {
        instrumentId: 'N123456',
        externalIdType: 'CUSIP',
        externalId: '46625H100',
        categoryId: 'STOCK',
        instrumentDescription: 'JPMorgan Chase & Co. Capital Stock',
        maxQuantity: 1000,
        minQuantity: 1,
      },
    },
    {
      askPrice: 312500,
      bidPrice: 312000,
      priceTimestamp: '21-AUG-19 05.00.00.040000000 AM -05:00',
      instrument: {
        instrumentId: 'N123789',
        externalIdType: 'ISIN',
        externalId: 'US0846707026',
        categoryId: 'STOCK',
        instrumentDescription: 'Berkshire Hathaway Inc. Class A',
        maxQuantity: 10,
        minQuantity: 1,
      },
    },
    {
      askPrice: 95.92,
      bidPrice: 95.42,
      priceTimestamp: '21-AUG-19 10.00.02.042000000 AM GMT',
      instrument: {
        instrumentId: 'C100',
        externalIdType: 'CUSIP',
        externalId: '48123Y5A0',
        categoryId: 'CD',
        instrumentDescription:
          'JPMorgan Chase Bank, National Association 01/19',
        maxQuantity: 1000,
        minQuantity: 100,
      },
    },
    {
      askPrice: 1.03375,
      bidPrice: 1.03390625,
      priceTimestamp: '21-AUG-19 10.00.02.000000000 AM GMT',
      instrument: {
        instrumentId: 'T67890',
        externalIdType: 'CUSIP',
        externalId: '9128285M8',
        categoryId: 'GOVT',
        instrumentDescription: 'USA, Note 3.125 15nov2028 10Y',
        maxQuantity: 10000,
        minQuantity: 100,
      },
    },
    {
      askPrice: 0.998125,
      bidPrice: 0.99828125,
      priceTimestamp: '21-AUG-19 10.00.02.002000000 AM GMT',
      instrument: {
        instrumentId: 'T67894',
        externalIdType: 'CUSIP',
        externalId: '9128285Z9',
        categoryId: 'GOVT',
        instrumentDescription: 'USA, Note 2.5 31jan2024 5Y',
        maxQuantity: 10000,
        minQuantity: 100,
      },
    },
  ]

  constructor(investmentPreferenceService:InvestmentPurposeService) {
    investmentPreferenceService.getInvestmentPreference().subscribe(
      (data)=>{
        this.mockInvestmentPreferences = data
      }
    )
   }

  getSuggestions(){
    return this.mockSuggestions
  }

  getInvestmentPreferences(){
    return this.mockInvestmentPreferences
  }
}
