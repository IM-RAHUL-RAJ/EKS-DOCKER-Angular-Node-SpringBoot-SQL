import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor() { }
  getReports(): Observable<any[]> {
    const mockReports = [
      { id: 1, title: 'Annual Report 2023', summary: 'Summary of the annual performance for 2023.' },
      { id: 2, title: 'Quarterly Report Q1 2024', summary: 'Summary of the first quarter performance for 2024.' },
      { id: 3, title: 'Monthly Report August 2024', summary: 'Summary of the performance for August 2024.' }
    ];
    return of(mockReports);
  }
}

