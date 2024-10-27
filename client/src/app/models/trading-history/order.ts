import { Direction } from "./trade";

export class Order {
    constructor(
        public instrumentId:string,
        public quantity:number,
        public targetPrice:number,
        public direction:Direction,
        public clientId:string,
        public token:number,
        public orderDate:string
    ) {}
}
