import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ClientService } from 'src/app/services/client.service';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent {

  @Input() isLoggedIn! : boolean 

  isMenuOpen = false;

  constructor(private clientService: ClientService, private router: Router) {}

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  logOut() {
    this.clientService.logout();
    this.router.navigate(['/login'])?.then(() => {
      window.location.reload();
    });
  }

}
