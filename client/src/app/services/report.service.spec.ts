import { TestBed } from '@angular/core/testing';
import { ReportService } from './report.service';
import { Portfolio } from '../models/portfolio';
import { of } from 'rxjs';

fdescribe('ReportService', () => {
  let service: ReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return mock reports', (done: DoneFn) => {
    const expectedReports: Portfolio[] = [
      new Portfolio('Apple Inc.', 'AAPL', 10, 150, 1500, 155, 3.33, 50, 1.5),
      new Portfolio('Alphabet Inc.', 'GOOGL', 5, 2800, 14000, 2750, -1.79, -250, -2.0),
      new Portfolio('Tesla Inc.', 'TSLA', 8, 700, 5600, 720, 2.86, 1600, 0.7),
      new Portfolio('Microsoft Corp.', 'MSFT', 20, 250, 5000, 260, 4.00, 100, 2.0),
      new Portfolio('Amazon.com Inc.', 'AMZN', 15, 3000, 45000, 3100, 3.33, 75, 1.5),
      new Portfolio('Meta Platforms Inc.', 'META', 12, 200, 2400, 210, 3.50, 60, 1.8)
    ];

    service.getReports().subscribe((reports) => {
      expect(reports).toEqual(expectedReports);
      done();
    });
  });
});
