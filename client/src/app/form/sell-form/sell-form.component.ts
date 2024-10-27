import { Component, Inject, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Buy } from 'src/app/models/buy';
import { Portfolio } from 'src/app/models/portfolio';
import { PortfolioService } from 'src/app/services/portfolio.service';

@Component({
  selector: 'app-sell-form',
  templateUrl: './sell-form.component.html',
  styleUrls: ['./sell-form.component.css']
})
export class SellFormComponent {
  sellform: FormGroup
  public updatePortfolio! : Portfolio
 
  constructor(public dialogRef: MatDialogRef<SellFormComponent>,
    private portfolioService: PortfolioService,
    @Inject(MAT_DIALOG_DATA) public data: any,private fb: FormBuilder) {
    this.sellform = this.fb.group({
      quantity: [null, [Validators.required]]
    });
   } 

   getQuantity(): number {
    return this.sellform.get('quantity')?.value;
  }

  pricePerStock : number = this.data.price;
  calculateTotal(): number{
    return this.getQuantity() * this.pricePerStock;
  }

  onSubmit() {
    if (this.sellform.valid) {
      console.log('Form Submitted:', this.sellform.value);
      this.updatePortfolio = 
    new Portfolio(this.data.instrumentName, 'AAPL', this.getQuantity(), this.data.price, this.calculateTotal(), 155, 3.33, 50, 1.5)
      this.portfolioService.removeStock(this.data.instrumentName, this.getQuantity())
      this.dialogRef.close();
    }
  }
 
  ngOnInit() { 
     
  }
}
