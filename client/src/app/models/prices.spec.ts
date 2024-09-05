import { Instruments } from './instruments';
import { Prices } from './prices';

describe('Prices', () => {
  it('should create an instance', () => {
    expect(new Prices(NaN,NaN,'',new Instruments('','','','','',NaN,NaN))).toBeTruthy();
  });
});
