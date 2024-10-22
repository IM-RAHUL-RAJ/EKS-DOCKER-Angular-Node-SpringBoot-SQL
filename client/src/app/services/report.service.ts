import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Portfolio } from '../models/portfolio';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private baseUrl = 'http://localhost:8080/client/reports';

  constructor(private http: HttpClient) { }

  getReports(clientId: string): Observable<Portfolio[]> {
    return this.http.get<Portfolio[]>(`${this.baseUrl}/${clientId}`);
  }
}

