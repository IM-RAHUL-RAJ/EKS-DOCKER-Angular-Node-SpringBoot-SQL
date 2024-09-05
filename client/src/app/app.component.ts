import { Component, Input, OnInit } from '@angular/core';
import { ClientService } from './services/client.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(private clientService: ClientService, private route: ActivatedRoute) {
    
  }
  ngOnInit(): void {
    // this.isLoggedIn = this.clientService.isLoggedId()
    this.clientService.isLoggedId()?.subscribe(value => {
      this.isLoggedIn = value? true: false;
    })
  }
  title = 'stockstream';
  isLoggedIn = false;
}
