import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.css']
})
export class CarouselComponent implements OnDestroy,AfterViewInit  {
  @ViewChild('carouselWrapper') carouselWrapper!: ElementRef;
  currentIndex = 0;
  itemsToShow = 3;
  scrollInterval: any;

  cards = [
    { image: 'assets/images/news.jpg', heading: 'News heading : This is a news summary. We wil later use Api 1', link: '#link1' },
    { image: 'assets/images/news.jpg', heading: 'News heading : This is a news summary. We wil later use Api 2', link: '#link2' },
    { image: 'assets/images/news.jpg', heading: 'News heading : This is a news summary. We wil later use Api 3', link: '#link3' },
    { image: 'assets/images/news.jpg', heading: 'News heading : This is a news summary. We wil later use Api 4', link: '#link1' },
    { image: 'assets/images/news.jpg', heading: 'News heading : This is a news summary. We wil later use Api 5', link: '#link2' },
    { image: 'assets/images/news.jpg', heading: 'News heading : This is a news summary. We wil later use Api 6', link: '#link3' },
    // Add more card objects as needed
  ];

  ngAfterViewInit() {
    this.autoScroll();
  }

  prevSlide() {
    const wrapper = this.carouselWrapper.nativeElement;
    this.currentIndex = (this.currentIndex > 0) ? this.currentIndex - 1 : Math.max(this.cards.length - this.itemsToShow, 0);
    this.updateCarouselPosition();
  }

  nextSlide() {
    const wrapper = this.carouselWrapper.nativeElement;
    this.currentIndex = (this.currentIndex < this.cards.length - this.itemsToShow) ? this.currentIndex + 1 : 0;
    this.updateCarouselPosition();
  }

  updateCarouselPosition() {
    const wrapper = this.carouselWrapper.nativeElement;
    const itemWidth = wrapper.children[0].clientWidth;
    wrapper.scrollLeft = itemWidth * this.currentIndex;
  }

  autoScroll() {
    const wrapper = this.carouselWrapper.nativeElement;
    const itemWidth = wrapper.children[0].clientWidth;

    this.scrollInterval = setInterval(() => {
      this.currentIndex = (this.currentIndex < this.cards.length - this.itemsToShow) ? this.currentIndex + 1 : 0;
      wrapper.scrollLeft = itemWidth * this.currentIndex;
    }, 3000); // Scroll every 3 seconds
  }

  ngOnDestroy() {
    if (this.scrollInterval) {
      clearInterval(this.scrollInterval);
    }
  }
}
