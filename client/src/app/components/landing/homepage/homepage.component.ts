import { ChangeDetectionStrategy, Component, ViewChild } from '@angular/core';
import { StockIndexDataService } from '../data/StockIndexDataService';
import { IgxCarouselComponent } from 'igniteui-angular';

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [StockIndexDataService],
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent {
  public stocksData: any;
  constructor(private dataService: StockIndexDataService) {
    this.stocksData = [this.dataService.getData()];
  }

  @ViewChild('carousel', { static: true }) public carousel!: IgxCarouselComponent;

  public slides: any[] = [];

  public ngOnInit() {
    this.addSlides();
  }
  public addSlides() {
    this.slides.push(
      {
        description: 'Discover seamless investing options in stocks, CDs, and bonds with StockStream. Our platform provides user-friendly tools for making informed investment decisions.',
        heading: 'StockStream: Invest in Stocks, CDs & Bonds',
        image: 'https://i.ibb.co/ckc9pLN/Untitled-design.jpg',
        link: 'http://localhost:4200/trade'
      },
      {
        description: 'Efficiently manage your investment portfolio with StockStream\'s intuitive interface. Track your assets, analyze performance, and optimize your strategy with ease.',
        heading: 'StockStream: Manage Portfolio',
        image: 'https://i.ibb.co/sQ8bS9d/Untitled-design-2.jpg',
        link: 'http://localhost:4200/portfolio'
      },
      {
        description: 'Let our Robo-Advisor guide your investments with personalized strategies tailored to your financial goals. Experience automated investing like never before.',
        heading: 'Invest with Robo-Advisor',
        image: 'https://i.ibb.co/WFxb0jy/Untitled-design-1.jpg',
        link: 'http://localhost:4200/robo-advisor'
      }

    );
  }
}
