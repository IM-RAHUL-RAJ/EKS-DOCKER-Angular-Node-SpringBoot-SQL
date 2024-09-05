import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ClientService } from './client.service';
import { Client } from '../models/client';
import { ClientVerification } from '../models/client-verification';

describe('ClientService', () => {
  let service: ClientService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ClientService]
    });

    service = TestBed.inject(ClientService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('register', () => {
    it('should return an observable of any', () => {
      const dummyClient: Client = {
        dateOfBirth: '1990-01-01',
        email: 'test@example.com',
        country: 'Country',
        postalCode: '12345',
        password: '123456',
        fullName: 'Test Name',
        identification: {
          type: 'PAN',
          value: 'AIEPK3333E'
        },
        clientId: '100001'
      };


      service.register(dummyClient).subscribe((result) => {
        expect(result).toBeTruthy();
      });

      const req = httpMock.expectOne(`${service['baseUrl']}/register`);
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(dummyClient);
      req.flush({});
    });
  });

  describe('login', () => {
    it('should return an observable of any', () => {
      const email = 'test@example.com';
      const password = 'password123';

      service.login(email, password).subscribe((result) => {
        expect(result).toBeTruthy();
      });

      const req = httpMock.expectOne(`${service['baseUrl']}/login`);
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual({ email, password });
      req.flush({});
    });
  });

  describe('getCurrentUser', () => {
    it('should return the current user from sessionStorage', () => {
      const currentUser = { name: 'John Doe' };
      sessionStorage.setItem('currentUser', JSON.stringify(currentUser));

      const user = service.getCurrentUser();
      expect(user).toEqual(currentUser);
    });

    it('should return an empty object if no current user is found', () => {
      sessionStorage.removeItem('currentUser');

      const user = service.getCurrentUser();
      expect(user).toEqual({});
    });
  });

  describe('isLoggedId', () => {
    it('should return an observable of storageSubject', (done) => {
      service.isLoggedId().subscribe((currentUser) => {
        expect(currentUser).toEqual('');
        done();
      });
    });
  });

  describe('logout', () => {
    it('should remove currentUser from sessionStorage', () => {
      sessionStorage.setItem('currentUser', JSON.stringify({ name: 'John Doe' }));
      service.logout();

      const user = sessionStorage.getItem('currentUser');
      expect(user).toBeNull();
    });
  });
});
