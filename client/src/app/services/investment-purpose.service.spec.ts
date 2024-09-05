import { TestBed } from '@angular/core/testing';
import { InvestmentPurposeService } from './investment-purpose.service';
import { InvestmentPreferences } from '../models/investment-preferences';
import { ClientService } from './client.service';
import { of } from 'rxjs';

describe('InvestmentPurposeService', () => {
  let service: InvestmentPurposeService;
  let mockClientService: jasmine.SpyObj<ClientService>;

  beforeEach(() => {
    const clientServiceSpy = jasmine.createSpyObj('ClientService', ['getClient']);

    TestBed.configureTestingModule({
      providers: [
        InvestmentPurposeService,
        { provide: ClientService, useValue: clientServiceSpy }
      ]
    });

    service = TestBed.inject(InvestmentPurposeService);
    mockClientService = TestBed.inject(ClientService) as jasmine.SpyObj<ClientService>;
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return the initial investment preferences', (done: DoneFn) => {
    const expectedPreference = new InvestmentPreferences(
      '10001',
      'College Fund',
      'AVERAGE',
      '60,001 - 80,000',
      '0-5 years',
      false
    );

    service.getInvestmentPreference().subscribe(preference => {
      expect(preference).toEqual(expectedPreference);
      done();
    });
  });

  it('should update the investment preferences', () => {
    const updatedPreference = new InvestmentPreferences(
      '10001',
      'Retirement Fund',
      'HIGH',
      '100,001 - 150,000',
      '10-15 years',
      true
    );

    service.updateInvestmentPreference(updatedPreference);

    service.getInvestmentPreference().subscribe(preference => {
      expect(preference).toEqual(updatedPreference);
    });
  });
});
