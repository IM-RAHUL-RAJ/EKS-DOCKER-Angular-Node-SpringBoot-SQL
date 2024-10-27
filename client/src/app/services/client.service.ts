import { ChangeDetectorRef, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { Client } from '../models/client';
import { ClientVerification } from '../models/client-verification';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private baseUrl = 'http://localhost:8080'; // Main server URL
  private clientVerificationUrl = 'http://localhost:3000'; // URL for Fmts
  private storageSubject = new BehaviorSubject<string>(sessionStorage.getItem('currentUser') || '');
  public availableCash: number = 100000;
  constructor(private http: HttpClient) {}

  // Method to get clientId from Fmts
  getClientIdFromFmts(client: Partial<Client>): Observable<ClientVerification> {
    return this.http.post<ClientVerification>(`${this.clientVerificationUrl}/fmts/client`, {
      clientId: '',
      dateOfBirth: client.dateOfBirth,
      email: client.email,
      country: client.country,
      postalCode: client.postalCode,
      identificationType: client.identificationType,
      identificationNumber: client.identificationNumber,
      profileStatus: client.profileStatus
    });
  }

  // Registration method (after getting clientId from server2)
  register(client: Client): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/clients/register`, client);
  }

  // Login method
  login(email: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/clients/login`, { email, password });
  }

  // Get current user
  getCurrentUser() {
    return JSON.parse(sessionStorage.getItem('currentUser') || '{}');
  }

  // Check if user is logged in
  isLoggedId() {
      return this.storageSubject.asObservable();
  }

  // Logout user
  logout() {
    sessionStorage.removeItem('currentUser');
  }

  getAvailCash(){
    return this.availableCash;
  }
}
