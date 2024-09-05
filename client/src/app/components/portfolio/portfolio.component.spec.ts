import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PortfolioComponent } from './portfolio.component';
import { Portfolio } from 'src/app/models/portfolio';
import { of } from 'rxjs';
import { PortfolioService } from 'src/app/services/portfolio.service';
import { Component } from '@angular/core';
import { WatchlistComponent } from '../watchlist/watchlist.component';

@Component({
  selector:'app-watchlist'
})
class mockWatchlistComponent{}


describe('PortfolioComponent', () => {
  let component: PortfolioComponent;
  let fixture: ComponentFixture<PortfolioComponent>;
  let mockStockData:Portfolio[] = [
    new Portfolio('Apple Inc.', 'AAPL', 10, 150, 1500, 155, 3.33, 50, 1.5),
    new Portfolio('Alphabet Inc.', 'GOOGL', 5, 2800, 14000, 2750, -1.79, -250, -2.0),
    new Portfolio('Tesla Inc.', 'TSLA', 8, 700, 5600, 720, 2.86, 1600, 0.7)
  ];
  
  const mockPortfolioServiceSpy=jasmine.createSpyObj('PortfolioService',['getPortfolio','addStock'])
  mockPortfolioServiceSpy.getPortfolio.and.returnValue(of(mockStockData))
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PortfolioComponent,WatchlistComponent ],
      providers:[{provide:PortfolioService,useValue:mockPortfolioServiceSpy}]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PortfolioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return the portfolio list',()=>{
    component.ngOnInit();
    expect(mockPortfolioServiceSpy.getPortfolio).toHaveBeenCalled();
  })

  it('should sort portfolio by name', () => {
    const event = { target: { value: 'name' } } as unknown as Event;
    component.sortPortfolio(event);
    expect(component.portfolio[0].instrument).toBe('Alphabet Inc.');
  });
  
  it('should sort portfolio by investedCapital', () => {
    const event = { target: { value: 'investedCapital' } } as unknown as Event;
    component.sortPortfolio(event);
    expect(component.portfolio[0].investedCapital).toBe(1500);
  });
  
  it('should sort portfolio by profitLoss', () => {
    const event = { target: { value: 'profitLoss' } } as unknown as Event;
    component.sortPortfolio(event);
    expect(component.portfolio[0].profitLoss).toBe(-250);
  });

  it('should return "profit" for positive profitLoss', () => {
    expect(component.profLossFunc(100)).toBe('profit');
  });

  it('should return "loss" for negative profitLoss', () => {
    expect(component.profLossFunc(-100)).toBe('loss');
  });
});
