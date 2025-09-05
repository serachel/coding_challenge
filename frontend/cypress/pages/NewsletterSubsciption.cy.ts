/// <reference types="cypress" />
import "../../src/pages/NewsletterSubscription";
import { html } from "lit";
import { BASE_PATH } from "../../src/types";

describe("NewsletterSubscription Component", () => {
  it("should visualize success on valid input", () => {
    cy.intercept("POST", `${BASE_PATH}/newsletter-subscription`, {
      statusCode: 201,
      body: {},
    }).as("postSubscription");
    cy.mount(html` <newsletter-subscription></newsletter-subscription>`);

    cy.get("email-input").shadow().find('[data-testid="email-input"]').should("be.visible");
    cy.get('[data-testid="submit-button"]').should("be.visible").and("be.disabled");
    cy.get('[data-testid="email-input"]')
      .should("be.visible")
      .type("test@test.de", { force: true });
    cy.get('[data-testid="success-icon"]').should("be.visible");
    cy.get('[data-testid="submit-button"]').should("be.visible").and("not.be.disabled").click();
    cy.wait("@postSubscription");
    cy.get('[data-testid="success-message"]')
      .should("be.visible")
      .contains("Vielen Dank für Ihre Anmeldung zu unserem Newsletter!");
  });

  it("should send backend call when enter is pressed in input field and input is valid", () => {
    cy.intercept("POST", `${BASE_PATH}/newsletter-subscription`, {
      statusCode: 201,
      body: {},
    }).as("postSubscription");
    cy.mount(html` <newsletter-subscription></newsletter-subscription>`);

    cy.get("email-input").shadow().find('[data-testid="email-input"]').should("be.visible");
    cy.get('[data-testid="submit-button"]').should("be.visible").and("be.disabled");
    cy.get('[data-testid="email-input"]')
      .should("be.visible")
      .type("test@test.de{enter}", { force: true });
    cy.wait("@postSubscription");
    cy.get('[data-testid="success-message"]')
      .should("be.visible")
      .contains("Vielen Dank für Ihre Anmeldung zu unserem Newsletter!");
  });

  it("should not send backend call when enter is pressed in input field and input is valid", () => {
    cy.intercept("POST", `${BASE_PATH}/newsletter-subscription`, cy.spy().as("postSubscription"));
    cy.mount(html` <newsletter-subscription></newsletter-subscription>`);

    cy.get("email-input").shadow().find('[data-testid="email-input"]').should("be.visible");
    cy.get('[data-testid="submit-button"]').should("be.visible").and("be.disabled");
    cy.get('[data-testid="email-input"]')
      .should("be.visible")
      .type("testtest.de{enter}", { force: true });
    cy.get("@postSubscription").should("not.have.been.called");
  });
});
