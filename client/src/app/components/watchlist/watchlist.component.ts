// watchlist.component.ts
import { Component, OnInit } from '@angular/core';

interface WatchlistStock {
  name: string;
  symbol: string;
  price: number;
  change: number;
  logo: string;
}

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.css']
})
export class WatchlistComponent implements OnInit {

  watchlist: WatchlistStock[] = [
    { name: 'Adidas AG', symbol: 'ADS', price: 53.49, change: 0.76, logo: 'assets/images/adidas.png' },
    { name: 'Apple Inc.', symbol: 'AAPL', price: 193.36, change: 0.76, logo: 'assets/images/apple.png' },
    { name: 'Twitter Inc.', symbol: 'TWTR', price: 53.70, change: -0.54, logo: 'assets/images/twitter.png' },
    { name: 'Nike Inc.', symbol: 'NYSE', price: 108.49, change: -11.83, logo: 'assets/images/nike.png' }
  ];

  constructor() { }

  ngOnInit(): void {
  }

  addNewStock(): void {
    const newStock: WatchlistStock = {
      name: 'New Stock',
      symbol: 'NEW',
      price: 100.00,
      change: 0.00,
      logo: 'assets/images/nike.png'
    };
    this.watchlist.push(newStock);
  }

}
