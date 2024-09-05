import { Injectable } from '@angular/core';
import { TRADES_MOCK } from 'src/app/mockData/trade.mock';
import { Trade } from 'src/app/models/trading-history/trade';

@Injectable({
  providedIn: 'root'
})
export class TradeService {
  trades: Trade[] = TRADES_MOCK

  constructor() {}

  getTradeHistory():Trade[] {
    return this.trades
  }

  addTradeHistory(trade:Trade){
    this.trades.push(trade)
  }
}
