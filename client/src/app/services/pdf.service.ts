import { Injectable } from '@angular/core';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable'

@Injectable({
  providedIn: 'root'
})
export class PDFService {

  constructor() { }
  generatePdf(data: any[], columns: string[]) {
    const doc = new jsPDF();

    autoTable(doc,{
      head: [columns],
      body: data,
    });

    doc.save('report.pdf');
  }

  formatNumber(value: number): string {
    return value.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
  }

}
