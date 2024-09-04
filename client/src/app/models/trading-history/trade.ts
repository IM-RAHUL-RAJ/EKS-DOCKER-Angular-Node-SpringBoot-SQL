import { Order } from "./order";

export enum Direction {
    buy="BUY",
    sell="SELL"  
  }

export class Trade {
    constructor(
        public instrumentId:string,
        public quantity:number,
        public executionPrice:number,
        public direction:Direction,
        public clientId:string,
        public order:Order,
        public tradeId:string,
        public cashValue:number,
        public tradeDate:string
    ){}
}
