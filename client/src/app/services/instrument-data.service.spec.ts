import { TestBed } from '@angular/core/testing';

import { InstrumentDataService } from './instrument-data.service';

describe('InstrumentDataService', () => {
  let service: InstrumentDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InstrumentDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
