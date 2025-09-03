/// <reference types="cypress" />
import "../../src/pages/NewsletterSubscription";
import { html } from 'lit';

describe('NewsletterSubscription Component', () => {

    it('should visualize success on valid input', () => {
        cy.intercept('POST', 'http://localhost:8080/newsletter-subscription', {
            statusCode: 201,
            body: {
            },
        }).as('postSubscription');
        cy.mount(html` <newsletter-subscription></newsletter-subscription>`);

        cy.get('email-input')
            .shadow().find('[data-testid="email-input"]')
            .should('be.visible');
        cy.get('[data-testid="submit-button"]').should('be.visible').and('be.disabled')
        cy.get('[data-testid="email-input"]').should('be.visible').type('test@test.de', { force: true })
        cy.get('[data-testid="success-icon"]').should('be.visible')
        cy.get('[data-testid="submit-button"]').should('be.visible').and('not.be.disabled').click()
        cy.wait('@postSubscription')
        cy.get('[data-testid="success-message"]').should('be.visible').contains('Vielen Dank für Ihre Anmeldung zu unserem Newsletter!')
    })
});