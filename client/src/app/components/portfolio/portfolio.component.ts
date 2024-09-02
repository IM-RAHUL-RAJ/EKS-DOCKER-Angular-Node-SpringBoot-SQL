import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Portfolio } from 'src/app/models/portfolio';
import { PortfolioService } from 'src/app/services/portfolio.service';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.css']
})
export class PortfolioComponent {

  totalValue=250000;
  portfolio:Portfolio[]=[];
  
  constructor(private portfolioService:PortfolioService){

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

  

}
