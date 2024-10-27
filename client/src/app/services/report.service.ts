import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

export interface Report {
  title: string;
  summary: string;
  clientId: string;
}

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private baseUrl = 'http://localhost:8080/api/clients/reports';

  constructor(private http: HttpClient) { }

  getReports(clientId: string): Observable<Report[]> {
    return this.http.get<Report[]>(`${this.baseUrl}/${clientId}`);
  }
}
