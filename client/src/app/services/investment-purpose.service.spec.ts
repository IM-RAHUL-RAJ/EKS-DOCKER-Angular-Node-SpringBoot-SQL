import { TestBed } from '@angular/core/testing';

import { InvestmentPurposeService } from './investment-purpose.service';

fdescribe('InvestmentPurposeService', () => {
  let service: InvestmentPurposeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InvestmentPurposeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
