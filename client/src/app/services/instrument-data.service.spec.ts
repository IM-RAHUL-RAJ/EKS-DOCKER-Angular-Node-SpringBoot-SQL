import { InstrumentDataService } from './instrument-data.service';
import { Prices } from '../models/prices';
import { Instruments } from '../models/instruments';
import { TestBed } from '@angular/core/testing';

describe('InstrumentDataService', () => {
  let service: InstrumentDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InstrumentDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return the correct categories', () => {
    const categories = service.getCategories();
    expect(categories).toEqual(['STOCK', 'CD', 'GOVT']);
  });

  it('should return instruments for a given category', () => {
    const govtInstruments = service.getInstrumentsByCategory('GOVT');
    expect(govtInstruments).toEqual([{
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
      }
    }]);

    const stockInstruments = service.getInstrumentsByCategory('STOCK');
    expect(stockInstruments).toEqual([{
      "askPrice": 104.75,
      "bidPrice": 104.25,
      "priceTimestamp": "21-AUG-19 10.00.01.042000000 AM GMT",
      "instrument": {
        "instrumentId": "N123456",
        "externalIdType": "CUSIP",
        "externalId": "46625H100",
        "categoryId": "STOCK",
        "instrumentDescription": "JPMorgan Chase & Co. Capital Stock",
        "maxQuantity": 1000,
        "minQuantity": 1
      }
    }]);

    const unknownCategoryInstruments = service.getInstrumentsByCategory('UNKNOWN');
    expect(unknownCategoryInstruments).toBeUndefined();
  });

  it('should handle getPricePerStock correctly', () => {
    const price = service.getPricePerStock();
    expect(price).toBeUndefined(); 
  });
});
