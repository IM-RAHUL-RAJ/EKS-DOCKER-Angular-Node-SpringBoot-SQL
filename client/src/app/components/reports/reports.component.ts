import { Component, OnInit } from '@angular/core';
import { Portfolio } from 'src/app/models/portfolio';
import { PDFService } from 'src/app/services/pdf.service';
import { ReportService } from 'src/app/services/report.service';
import { formatNumber, formatDate } from '@angular/common'; // Import for formatting numbers and dates


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

  // generateReport() {
  //   const body = this.reports.map(portfolio => [
  //     portfolio.instrument,
  //     portfolio.instrumentId,
  //     portfolio.quantity,
  //     portfolio.averagePrice,
  //     portfolio.investedCapital,
  //     portfolio.ltp,
  //     portfolio.percentChange,
  //     portfolio.profitLoss,
  //     portfolio.dayChangePercent
  //   ]);
  //   this.pdfService.generatePdf(body, this.columns);
  // }
  generateReport() {
    // Define a header for the report
    const header = ['Report Generated on: ' + formatDate(new Date(), 'dd/MM/yyyy', 'en-US')];
  
    // Create the body of the report
    const body = this.reports.map(portfolio => [
      portfolio.instrument || 'N/A',
      portfolio.instrumentId || 'N/A',
      formatNumber(portfolio.quantity, 'en-US', '1.0-0') || 'N/A',
      formatNumber(portfolio.averagePrice, 'en-US', '1.2-2') || 'N/A',
      formatNumber(portfolio.investedCapital, 'en-US', '1.2-2') || 'N/A',
      formatNumber(portfolio.ltp, 'en-US', '1.2-2') || 'N/A',
      formatNumber(portfolio.percentChange, 'en-US', '1.2-2') + '%' || 'N/A',
      formatNumber(portfolio.profitLoss, 'en-US', '1.2-2') || 'N/A',
      formatNumber(portfolio.dayChangePercent, 'en-US', '1.2-2') + '%' || 'N/A'
    ]);
  
    // Create a title and date for the report
    const reportTitle = 'Portfolio Report';
    const reportDate = formatDate(new Date(), 'dd/MM/yyyy', 'en-US');
   // Generate the PDF with the title, header, body, and columns
   this.pdfService.generatePdf(
    body, // Combine header and body
    this.columns
  );
}
generatePerformanceReport() {
  // Define columns and data for the report
  const columns = ['Date', 'Total Value', 'Change (%)'];
  const body = this.reports.map(report => [
    formatDate('12/03/2024', 'dd/MM/yyyy', 'en-US'),
    formatNumber(5692.03, 'en-US', '1.2-2'),
    formatNumber(23, 'en-US', '1.2-2') + '%'
  ]);

  // Add a title and summary
  const title = 'Portfolio Performance Report';
  const summary = `Report generated on ${formatDate(new Date(), 'dd/MM/yyyy', 'en-US')}`;

  // Generate PDF with title, summary, and body
  this.pdfService.generatePdf([title, summary, ...body.flat()], columns);
}
}
