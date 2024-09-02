import { Component, OnInit } from '@angular/core';
import { Instruments } from 'src/app/models/instruments';
import { Prices } from 'src/app/models/prices';
import { InstrumentDataService } from 'src/app/services/instrument-data.service';
import { Buy } from 'src/app/models/buy';
import { TradeService } from 'src/app/services/trade.service';
import { BuyFormComponent } from 'src/app/form/buy-form/buy-form.component';
import { MatDialog } from '@angular/material/dialog';
import { SellFormComponent } from 'src/app/form/sell-form/sell-form.component';
import { ClientService } from 'src/app/services/client.service';
@Component({
  selector: 'app-trade',
  templateUrl: './trade.component.html',
  styleUrls: ['./trade.component.css']
})
export class TradeComponent implements OnInit{

  // categories: string[] = ['All STOCK','STOCK','CD','GOVT'];
  // instruments: { [key: string]: string[] } = {
  //   'All Stocks': ['Q123', 'Q456', 'N123456', 'N123789','C100', 'T67890', 'T67894', 'T67895', 'T67897', 'T67899', 'T67880', 'T67883', 'T67878'],
  //   'Stocks': ['Q123', 'Q456', 'N123456', 'N123789'],
  //   'CD': ['C100'],
  //   'GOVT': ['T67890', 'T67894', 'T67895', 'T67897', 'T67899', 'T67880', 'T67883', 'T67878']
  // };
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
    // console.log(this.selectedInst= this.instrumentdataservice.getInstrumentsByCategory(category));
  }

  BuyForm(price:number) {
    this.dialog.open(BuyFormComponent, {
      width: '250px',
      data: { 
        instrumentName: 'USA, Note 3.125 15nov2028 10Y',
        price: price
      } 
    });
  }

  SellForm() {
    this.dialog.open(SellFormComponent, {
      width: '250px',
      data: { 
        instrumentName: 'USA, Note 3.125 15nov2028 10Y',
        price: '1162.42'
      } 
    });
  }
  // selectedInstrument(instrument:string){
  //   this.selectedInst = ;
  // }


}
