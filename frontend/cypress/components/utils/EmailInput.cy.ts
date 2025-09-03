/// <reference types="cypress" />
import "../../../src/components/utils/EmailInput";
import { html } from 'lit';

describe('EmailInput Component', () => {

  it('should visualize success on valid input', () => {
    cy.mount(html` <email-input></email-input>`);

    cy.get('email-input')
      .shadow().find('[data-testid="email-input"]')
      .should('be.visible');
    cy.get('[data-testid="email-input"]').should('be.visible').type('test@test.de', { force: true })
    cy.get('[data-testid="success-icon"]').should('be.visible')
  })

  it('should visualize error for invalid input', () => {
    cy.mount(html` <email-input></email-input>`);
    cy.get('email-input')
      .shadow().find('[data-testid="email-input"]')
      .should('be.visible');
    cy.get('[data-testid="email-input"]').should('be.visible').type('test', { force: true })
    cy.get('[data-testid="error-span-invalid-email"]').should('be.visible').contains('Bitte geben Sie eine gültige E-Mail-Adresse ein.')
  })

  it('should visualize error for empty input', () => {
    cy.mount(html` <email-input></email-input>`);
    cy.get('email-input')
      .shadow().find('[data-testid="email-input"]')
      .should('be.visible');
    cy.get('[data-testid="email-input"]').should('be.visible').type('test{backspace}{backspace}{backspace}{backspace}', { force: true })
    cy.get('[data-testid="error-empty-input"]').should('be.visible').contains('Dieses Feld ist ein Pflichtfeld.')
  })

});