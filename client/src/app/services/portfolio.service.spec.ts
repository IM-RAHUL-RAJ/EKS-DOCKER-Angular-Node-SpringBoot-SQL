import { fakeAsync, inject, TestBed, tick } from '@angular/core/testing';

import { PortfolioService } from './portfolio.service';
import { Portfolio } from '../models/portfolio';

describe('PortfolioService', () => {
  let service: PortfolioService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PortfolioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return Portfolio as a list', inject([PortfolioService], fakeAsync((service: PortfolioService) => {
    let portfolioList: Portfolio[] = [];
    service.getPortfolio()
    .subscribe(data => portfolioList = data);
    tick();
    expect(portfolioList).toBeTruthy();
    })));

});
