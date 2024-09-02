import { Component, OnInit } from '@angular/core';
import { ReportService } from 'src/app/services/report.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {
  reports: any[] = [];

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.reportService.getReports().subscribe(data => {
      this.reports = data;
    });
  }
}
