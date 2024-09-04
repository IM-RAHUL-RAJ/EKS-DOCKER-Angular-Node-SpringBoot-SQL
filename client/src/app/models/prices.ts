import { Instruments } from "./instruments";
export class Prices {
    constructor(
        // public instrumentId: string,
        public askPrice: number,
        public bidPrice: number,
        public priceTimestamp: string,
        public instrument: Instruments
    ){}
}
