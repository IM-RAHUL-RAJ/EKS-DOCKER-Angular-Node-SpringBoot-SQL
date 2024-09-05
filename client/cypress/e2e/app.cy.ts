import { AppPageObject } from "cypress/support/app.po";

describe('StockStream loading spec', () => {
  let appPage: AppPageObject = new AppPageObject();
  it('navigates to the Home page', () => {
    appPage.navigateToHomePage()
  })
  after(()=>{
    
  })
})