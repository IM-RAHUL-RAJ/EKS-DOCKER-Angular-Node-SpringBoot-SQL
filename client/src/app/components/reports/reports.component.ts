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
  columns = ['Instrument','']

  constructor(private reportService: ReportService,private pdfService: PDFService) { }

  ngOnInit(): void {
    this.reportService.getReports().subscribe((data: Portfolio[])=>{
      this.reports = data
    })
  }

  generateReport() {
    this.pdfService.generatePdf(this.reports, this.columns);
  }
}
