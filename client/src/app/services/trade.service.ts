import { Injectable, OnInit } from '@angular/core';
import { Buy } from '../models/buy';
import { Instruments } from '../models/instruments';
import { Prices } from '../models/prices';
import { Text } from '@angular/compiler';
@Injectable({
  providedIn: 'root'
})
export class TradeService implements OnInit{
  
//   buys: Buy[]=[{
//     category: 'STOCK',
//     instrument:'Q123',
//     askprice: 1162.42
//   },{
//     category: 'STOCK',
//     instrument:'Q123',
//     askprice: 1162.42
//   }
// ]
  constructor() { }
  ngOnInit(): void {
    
  }
}
