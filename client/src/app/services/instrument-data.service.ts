import { Injectable } from '@angular/core';
import { Instruments } from '../models/instruments';
import { Prices } from '../models/prices';
import { map, Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InstrumentDataService {

  category: string[] = ['STOCK', 'CD', 'GOVT'];
  instruments: Map<string, Prices[]> = new Map([
    ['GOVT', [{
      "askPrice": 1.03375,
      "bidPrice": 1.03390625,
      "priceTimestamp": "21-AUG-19 10.00.02.000000000 AM GMT",
      "instrument": {
        "instrumentId": "T67890",
        "externalIdType": "CUSIP",
        "externalId": "9128285M8",
        "categoryId": "GOVT",
        "instrumentDescription": "USA, Note 3.125 15nov2028 10Y",
        "maxQuantity": 10000,
        "minQuantity": 100
      }}],
    ],
    ['STOCK',[{
      "askPrice": 104.75,
      "bidPrice": 104.25,
      "priceTimestamp": "21-AUG-19 10.00.01.042000000 AM GMT",
      "instrument":{
        "instrumentId": "N123456",
        "externalIdType": "CUSIP",
        "externalId": "46625H100",
        "categoryId": "STOCK",
        "instrumentDescription": "JPMorgan Chase & Co. Capital Stock",
        "maxQuantity": 1000,
        "minQuantity": 1
      }
    }]
  ]])



getCategories(){
  console.log(this.category)
  return this.category;
}

getInstrumentsByCategory(category: string){
  console.log(this.instruments)
  console.log(this.instruments.get(category))
  return this.instruments.get(category)
}

getPricePerStock(){
  return this.instruments.get('askPrice')
}

constructor() { }
}
