import { Component, OnInit } from '@angular/core';
import { Portfolio } from 'src/app/models/portfolio';
import { PDFService } from 'src/app/services/pdf.service';
import { ReportService } from 'src/app/services/report.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {
  reports: Portfolio[] = [];
  mockReports = [
    { id: 1, title: 'Annual Report 2023', summary: 'Summary of the annual performance for 2023.' },
    { id: 2, title: 'Quarterly Report Q1 2024', summary: 'Summary of the first quarter performance for 2024.' },
    { id: 3, title: 'Monthly Report August 2024', summary: 'Summary of the performance for August 2024.' }
  ];

  columns = ['Instrument', 'Instrument ID', 'Quantity', 'Average Price', 'Invested Capital', 'LTP', 'Percent change', 'Profit and Loss', 'Day change percent']

  constructor(private reportService: ReportService, private pdfService: PDFService) { }

  ngOnInit(): void {
    this.reportService.getReports().subscribe((data: Portfolio[]) => {
      this.reports = data
      console.log(data)
    })
  }

  generateReport() {
    const body = this.reports.map(portfolio => [
      portfolio.instrument,
      portfolio.instrumentId,
      portfolio.quantity,
      portfolio.averagePrice,
      portfolio.investedCapital,
      portfolio.ltp,
      portfolio.percentChange,
      portfolio.profitLoss,
      portfolio.dayChangePercent
    ]);
    this.pdfService.generatePdf(body, this.columns);
  }
}
