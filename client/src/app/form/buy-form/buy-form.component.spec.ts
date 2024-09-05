import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { of } from 'rxjs';
import { BuyFormComponent } from './buy-form.component';
import { InstrumentDataService } from 'src/app/services/instrument-data.service';
import { ClientService } from 'src/app/services/client.service';
import { PortfolioService } from 'src/app/services/portfolio.service';
import { Portfolio } from 'src/app/models/portfolio';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

// Mock services using createSpyObj
const mockInstrumentDataService = jasmine.createSpyObj('InstrumentDataService', ['getInstruments']);
const mockClientService = jasmine.createSpyObj('ClientService', ['getAvailCash']);
const mockPortfolioService = jasmine.createSpyObj('PortfolioService', ['addStock']);

describe('BuyFormComponent', () => {
  let component: BuyFormComponent;
  let fixture: ComponentFixture<BuyFormComponent>;
  let dialogRef: jasmine.SpyObj<MatDialogRef<BuyFormComponent>>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BuyFormComponent],
      imports: [ReactiveFormsModule, FormsModule],
      schemas:[CUSTOM_ELEMENTS_SCHEMA],
      providers: [
        { provide: InstrumentDataService, useValue: mockInstrumentDataService },
        { provide: ClientService, useValue: mockClientService },
        { provide: PortfolioService, useValue: mockPortfolioService },
        { provide: MAT_DIALOG_DATA, useValue: { minQuantity: 1, maxQuantity: 100, price: 150, instrumentName: 'Apple Inc' } },
        { provide: MatDialogRef, useValue: { close: jasmine.createSpy('close') } }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BuyFormComponent);
    component = fixture.componentInstance;
    dialogRef = TestBed.inject(MatDialogRef) as jasmine.SpyObj<MatDialogRef<BuyFormComponent>>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form with proper validators', () => {
    const quantityControl = component.buyform.get('quantity');
    expect(quantityControl).toBeTruthy();
    expect(quantityControl?.validator).toBeTruthy();
  });

  it('should calculate total price correctly', () => {
    component.buyform.setValue({ quantity: 10 });
    expect(component.calculateTotal()).toBe(1500); // 10 * 150
  });

  it('should alert "Trade successful!" when verifyTradeCost is called with enough cash', () => {
    mockClientService.getAvailCash.and.returnValue(2000); // Set cash to more than required
    spyOn(window, 'alert');
    component.buyform.setValue({ quantity: 10 });
    component.verifyTradeCost();
    expect(window.alert).toHaveBeenCalledWith('Trade successful!');
    expect(mockPortfolioService.addStock).toHaveBeenCalled();
  });

  it('should alert "Not Enough Cash!" when verifyTradeCost is called with insufficient cash', () => {
    mockClientService.getAvailCash.and.returnValue(10); // Set cash to less than required
    spyOn(window, 'alert');
    component.buyform.setValue({ quantity: 10 });
    console.log(component.calculateTotal);
    
    component.verifyTradeCost();
    expect(window.alert).toHaveBeenCalledWith('Not Enough Cash!');
  });

  it('should log form values and call verifyTradeCost on submit', () => {
    spyOn(console, 'log');
    spyOn(component, 'verifyTradeCost');
    component.buyform.setValue({ quantity: 10 });
    component.onSubmit();
    expect(console.log).toHaveBeenCalledWith('Form Submitted:', { quantity: 10 });
    expect(component.verifyTradeCost).toHaveBeenCalled();
    expect(dialogRef.close).toHaveBeenCalled();
  });

  it('should correctly instantiate updatePortfolio on form submission', () => {
    component.buyform.setValue({ quantity: 10 });
    component.onSubmit();
    expect(component.updatePortfolio).toBeTruthy();
    expect(component.updatePortfolio.instrument).toBe('Apple Inc');
    expect(component.updatePortfolio.quantity).toBe(10);
  });
});
