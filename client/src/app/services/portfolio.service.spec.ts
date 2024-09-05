import { fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing'
import { PortfolioService } from './portfolio.service';
import { Portfolio } from '../models/portfolio';
import { MaterialModule } from '../material.module';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

describe('PortfolioService', () => {
  let service: PortfolioService;

  beforeEach(() => {
    TestBed.configureTestingModule({imports:[MaterialModule, HttpClientTestingModule],
      providers:[{ provide: MAT_DIALOG_DATA, useValue: { instrumentName: 'USA, Note 3.125 15nov2028 10Y', price: 1.03375 } },]
    });
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


  it('should add a new stock to the portfolio', () => {
    const initialLength = service['mockPortfolio'].length;
    const newStock = new Portfolio('Netflix Inc.', 'NFLX', 10, 500, 5000, 520, 4.50, 200, 3.0);
    service.addStock(newStock);

    const updatedPortfolio = service['mockPortfolio'];
    expect(updatedPortfolio.length).toBe(initialLength + 1);
    expect(updatedPortfolio.find(p => p.instrumentId === 'NFLX'))?.toBeTruthy();
  });
});




