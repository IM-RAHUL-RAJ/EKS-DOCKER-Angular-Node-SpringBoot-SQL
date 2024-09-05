import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { AppComponent } from './app.component';
import { ClientService } from './services/client.service';
import { ActivatedRoute } from '@angular/router';
import { By } from '@angular/platform-browser';
import { Component, Input } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from './material.module';

@Component({
  selector: 'app-navigation-bar'
})
class MockNavigationBarComponent {
  @Input() isLoggedIn!: boolean;

 }
@Component({
  selector: 'app-footer'
})
class MockFooterComponent { }

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let mockClientService: jasmine.SpyObj<ClientService>;
  let mockActivatedRoute: jasmine.SpyObj<ActivatedRoute>;

  beforeEach(() => {
    const activatedRouteSpy = jasmine.createSpyObj('ActivatedRoute', [], { snapshot: { params: {} } });

    TestBed.configureTestingModule({
      declarations: [AppComponent, MockNavigationBarComponent, MockFooterComponent],
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MaterialModule
    ],
      providers: [
        { provide: ClientService },
        { provide: ActivatedRoute, useValue: activatedRouteSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    mockClientService = TestBed.inject(ClientService) as jasmine.SpyObj<ClientService>;
    mockActivatedRoute = TestBed.inject(ActivatedRoute) as jasmine.SpyObj<ActivatedRoute>;

    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });
});
