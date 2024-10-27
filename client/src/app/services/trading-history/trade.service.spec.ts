import { TestBed } from '@angular/core/testing';

import { TradeService } from './trade.service';
import { Trade } from 'src/app/models/trading-history/trade';

describe('TradeService', () => {
  let service: TradeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TradeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get the mock trade history',()=>{

    let trade_history:Trade[] = service.getTradeHistory()

    expect(trade_history).toBe(service.trades)
  })
});
