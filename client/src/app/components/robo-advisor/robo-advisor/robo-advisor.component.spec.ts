import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { of } from 'rxjs';
import { RoboAdvisorComponent } from './robo-advisor.component';
import { RoboAdvisorService } from 'src/app/services/robo-advisor/robo-advisor.service';
import { BuyFormComponent } from 'src/app/form/buy-form/buy-form.component';
import { SellFormComponent } from 'src/app/form/sell-form/sell-form.component';
import { Prices } from 'src/app/models/prices';
import { InvestmentPreferences } from 'src/app/models/investment-preferences';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

describe('RoboAdvisorComponent', () => {
  let component: RoboAdvisorComponent;
  let fixture: ComponentFixture<RoboAdvisorComponent>;
  let roboAdvisorService: jasmine.SpyObj<RoboAdvisorService>;
  let dialog: jasmine.SpyObj<MatDialog>;

  // Mock data
  const mockSuggestions: Prices[] = [
    {
      askPrice: 104.75,
      bidPrice: 104.25,
      priceTimestamp: '21-AUG-19 10.00.01.042000000 AM GMT',
      instrument: {
        instrumentId: 'N123456',
        externalIdType: 'CUSIP',
        externalId: '46625H100',
        categoryId: 'STOCK',
        instrumentDescription: 'JPMorgan Chase & Co. Capital Stock',
        maxQuantity: 1000,
        minQuantity: 1,
      },
    },
    // Add more mock Prices objects as needed
  ];

  const mockPreferences: InvestmentPreferences = new InvestmentPreferences('','','','','',false);

  beforeEach(async () => {
    const roboAdvisorServiceSpy = jasmine.createSpyObj('RoboAdvisorService', ['getSuggestions', 'getInvestmentPreferences']);
    const dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    // Provide mock implementations
    roboAdvisorServiceSpy.getSuggestions.and.returnValue(mockSuggestions);
    roboAdvisorServiceSpy.getInvestmentPreferences.and.returnValue(mockPreferences);

    await TestBed.configureTestingModule({
      declarations: [RoboAdvisorComponent],
      schemas:[CUSTOM_ELEMENTS_SCHEMA],
      providers: [
        { provide: RoboAdvisorService, useValue: roboAdvisorServiceSpy },
        { provide: MatDialog, useValue: dialogSpy },

      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoboAdvisorComponent);
    component = fixture.componentInstance;
    roboAdvisorService = TestBed.inject(RoboAdvisorService) as jasmine.SpyObj<RoboAdvisorService>;
    dialog = TestBed.inject(MatDialog) as jasmine.SpyObj<MatDialog>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize mockRoboSuggestions and preferences on ngOnInit', () => {
    expect(component.mockRoboSuggestions).toEqual(mockSuggestions);
    expect(component.preferences).toEqual(mockPreferences);
  });

  it('should open BuyFormComponent dialog with correct data when buyTrade is called', () => {
    const instrument = mockSuggestions[0];
    component.buyTrade(instrument);

    expect(dialog.open).toHaveBeenCalledWith(BuyFormComponent, {
      width: '250px',
      data: { 
        instrumentName: instrument.instrument.instrumentDescription,
        price: instrument.askPrice,
        min: instrument.instrument.minQuantity,
        max: instrument.instrument.maxQuantity
      }
    });
  });

  it('should open SellFormComponent dialog with correct data when sellTrade is called', () => {
    const instrument = mockSuggestions[0];
    component.sellTrade(instrument);

    expect(dialog.open).toHaveBeenCalledWith(SellFormComponent, {
      width: '250px',
      data: { 
        instrumentName: instrument.instrument.instrumentDescription,
        price: instrument.askPrice
      }
    });
  });
});
