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
