export class Portfolio {
    instrument: string;
    instrumentId: string;
    quantity: number;
    averagePrice: number;
    investedCapital: number;
    ltp: number; 
    percentChange: number; 
    profitLoss: number; 
    dayChangePercent: number; 

    constructor(
        instrument: string,
        instrumentId: string,
        quantity: number,
        averagePrice: number,
        investedCapital: number,
        ltp: number,
        percentChange: number,
        profitLoss: number,
        dayChangePercent: number
    ) {
        this.instrument = instrument;
        this.instrumentId = instrumentId;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
        this.investedCapital = investedCapital;
        this.ltp = ltp;
        this.percentChange = percentChange;
        this.profitLoss = profitLoss;
        this.dayChangePercent = dayChangePercent;
    }
}
