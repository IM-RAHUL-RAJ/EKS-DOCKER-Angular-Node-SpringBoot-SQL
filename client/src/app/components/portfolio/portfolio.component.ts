import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Portfolio } from 'src/app/models/portfolio';
import { PortfolioService } from 'src/app/services/portfolio.service';
import { SellFormComponent } from 'src/app/form/sell-form/sell-form.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.css']
})
export class PortfolioComponent {

  totalValue=250000;
  portfolio:Portfolio[]=[];
  
  constructor(private portfolioService:PortfolioService,public dialog: MatDialog){

  }
  sortPortfolio(event: Event) {
    this.portfolio.sort((a, b) => {
      const selectElement = event.target as HTMLSelectElement;
      const criteria = selectElement.value;
        if (criteria === 'name') {
            return a.instrument.localeCompare(b.instrument);
        } else if (criteria === 'investedCapital') {
            return a.investedCapital - b.investedCapital;
        } else if (criteria === 'profitLoss') {
            return a.profitLoss - b.profitLoss;
        }
        return 0;
    });
}
  profLossFunc(profitLoss:number){
    if(profitLoss > 0){
      return 'profit';
    }
    return 'loss';
  }

  ngOnInit(){
    this.portfolioService.getPortfolio().subscribe((data)=>this.portfolio=data);
    
  }
  SellForm(price:number) {
    this.dialog.open(SellFormComponent, {
      width: '250px',
      data: { 
      instrumentName: 'USA, Note 3.125 15nov2028 10Y',
      price: price
      } 
    });
  }

  

}