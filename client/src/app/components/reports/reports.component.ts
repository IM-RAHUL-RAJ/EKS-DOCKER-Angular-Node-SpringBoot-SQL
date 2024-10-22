import { Component, OnInit } from '@angular/core';
import { ReportService, Report } from 'src/app/services/report.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {
  reports: Report[] = [];
  clientId = 'C001'; // Example client ID

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.reportService.getReports(this.clientId).subscribe((data) => {
      this.reports = data;
    });
  }
}

