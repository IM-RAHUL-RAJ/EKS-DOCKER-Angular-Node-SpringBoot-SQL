export interface Client {
    email: string;
    password: string;
    fullName: string;
    dateOfBirth: string;
    country: string;
    postalCode: string;
    identification: { type: string; value: string };
    clientId: string;
  }
  