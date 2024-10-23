export class InvestmentPreferences {
    constructor(
    public cliendID : String,
    public investmentPurpose: String,
    public investmentPurposeDescription: String,
    public riskTolerance :  String,
    public incomeCategory :  String,
    public investmentYear:  String,
    public isRoboAdviserTermsAccepted: boolean){}
}
