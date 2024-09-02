import { formatDate } from '@angular/common';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { Trade } from 'src/app/models/trading-history/trade';
import { TradeService } from 'src/app/services/trading-history/trade.service';
import {MatTableDataSource} from '@angular/material/table';

import {MatPaginator} from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { LiveAnnouncer } from '@angular/cdk/a11y';


@Component({
  selector: 'app-trading-history',
  templateUrl: './trading-history.component.html',
  styleUrls: ['./trading-history.component.css']
})
export class TradingHistoryComponent implements AfterViewInit {

  trades: Trade[] = [];

  displayedColumns: string[] = ['instrumentId', 'order.orderDate', 'tradeDate', 'direction','quantity','executionPrice'];
  dataSource = new MatTableDataSource<Trade>();

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

  searchTrade: string = '';
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

  currentSortKey: keyof Trade | null = null;
  sortAscending: boolean = true;

  constructor(private tradingService: TradeService,private _liveAnnouncer: LiveAnnouncer) {
    
  }
  ngOnInit(): void {
    this.loadTradeData();
    console.log(!this.trades.length);
    this.dataSource = new MatTableDataSource<Trade>(this.trades);
  }

  loadTradeData() {
    this.trades = this.tradingService.getTradeHistory();
  }

  filterData(key: keyof Trade, order: 'asc' | 'desc' = 'asc'): void {
    if (this.currentSortKey === key) {
      // Reverse the sort order if the same column is clicked
      this.sortAscending = !this.sortAscending;
    } else {
      // Sort in ascending order if a new column is clicked
      this.currentSortKey = key;
      this.sortAscending = true;
    }

    this.trades.sort((a, b) => {
      const valueA = a[key];
      const valueB = b[key];

      if (valueA < valueB) return this.sortAscending ? -1 : 1;
      if (valueA > valueB) return this.sortAscending ? 1 : -1;
      return 0;
    });
  }

  check() {
    console.log(formatDate('2002/02/11', 'dd/mm/yyyy', 'en-US'));
  }



}
