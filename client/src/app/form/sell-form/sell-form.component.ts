import { Component, Inject, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Buy } from 'src/app/models/buy';

@Component({
  selector: 'app-sell-form',
  templateUrl: './sell-form.component.html',
  styleUrls: ['./sell-form.component.css']
})
export class SellFormComponent {
  sellform: FormGroup
  @Input() instrumentId: number = -1
  buy : Buy = new Buy('','',-1);
 
  constructor(public dialogRef: MatDialogRef<SellFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,private fb: FormBuilder) {
    this.sellform = this.fb.group({
      quantity: [null, [Validators.required, Validators.min(100), Validators.max(1000)]]
    });
   } 


  onSubmit() {
    if (this.sellform.valid) {
      console.log('Form Submitted:', this.sellform.value);
      this.dialogRef.close();
    }
  }
  
  ngOnInit() { 
    this.buy = new Buy('','', this.instrumentId); 
  }
}
