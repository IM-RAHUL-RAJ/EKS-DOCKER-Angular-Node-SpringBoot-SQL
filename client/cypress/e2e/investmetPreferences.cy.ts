import { AppPageObject } from "cypress/support/app.po";

describe('Investment Preferences Test', () => {
    let appPage: AppPageObject = new AppPageObject();
  
    before(() => {
        appPage.navigateToHomePage()
        appPage.login()
      
    })

    it('navigates to investment Preferences',()=>{
        cy.get('button[id="user"]').click()
        cy.get('button[id="investmentNavigation"]').click()
    })

    after(()=>{
        appPage.logout()
    })

  
  })