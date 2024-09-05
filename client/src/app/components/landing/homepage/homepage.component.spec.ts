import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomepageComponent } from './homepage.component';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-watchlist'
})
class MockWatchlistComponent {

}
@Component({
  selector: 'app-reports'
})
class MockReportsComponent {

}
describe('HomepageComponent', () => {
  let component: HomepageComponent;
  let fixture: ComponentFixture<HomepageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HomepageComponent, MockWatchlistComponent, MockReportsComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(HomepageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
