import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TradingHistoryComponent } from './trading-history.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { Direction, Trade } from 'src/app/models/trading-history/trade';
import { TradeService } from 'src/app/services/trading-history/trade.service';

fdescribe('TradingHistoryComponent', () => {
  let component: TradingHistoryComponent;
  let fixture: ComponentFixture<TradingHistoryComponent>;
  let mockTradeService:any

  let mockTradeData:Trade[] = [
    {
      instrumentId: 'N123456',
      quantity: 5,
      executionPrice: 104.75,
      direction: Direction.buy,
      clientId: '123',
      tradeDate:"8/12/24 1:45p ET",
      order: {
        instrumentId: 'N123456',
        quantity: 10,
        targetPrice: 104,
        direction: Direction.buy,
        clientId: '123',
        token: 601778450,
        orderDate:"8/14/24 1:45p ET"
      },
      tradeId: 'uy1e3if9t5f-thn4mu36t6n-b7gjedz44lh',
      cashValue: 1057.975,
    } as Trade,
    {
      instrumentId: 'N24Y',
      quantity: 10,
      executionPrice: 104.75,
      direction: Direction.sell,
      clientId: '123',
      tradeDate:"8/16/24 1:45p ET",
      order: {
        instrumentId: 'N123456',
        quantity: 10,
        targetPrice: 104,
        direction: Direction.sell,
        clientId: '123',
        token: 601778450,
        orderDate:"8/16/24 1:45p ET"
      },
      tradeId: 'uy1e3if9t5f-thn4mu36t6n-b7gjedz44lh',
      cashValue: 1057.975,
    } as Trade,
    {
      instrumentId: 'GT125',
      quantity: 10,
      executionPrice: 1043.75,
      direction: Direction.buy,
      clientId: '123',
      tradeDate:"8/16/24 1:45p ET",
      order: {
        instrumentId: 'N123456',
        quantity: 10,
        targetPrice: 104,
        direction: Direction.buy,
        clientId: '123',
        token: 601778450,
        orderDate:"8/16/24 1:45p ET"
      },
      tradeId: 'uy1e3if9t5f-thn4mu36t6n-b7gjedz44lh',
      cashValue: 17.975,
    } as Trade
  ]

  beforeEach(async () => {


    mockTradeService = jasmine.createSpyObj('TradeService',['getTradeHistory'])

    mockTradeService.getTradeHistory.and.returnValue(mockTradeData)

    await TestBed.configureTestingModule({
      declarations: [ TradingHistoryComponent ],
      imports:[ReactiveFormsModule,MatTableModule],
      schemas:[
        CUSTOM_ELEMENTS_SCHEMA
      ],
      providers:[{
        provide:TradeService,
        useValue:mockTradeService
      }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TradingHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get the expected number of trades from service',()=>{
    component.loadTradeData()
    expect(component.trades.length).toBe(mockTradeData.length)
  })

  it('should call the service getTrade method',()=>{
    component.loadTradeData()
    expect(mockTradeService.getTradeHistory).toHaveBeenCalled()
  })
});
