// import { ComponentFixture, TestBed } from '@angular/core/testing';

// import { TradeComponent } from './trade.component';

// describe('TradeComponent', () => {
//   let component: TradeComponent;
//   let fixture: ComponentFixture<TradeComponent>;

//   beforeEach(async () => {
//     await TestBed.configureTestingModule({
//       declarations: [ TradeComponent ]
//     })
//     .compileComponents();

//     fixture = TestBed.createComponent(TradeComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//   });

//   it('should create', () => {
//     expect(component).toBeTruthy();
//   });
// });

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TradeComponent } from './trade.component';
import { InstrumentDataService } from 'src/app/services/instrument-data.service';
import { ClientService } from 'src/app/services/client.service';
import { MatDialog } from '@angular/material/dialog';
import { of } from 'rxjs';
import { BuyFormComponent } from 'src/app/form/buy-form/buy-form.component';
import { SellFormComponent } from 'src/app/form/sell-form/sell-form.component';
import { Instruments } from 'src/app/models/instruments';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

// Mock services
const mockInstrumentDataService = jasmine.createSpyObj('InstrumentDataService', ['getCategories', 'getInstrumentsByCategory']);
const mockClientService = jasmine.createSpyObj('ClientService', ['getCurrentUser']);
const mockMatDialog = jasmine.createSpyObj('MatDialog', ['open']);

describe('TradeComponent', () => {
  let component: TradeComponent;
  let fixture: ComponentFixture<TradeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TradeComponent],
      schemas:[CUSTOM_ELEMENTS_SCHEMA],
      providers: [
        { provide: InstrumentDataService, useValue: mockInstrumentDataService },
        { provide: ClientService, useValue: mockClientService },
        { provide: MatDialog, useValue: mockMatDialog }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TradeComponent);
    component = fixture.componentInstance;

    // Set up mock data
    mockInstrumentDataService.getCategories.and.returnValue(['Stocks', 'Bonds']);
    mockInstrumentDataService.getInstrumentsByCategory.and.callFake((category: string) => {
      if (category === 'Stocks') {
        return [{ name: 'AAPL', price: 150 }, { name: 'GOOGL', price: 2800 }];
      } else if (category === 'Bonds') {
        return [{ name: 'US 10Y', price: 100 }];
      }
      return [];
    });
    mockClientService.getCurrentUser.and.returnValue({ name: 'John Doe' });

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with categories and client data', () => {
    expect(component.categories).toEqual(['Stocks', 'Bonds']);
    expect(component.client).toEqual({ name: 'John Doe' });
  });

  it('should update selectedInst when a category is selected', () => {
    component.onSelectCategory('Stocks');
    expect(component.selectedInst).toEqual([{ name: 'AAPL', price: 150 }, { name: 'GOOGL', price: 2800 }]);

    component.onSelectCategory('Bonds');
    expect(component.selectedInst).toEqual([{ name: 'US 10Y', price: 100 }]);
  });

  it('should open BuyForm dialog with correct data', () => {
    const price = 150;
    const minQuantity = 1;
    const maxQuantity = 100;
    component.BuyForm(price, minQuantity, maxQuantity);

    expect(mockMatDialog.open).toHaveBeenCalledWith(BuyFormComponent, {
      width: '250px',
      data: { 
        instrumentName: 'USA, Note 3.125 15nov2028 10Y',
        price: price,
        min: minQuantity,
        max: maxQuantity
      }
    });
  });

  // it('should open SellForm dialog with correct data', () => {
  //   const price = 150;
  //   component.SellForm(price);

  //   expect(mockMatDialog.open).toHaveBeenCalledWith(SellFormComponent, {
  //     width: '250px',
  //     data: { 
  //       instrumentName: 'USA, Note 3.125 15nov2028 10Y',
  //       price: price
  //     }
  //   });
  // });
});

