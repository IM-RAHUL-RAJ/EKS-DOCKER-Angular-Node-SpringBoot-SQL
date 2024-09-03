import { ChangeDetectorRef, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { Client } from '../models/client';
import { ClientVerification } from '../models/client-verification';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private baseUrl = 'http://localhost:3001'; // Main server URL
  private clientVerificationUrl = 'http://localhost:3000'; // URL for Fmts
  private storageSubject = new BehaviorSubject<string>(sessionStorage.getItem('currentUser') || '');

  constructor(private http: HttpClient) {}

  // Method to get clientId from Fmts
  getClientIdFromFmts(client: Partial<Client>): Observable<ClientVerification> {
    return this.http.post<ClientVerification>(`${this.clientVerificationUrl}/fmts/client`, {
      clientId: '',
      dateOfBirth: client.dateOfBirth,
      email: client.email,
      country: client.country,
      postalCode: client.postalCode,
      identification: client.identification
    });
  }

  // Registration method (after getting clientId from server2)
  register(client: Client): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, client);
  }

  // Login method
  login(email: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, { email, password });
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
}
