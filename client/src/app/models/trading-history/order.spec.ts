import { Order } from './order';
import { Direction } from './trade';

describe('Order', () => {
  it('should create an instance', () => {
    expect(new Order('',NaN,NaN,Direction.buy,'',NaN,'')).toBeTruthy();
  });
});
