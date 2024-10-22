import { Component, OnInit } from '@angular/core';
import { ReportService } from 'src/app/services/report.service'; 
import { Portfolio } from 'src/app/models/portfolio'; 

@Component({
  selector: 'app-client-activity',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ClientActivityComponent implements OnInit {

  clientActivityReports: Portfolio[] = [];

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.getClientActivity('C001');
  }

  getClientActivity(clientId: string): void {
    this.reportService.getReports(clientId).subscribe(
      (reports) => this.clientActivityReports = reports,
      (error) => console.error('Error fetching client activity', error)
    );
  }
}
