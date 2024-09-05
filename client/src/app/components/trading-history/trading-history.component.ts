import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { Trade } from 'src/app/models/trading-history/trade';
import { TradeService } from 'src/app/services/trading-history/trade.service';
import {MatTableDataSource} from '@angular/material/table';

import {MatPaginator} from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { FormControl } from '@angular/forms';
import { RoboAdvisorService } from 'src/app/services/robo-advisor/robo-advisor.service';


@Component({
  selector: 'app-trading-history',
  templateUrl: './trading-history.component.html',
  styleUrls: ['./trading-history.component.css']
})
export class TradingHistoryComponent implements AfterViewInit {

  trades: Trade[] = [];
  filteredTrades:Trade[] = []

  displayedColumns: string[] = ['instrumentId', 'order.orderDate', 'tradeDate', 'direction','quantity','executionPrice'];
  tradeDataSource = new MatTableDataSource<Trade>();


  searchFormControl = new FormControl('', []);

  @ViewChild(MatSort) sort: MatSort | undefined;

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined ;

  ngAfterViewInit() {
    if (this.paginator) {
      this.tradeDataSource.paginator = this.paginator as MatPaginator;
    }
    if(this.sort){
      this.tradeDataSource.sort = this.sort;
    }
  }
  roboAdvisorSuggestions: any = [];



  constructor(private tradingService: TradeService) {
  }
  ngOnInit(): void {
    this.loadTradeData();
    this.filteredTrades = this.trades
    this.refreshTable()
  }

  refreshTable():void{
    this.tradeDataSource.data = this.filteredTrades;
    
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
