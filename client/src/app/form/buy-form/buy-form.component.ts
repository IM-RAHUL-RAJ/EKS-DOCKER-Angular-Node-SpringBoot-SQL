import { Component, Inject, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Buy } from 'src/app/models/buy';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NgForm } from '@angular/forms';
import { Instruments } from 'src/app/models/instruments';
import { InstrumentDataService } from 'src/app/services/instrument-data.service';

@Component({
  selector: 'app-buy-form',
  templateUrl: './buy-form.component.html',
  styleUrls: ['./buy-form.component.css']
})
export class BuyFormComponent {
  buyform: FormGroup
  @Input() instrumentId: number = -1
  buy : Buy = new Buy('','',-1);
 
  constructor(private instrumentdataservice: InstrumentDataService,public dialogRef: MatDialogRef<BuyFormComponent>,
    
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
    // return this.getQuantity()* this.getPricePerStock();
  }
  onSubmit() {
    if (this.buyform.valid) {
      console.log('Form Submitted:', this.buyform.value);
      this.dialogRef.close();
    }
  }
  ngOnInit() { 
    this.buy = new Buy('','', this.instrumentId); 
    };
  }



