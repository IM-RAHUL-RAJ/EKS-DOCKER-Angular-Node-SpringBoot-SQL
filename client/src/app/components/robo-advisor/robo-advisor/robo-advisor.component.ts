import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { BuyFormComponent } from 'src/app/form/buy-form/buy-form.component';
import { SellFormComponent } from 'src/app/form/sell-form/sell-form.component';
import { Instruments } from 'src/app/models/instruments';
import { InvestmentPreferences } from 'src/app/models/investment-preferences';
import { Prices } from 'src/app/models/prices';
import { RoboAdvisorService } from 'src/app/services/robo-advisor/robo-advisor.service';


@Component({
  selector: 'app-robo-advisor',
  templateUrl: './robo-advisor.component.html',
  styleUrls: ['./robo-advisor.component.css']
})
export class RoboAdvisorComponent implements OnInit {

  mockRoboSuggestions:Prices[] = []
  preferences:InvestmentPreferences |undefined

  constructor(private roboAdvisorService:RoboAdvisorService,
    public dialog: MatDialog){
    
    this.mockRoboSuggestions = this.roboAdvisorService.getSuggestions()
  }
  
  ngOnInit(): void {
    this.mockRoboSuggestions = this.roboAdvisorService.getSuggestions()
    this.preferences = this.roboAdvisorService.getInvestmentPreferences()
  }

  buyTrade(instrument:Prices){
    this.dialog.open(BuyFormComponent, {
      width: '250px',
      data: { 
        instrumentName: instrument.instrument.instrumentDescription,
        price: instrument.askPrice,
        min: instrument.instrument.minQuantity,
        max: instrument.instrument.maxQuantity
      } 
    });
  }

  sellTrade(instrument:Prices){
    this.dialog.open(SellFormComponent, {
      width: '250px',
      data: { 
        instrumentName: instrument.instrument.instrumentDescription,
        price: instrument.askPrice
      } 
    });
  }
  

}
