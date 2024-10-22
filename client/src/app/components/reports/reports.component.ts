import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {
  mockReports = [
    {
      title: 'Annual Financial Report 2023',
      summary: 'A comprehensive overview of the financial performance for the year 2023.',
      downloadLink: 'assets/reports/annual-financial-report-2023.pdf'
    },
    {
      title: 'Quarterly Earnings Q3 2023',
      summary: 'Detailed earnings report for the third quarter of 2023.',
      downloadLink: 'assets/reports/quarterly-earnings-q3-2023.pdf'
    },
    {
      title: 'Market Analysis Report',
      summary: 'In-depth analysis of market trends and forecasts.',
      downloadLink: 'assets/reports/market-analysis-report.pdf'
    }
  ];

  constructor() { }

  ngOnInit(): void {
  }
}
