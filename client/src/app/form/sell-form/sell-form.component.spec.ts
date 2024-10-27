import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { of } from 'rxjs';
import { SellFormComponent } from './sell-form.component';
import { PortfolioService } from 'src/app/services/portfolio.service';
import { Portfolio } from 'src/app/models/portfolio';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

describe('SellFormComponent', () => {
  let component: SellFormComponent;
  let fixture: ComponentFixture<SellFormComponent>;
  let portfolioService: jasmine.SpyObj<PortfolioService>;
  let dialogRef: jasmine.SpyObj<MatDialogRef<SellFormComponent>>;

  beforeEach(async () => {
    const spyPortfolioService = jasmine.createSpyObj('PortfolioService', ['removeStock']);
    const spyDialogRef = jasmine.createSpyObj('MatDialogRef', ['close']);

    await TestBed.configureTestingModule({
      declarations: [SellFormComponent],
      imports: [ReactiveFormsModule],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
      providers: [
        { provide: PortfolioService, useValue: spyPortfolioService },
        { provide: MAT_DIALOG_DATA, useValue: { instrumentName: 'USA, Note 3.125 15nov2028 10Y', price: 1.03375 } },
        { provide: MatDialogRef, useValue: spyDialogRef }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SellFormComponent);
    component = fixture.componentInstance;
    portfolioService = TestBed.inject(PortfolioService) as jasmine.SpyObj<PortfolioService>;
    dialogRef = TestBed.inject(MatDialogRef) as jasmine.SpyObj<MatDialogRef<SellFormComponent>>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a form with quantity control', () => {
    expect(component.sellform.contains('quantity')).toBeTrue();
  });

  it('should be invalid when quantity is empty', () => {
    component.sellform.controls['quantity'].setValue('');
    expect(component.sellform.valid).toBeFalse();
  });

  it('should be valid when quantity is provided', () => {
    component.sellform.controls['quantity'].setValue(10);
    expect(component.sellform.valid).toBeTrue();
  });

  it('should calculate the total correctly', () => {
    component.sellform.controls['quantity'].setValue(10);
    expect(component.calculateTotal()).toBe(10 * 1.03375);
  });

  it('should call removeStock and close the dialog on valid submit', () => {
    component.sellform.controls['quantity'].setValue(10);
    component.onSubmit();
    
    expect(portfolioService.removeStock).toHaveBeenCalledWith('USA, Note 3.125 15nov2028 10Y', 10);
    expect(dialogRef.close).toHaveBeenCalled();
  });

  it('should not call removeStock or close dialog on invalid submit', () => {
    component.sellform.controls['quantity'].setValue('');
    component.onSubmit();

    expect(portfolioService.removeStock).not.toHaveBeenCalled();
    expect(dialogRef.close).not.toHaveBeenCalled();
  });
});
