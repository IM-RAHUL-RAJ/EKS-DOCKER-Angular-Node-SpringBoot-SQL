export class InvestmentPreferences {
    constructor(
    public cliendID : String,
    public investmentPurpose: String,
    public riskTolerance :  String,
    public incomeCategory :  String,
    public investmentYears:  String,
    public isRoboAdviserTermsAccepted: boolean){}
}
