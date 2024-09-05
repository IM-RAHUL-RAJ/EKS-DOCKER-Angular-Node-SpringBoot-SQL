import { Order } from './order';
import { Direction, Trade } from './trade';

describe('Trade', () => {
  it('should create an instance', () => {
    expect(new Trade('',NaN,NaN,Direction.buy,'',new Order('',NaN,NaN,Direction.buy,'',NaN,''),'',NaN,'')).toBeTruthy();
  });
});
