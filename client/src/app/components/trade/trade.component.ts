import { Component, OnInit } from '@angular/core';
import { Instruments } from 'src/app/models/instruments';
import { Prices } from 'src/app/models/prices';
import { InstrumentDataService } from 'src/app/services/instrument-data.service';
import { BuyFormComponent } from 'src/app/form/buy-form/buy-form.component';
import { MatDialog } from '@angular/material/dialog';
import { SellFormComponent } from 'src/app/form/sell-form/sell-form.component';
import { ClientService } from 'src/app/services/client.service';
import { max } from 'rxjs';
@Component({
  selector: 'app-trade',
  templateUrl: './trade.component.html',
  styleUrls: ['./trade.component.css']
})
export class TradeComponent implements OnInit{
  client: any;
  categories: string[] = [];
  instruments: Instruments[] = [];
  selectedCat: string = '';
  selectedInst: any = [] ;

  constructor(private instrumentdataservice: InstrumentDataService,
    private clientService: ClientService,
    public dialog: MatDialog
  ) {
    this.categories = this.instrumentdataservice.getCategories();
  };
  
  
  ngOnInit(): void {
    this.client = this.clientService.getCurrentUser();
  }

  onSelectCategory(category: string): void {
    this.selectedCat = category;
    this.selectedInst= this.instrumentdataservice.getInstrumentsByCategory(category);
  }

  BuyForm(price:number,minQuantity:number,maxQuantity:number) {
    this.dialog.open(BuyFormComponent, {
      width: '250px',
      data: { 
        instrumentName: 'USA, Note 3.125 15nov2028 10Y',
        price: price,
        min: minQuantity,
        max: maxQuantity
      } 
    });
  }

  // SellForm(price:number) {
  //   this.dialog.open(SellFormComponent, {
  //     width: '250px',
  //     data: { 
  //       instrumentName: 'USA, Note 3.125 15nov2028 10Y',
  //       price: price
  //     } 
  //   });
  // }



}
