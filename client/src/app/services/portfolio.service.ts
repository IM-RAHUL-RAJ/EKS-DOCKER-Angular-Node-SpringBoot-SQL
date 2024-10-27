import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Portfolio } from '../models/portfolio';
import { HttpClient } from '@angular/common/http';
import { ClientService } from './client.service';


@Injectable({
  providedIn: 'root'
})
export class PortfolioService {

  public clientID!: string

  private mockPortfolio: Portfolio[] = [
    new Portfolio('Apple Inc.', 'AAPL', 10, 150, 1500, 155, 3.33, 50, 1.5),
    new Portfolio('Alphabet Inc.', 'GOOGL', 5, 2800, 14000, 2750, -1.79, -250, -2.0),
    new Portfolio('Tesla Inc.', 'TSLA', 8, 700, 5600, 720, 2.86, 1600, 0.7),
    new Portfolio('Microsoft Corp.', 'MSFT', 20, 250, 5000, 260, 4.00, 100, 2.0),
    new Portfolio('Amazon.com Inc.', 'AMZN', 15, 3000, 45000, 3100, 3.33, 75, 1.5),
    new Portfolio('Meta Platforms Inc.', 'META', 12, 200, 2400, 210, 3.50, 60, 1.8)
  ];

  private apiUrl = 'http://localhost:3030/api/clients/holdings/';

  constructor(private clientService: ClientService,private http: HttpClient) {
    const currentUser = clientService.getCurrentUser();
    const clientId = currentUser ? currentUser.clientId : null; // Handle the case where currentUser might be null
    this.clientID = clientId
   }

  getPortfolio(clientId: string): Observable<Portfolio[]> {
    return this.http.get<Portfolio[]>(`${this.apiUrl}${clientId}`);
  }

  getLivePrices(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/live-prices`);
  }


  
  addStock(stock: Portfolio): void {
    this.mockPortfolio.push(stock);
    console.log(this.mockPortfolio);
  }

  removeStock(instrumentName: string, quantity: number): void {
    const portfolioItem = this.mockPortfolio.find(p => p.instrument === instrumentName);
    if (portfolioItem) {
      if (portfolioItem.quantity > quantity) {
        portfolioItem.quantity -= quantity;
      } else {
        this.mockPortfolio = this.mockPortfolio.filter(p => p.instrument !== instrumentName);
      }
    }
  }

}
