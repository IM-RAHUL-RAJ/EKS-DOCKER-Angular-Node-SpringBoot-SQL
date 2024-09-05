import { ClientVerification } from './client-verification';

describe('ClientVerification', () => {
  it('should create an instance', () => {

    let client:ClientVerification = {
      "clientId": "100001",
      "token": "Karen Verma",
      "dateOfBirth": "07/01/2001",
      "country": "United States",
      "postalCode": "90001",
      "identification": [
        {
          "type": "SSN",
          "value": "100111111"
        }
      ],
    }

    expect(client).toBeTruthy();
  });
});
