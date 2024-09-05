import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { InvestmentPreferences } from 'src/app/models/investment-preferences';
import { InvestmentPurposeService } from 'src/app/services/investment-purpose.service';

@Component({
  selector: 'app-investment-preferences',
  templateUrl: './investment-preferences.component.html',
  styleUrls: ['./investment-preferences.component.css']
})
export class InvestmentPreferencesComponent implements OnInit{
  
  public showTerms = false
  public showSubmissionAlert = false
  public isSubmitted = false
  public clientID! : String

  investmentPreferencesForm = new FormGroup({
    investmentPurpose : new FormControl('',Validators.required),
    riskTolerance:  new FormControl('',Validators.required),
    incomeCategory:  new FormControl('',Validators.required),
    investmentYears:  new FormControl('',Validators.required),
    isRoboAdviserTermsAccepted: new FormControl(''),
  })

  constructor(private investmentPreferenceService: InvestmentPurposeService, private router: Router){}

  ngOnInit(): void {
    this.investmentPreferenceService.getInvestmentPreference().subscribe((data) =>{
        if(data){
          this.clientID = data.cliendID
          this.patchExistingPreference(data)
        }
    })
  }

  onSubmit() {
    this.isSubmitted = true
    if(this.investmentPreferencesForm.valid && this.isRoboAdviserTermsAccepted.value){
      console.log('hello',this.investmentPreferencesForm)
      let newPreference = new InvestmentPreferences(
        this.clientID,
        this.investmentPurpose?.value,
        this.riskTolerance?.value,
        this.incomeCategory?.value,
        this.investmentYears?.value,
        this.isRoboAdviserTermsAccepted?.value
      )
      this.investmentPreferenceService.updateInvestmentPreference(newPreference)
      this.isSubmitted = false
      this.router.navigate(['/home'])

    }else{
      console.log('else',this.isRoboAdviserTermsAccepted)
      if(!this.isRoboAdviserTermsAccepted.value){
        this.showSubmissionAlertPopUp()
      }
    }
    
  }

  get investmentPurpose(){
    return this.investmentPreferencesForm && this.investmentPreferencesForm.get('investmentPurpose') as FormControl
  }

  get riskTolerance(){
    return this.investmentPreferencesForm && this.investmentPreferencesForm.get('riskTolerance') as FormControl
  }

  get incomeCategory(){
    return this.investmentPreferencesForm && this.investmentPreferencesForm.get('incomeCategory') as FormControl
  }

  get investmentYears(){
    return this.investmentPreferencesForm && this.investmentPreferencesForm.get('investmentYears') as FormControl
  }

  get isRoboAdviserTermsAccepted(){
    return this.investmentPreferencesForm && this.investmentPreferencesForm.get('isRoboAdviserTermsAccepted') as FormControl
  }

  patchExistingPreference(investmentPreference: InvestmentPreferences){
    if(investmentPreference.investmentPurpose){
      this.investmentPurpose?.patchValue(investmentPreference.investmentPurpose)
    }
    if(investmentPreference.riskTolerance){
      this.riskTolerance?.patchValue(investmentPreference.riskTolerance)
    }
    if(investmentPreference.incomeCategory){
      this.incomeCategory?.patchValue(investmentPreference.incomeCategory)
    }
    if(investmentPreference.investmentYears){
      this.investmentYears?.patchValue(investmentPreference.investmentYears)
    }
    if(investmentPreference.isRoboAdviserTermsAccepted){
      this.isRoboAdviserTermsAccepted?.patchValue(investmentPreference.isRoboAdviserTermsAccepted)
    }
    

  }

  showTermsPopUp(){
    this.showTerms = true
  }

  closeTermsPopUp(){
    this.showTerms = false
  }

  showSubmissionAlertPopUp(){
    this.showSubmissionAlert = true
  }

  closeSubmissionAlertPopUp(){
    this.showSubmissionAlert = false
  }

}
