export interface ClientVerification {
    clientId: string;
    token: string;
    dateOfBirth: string;
    country: string;
    postalCode: string;
    identification: { type: string; value: string };
  }
  