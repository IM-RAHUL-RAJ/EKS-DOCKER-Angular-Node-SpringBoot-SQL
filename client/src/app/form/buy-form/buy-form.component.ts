import { Component, Inject, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Buy } from 'src/app/models/buy';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NgForm } from '@angular/forms';
import { Instruments } from 'src/app/models/instruments';
import { InstrumentDataService } from 'src/app/services/instrument-data.service';
import { ClientService } from 'src/app/services/client.service';
import { PortfolioService } from 'src/app/services/portfolio.service';
import { Portfolio } from 'src/app/models/portfolio';

@Component({
  selector: 'app-buy-form',
  templateUrl: './buy-form.component.html',
  styleUrls: ['./buy-form.component.css']
})
export class BuyFormComponent {
  buyform: FormGroup
  public updatePortfolio! : Portfolio  
 
  constructor(
    private instrumentdataservice: InstrumentDataService,
    private clientService: ClientService,
    private portfolioService: PortfolioService,
    public dialogRef: MatDialogRef<BuyFormComponent>,
    
    @Inject(MAT_DIALOG_DATA) public data: any,private fb: FormBuilder) {
    this.buyform = this.fb.group({
      quantity: [null, [Validators.required, Validators.min(data.minQuantity),Validators.max(data.maxQuantity)
      ]]
    });
    
   }  

  getQuantity(): number {
    return this.buyform.get('quantity')?.value;
  }

  pricePerStock : number = this.data.price;
  calculateTotal(): number{
    return this.getQuantity() * this.pricePerStock;
  }

  verifyTradeCost(){
    if(this.clientService.getAvailCash() > this.calculateTotal()){
      this.portfolioService.addStock(this.updatePortfolio);
      alert('Trade successful!')
    }
    else{
      alert('Not Enough Cash!')
    }
  }

  onSubmit() {
    if (this.buyform.valid) {
      console.log('Form Submitted:', this.buyform.value);
      this.updatePortfolio = 
    new Portfolio(this.data.instrumentName, 'AAPL', this.getQuantity(), this.data.price, this.calculateTotal(), 155, 3.33, 50, 1.5)
      this.verifyTradeCost();
      this.dialogRef.close();
    }
  }
  ngOnInit() { 
 
    };
  }



