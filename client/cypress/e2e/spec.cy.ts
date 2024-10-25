describe('My First Test', () => {
  describe('Portfolio Component', () => {
    before(() => {
      // Visit the login page
      cy.visit('/login');
  
      // Fill in the login form
      cy.get('#email').type('john.doe@example.com');
      cy.get('#password').type('password123');
  
      // Submit the login form
      cy.get('button[type="submit"]').click();
  
      // Wait for the login to complete and redirect to the home page
      cy.url().should('include', '/home');
      cy.get('.carousel-wrapper').should('be.visible'); // Ensure the carousel is visible
    });
  
    beforeEach(() => {
      // Visit the portfolio page
      cy.visit('/portfolio');
    });
  
    it('should load the portfolio data', () => {
      // Check if the portfolio summary is displayed
      cy.get('.portfolio-summary').should('be.visible');
  
      // Check if the portfolio cards are displayed
      cy.get('.portfolio-card').should('have.length.greaterThan', 0);

      cy.get('#sort').select('name');
  
      // Check if the portfolio is sorted by name
      cy.get('.portfolio-card .stock-name').then(($names) => {
        const names = $names.map((index, html) => Cypress.$(html).text()).get();
        const sortedNames = [...names].sort();
        expect(names).to.deep.equal(sortedNames);
      });

      // Select the sort option by invested capital
      cy.get('#sort').select('investedCapital');
  
      // Check if the portfolio is sorted by invested capital
      cy.get('.portfolio-card .stock-metric:nth-child(3) span').then(($values) => {
        const values = $values.map((index, html) => parseFloat(Cypress.$(html).text().replace(/[^0-9.-]+/g, ""))).get();
        const sortedValues = [...values].sort((a, b) => a - b);
        expect(values).to.deep.equal(sortedValues);
      });
      
      // Select the sort option by profit/loss
      cy.get('#sort').select('profitLoss');
  
      // Check if the portfolio is sorted by profit/loss
      cy.get('.portfolio-card .stock-metric:nth-child(6) span').then(($values) => {
        const values = $values.map((index, html) => parseFloat(Cypress.$(html).text().replace(/[^0-9.-]+/g, ""))).get();
        const sortedValues = [...values].sort((a, b) => a - b);
        expect(values).to.deep.equal(sortedValues);
      });

      // Click the sell button on the first portfolio card
      cy.get('.portfolio-card').first().find('button').click();
  
      
    });
    });

   
  
    
  
    

  describe('Trading History Component', () => {
    beforeEach(() => {
      // Visit the trading history page
      // Visit the login page
      cy.visit('/login');
  
      // Fill in the login form
      cy.get('#email').type('john.doe@example.com');
      cy.get('#password').type('password123');
  
      // Submit the login form
      cy.get('button[type="submit"]').click();
  
      // Wait for the login to complete and redirect to the home page
      cy.url().should('include', '/home');
      cy.get('.carousel-wrapper').should('be.visible');
      cy.visit('/history');
    });

    it('should load the trading history data', () => {
      // Check if the trading history table is displayed
      cy.get('table').should('be.visible');

      // Check if the trading history rows are displayed
      cy.get('table tbody tr').should('have.length.greaterThan', 0);
    });

    
  });
});
