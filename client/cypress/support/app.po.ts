export class AppPageObject {
    navigateToHomePage() {
        cy.visit("/");
    }
    login(){
        cy.get('button[id="user"]').click()
        cy.get('button[id="loginButton"]').click()
        cy.get('input[id="email"]').type("one@email.com")
        cy.get('input[id="password"]').type("password1")
        cy.get('button').contains('Login').click();
    }
    logout(){
        cy.get('button[id="user"]').click()
        cy.get('button[id="logout"]').click()
    }
}   