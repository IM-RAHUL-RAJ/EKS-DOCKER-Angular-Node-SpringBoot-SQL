import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PortfolioComponent } from './portfolio.component';
import { Portfolio } from 'src/app/models/portfolio';
import { of } from 'rxjs';
import { PortfolioService } from 'src/app/services/portfolio.service';
import { MatDialog } from '@angular/material/dialog';

describe('PortfolioComponent', () => {
  let component: PortfolioComponent;
  let fixture: ComponentFixture<PortfolioComponent>;
  let mockStockData:Portfolio[] = [
    new Portfolio('Apple Inc.', 'AAPL', 10, 150, 1500, 155, 3.33, 50, 1.5),
    new Portfolio('Alphabet Inc.', 'GOOGL', 5, 2800, 14000, 2750, -1.79, -250, -2.0),
    new Portfolio('Tesla Inc.', 'TSLA', 8, 700, 5600, 720, 2.86, 1600, 0.7)
  ];
  
  const mockPortfolioServiceSpy=jasmine.createSpyObj('PortfolioService',['getPortfolio','addStock'])
  mockPortfolioServiceSpy.getPortfolio.and.returnValue(of(mockStockData))
  const mockMatDialog = jasmine.createSpyObj('MatDialog', ['open']);
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PortfolioComponent ],
      providers:[{provide:PortfolioService,useValue:mockPortfolioServiceSpy},
        { provide: MatDialog, useValue: mockMatDialog }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PortfolioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return the portfolio list',()=>{
    component.ngOnInit();
    expect(mockPortfolioServiceSpy.getPortfolio).toHaveBeenCalled();
  })
});
