import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { NavigationBarComponent } from './navigation-bar.component';
import { ClientService } from 'src/app/services/client.service';
import { By } from '@angular/platform-browser';
import { MaterialModule } from 'src/app/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('NavigationBarComponent', () => {
  let component: NavigationBarComponent;
  let fixture: ComponentFixture<NavigationBarComponent>;
  let mockClientService: jasmine.SpyObj<ClientService>;
  let mockRouter: jasmine.SpyObj<Router>;

  beforeEach(() => {
    const clientServiceSpy = jasmine.createSpyObj('ClientService', ['logout']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      declarations: [NavigationBarComponent],
      imports: [MaterialModule, BrowserAnimationsModule],
      providers: [
        { provide: ClientService, useValue: clientServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(NavigationBarComponent);
    component = fixture.componentInstance;
    mockClientService = TestBed.inject(ClientService) as jasmine.SpyObj<ClientService>;
    mockRouter = TestBed.inject(Router) as jasmine.SpyObj<Router>;

    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  describe('toggleMenu', () => {
    it('should toggle isMenuOpen from false to true', () => {
      component.isMenuOpen = false;
      component.toggleMenu();
      expect(component.isMenuOpen).toBeTrue();
    });

    it('should toggle isMenuOpen from true to false', () => {
      component.isMenuOpen = true;
      component.toggleMenu();
      expect(component.isMenuOpen).toBeFalse();
    });
  });

  describe('logOut', () => {
    it('should call clientService.logout and navigate to login', () => {
      component.logOut();
      expect(mockClientService.logout).toHaveBeenCalled();
      expect(mockRouter.navigate).toHaveBeenCalledWith(['/login']);
    });
  });
});
