import { Component, Input, OnInit } from '@angular/core';
import { ClientService } from 'src/app/services/client.service';
import { InstrumentDataService } from 'src/app/services/instrument-data.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})



export class OrdersComponent implements OnInit{

  client: any;

  constructor(private instrumentdataservice: InstrumentDataService,
    private clientService: ClientService
  ){}
  ngOnInit(): void {
    this.client = this.clientService.getCurrentUser();
    
  }

  


}
