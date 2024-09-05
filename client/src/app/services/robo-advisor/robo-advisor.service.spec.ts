import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { RoboAdvisorService } from './robo-advisor.service';
import { InvestmentPurposeService } from '../investment-purpose.service';
import { InvestmentPreferences } from 'src/app/models/investment-preferences';
import { Prices } from 'src/app/models/prices';

// Define the mock data
const mockInvestmentPreferences: InvestmentPreferences = new InvestmentPreferences('','','','','',false)

const mockSuggestions: Prices[] = [
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
];

describe('RoboAdvisorService', () => {
  let service: RoboAdvisorService;
  let investmentPurposeService: jasmine.SpyObj<InvestmentPurposeService>;

  beforeEach(() => {
    // Create a mock InvestmentPurposeService
    const investmentPurposeServiceSpy = jasmine.createSpyObj('InvestmentPurposeService', ['getInvestmentPreference']);
    
    // Provide a default mock implementation for getInvestmentPreference
    investmentPurposeServiceSpy.getInvestmentPreference.and.returnValue(of(mockInvestmentPreferences));

    TestBed.configureTestingModule({
      providers: [
        RoboAdvisorService,
        { provide: InvestmentPurposeService, useValue: investmentPurposeServiceSpy }
      ]
    });

    service = TestBed.inject(RoboAdvisorService);
    investmentPurposeService = TestBed.inject(InvestmentPurposeService) as jasmine.SpyObj<InvestmentPurposeService>;
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch investment preferences on initialization', () => {
    // Check if mockInvestmentPreferences was set correctly
    expect(service.mockInvestmentPreferences).toEqual(mockInvestmentPreferences);
  });

  it('should return correct suggestions', () => {
    // Check if getSuggestions returns the expected mock data
    service.mockSuggestions = mockSuggestions
    expect(service.getSuggestions()).toEqual(mockSuggestions);
  });
});
