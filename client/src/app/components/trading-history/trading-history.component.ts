import { formatDate } from '@angular/common';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { Trade } from 'src/app/models/trading-history/trade';
import { TradeService } from 'src/app/services/trading-history/trade.service';
import {MatTableDataSource} from '@angular/material/table';

import {MatPaginator} from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';


@Component({
  selector: 'app-trading-history',
  templateUrl: './trading-history.component.html',
  styleUrls: ['./trading-history.component.css']
})
export class TradingHistoryComponent implements AfterViewInit {

  trades: Trade[] = [];
  filteredTrades:Trade[] = []

  displayedColumns: string[] = ['instrumentId', 'order.orderDate', 'tradeDate', 'direction','quantity','executionPrice'];
  dataSource = new MatTableDataSource<Trade>();


  searchFormControl = new FormControl('', []);

  @ViewChild(MatSort) sort: MatSort | undefined;

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined ;

  ngAfterViewInit() {
    if (this.paginator) {
      this.dataSource.paginator = this.paginator as MatPaginator;
    }
    if(this.sort){
      this.dataSource.sort = this.sort;
    }
  }
  roboSuggestionsMock: any = [
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
    {
      askPrice: 312500,
      bidPrice: 312000,
      priceTimestamp: '21-AUG-19 05.00.00.040000000 AM -05:00',
      instrument: {
        instrumentId: 'N123789',
        externalIdType: 'ISIN',
        externalId: 'US0846707026',
        categoryId: 'STOCK',
        instrumentDescription: 'Berkshire Hathaway Inc. Class A',
        maxQuantity: 10,
        minQuantity: 1,
      },
    },
    {
      askPrice: 95.92,
      bidPrice: 95.42,
      priceTimestamp: '21-AUG-19 10.00.02.042000000 AM GMT',
      instrument: {
        instrumentId: 'C100',
        externalIdType: 'CUSIP',
        externalId: '48123Y5A0',
        categoryId: 'CD',
        instrumentDescription:
          'JPMorgan Chase Bank, National Association 01/19',
        maxQuantity: 1000,
        minQuantity: 100,
      },
    },
    {
      askPrice: 1.03375,
      bidPrice: 1.03390625,
      priceTimestamp: '21-AUG-19 10.00.02.000000000 AM GMT',
      instrument: {
        instrumentId: 'T67890',
        externalIdType: 'CUSIP',
        externalId: '9128285M8',
        categoryId: 'GOVT',
        instrumentDescription: 'USA, Note 3.125 15nov2028 10Y',
        maxQuantity: 10000,
        minQuantity: 100,
      },
    },
    {
      askPrice: 0.998125,
      bidPrice: 0.99828125,
      priceTimestamp: '21-AUG-19 10.00.02.002000000 AM GMT',
      instrument: {
        instrumentId: 'T67894',
        externalIdType: 'CUSIP',
        externalId: '9128285Z9',
        categoryId: 'GOVT',
        instrumentDescription: 'USA, Note 2.5 31jan2024 5Y',
        maxQuantity: 10000,
        minQuantity: 100,
      },
    },
  ];



  constructor(private tradingService: TradeService,private _liveAnnouncer: LiveAnnouncer) {
    
  }
  ngOnInit(): void {
    this.loadTradeData();
    this.filteredTrades = this.trades
    this.refreshTable()
  }

  refreshTable():void{
    this.dataSource.data = this.filteredTrades;
    
  }

  loadTradeData() {
    this.trades = this.tradingService.getTradeHistory();
  }

  

  filterData(searchText: any): void {
    if (!this.trades) {
      this.filteredTrades =  [];
      this.refreshTable()
      return
    }
    if (!searchText || !searchText.length) {
      this.filteredTrades = this.trades
      this.refreshTable()
      return
    }
    searchText = searchText.toLocaleLowerCase();


    this.filteredTrades = this.trades.filter(it => {
      return (it.direction.toLocaleLowerCase().includes(searchText)||it.instrumentId.toLocaleLowerCase().includes(searchText)||it.order.orderDate.toLocaleLowerCase().includes(searchText)||it.tradeDate.toLocaleLowerCase().includes(searchText));
    });
    
    this.refreshTable()
  }



}
