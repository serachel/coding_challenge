import { LitElement, html } from "lit";
import { customElement, property, state } from "lit/decorators.js";
import "../components/utils/EmailInput";
import NEWSLETTER_API from "../api_clients/NewsletterSubscriptionApiClient";

@customElement("newsletter-subscription")
export class NewsletterSubscription extends LitElement {
  @state()
  private email: string = "";

  @state()
  private error: Boolean = true;

  @property({ type: Boolean })
  sucess = false;

  private handleValueChanged(e: CustomEvent) {
    this.email = e.detail.value;
  }

  private handleErrorValueChanged(e: CustomEvent) {
    this.error = e.detail.value;
  }

  private _onClick() {
    NEWSLETTER_API.subscribtion
      .createEmailSubscription({ createNewsletterSubscriptionRequestDto: { email: this.email } })
      .then(() => {
        this.sucess = true;
      });
  }

  render() {
    return html`
      <link rel="stylesheet" href="./src/index.css" />
      <div class="max-w-xl mx-auto p-6">
        <div class="bg-[#faf8f7] p-3 mb-2">
          <h1 class="text-2xl text-center">NEWSLETTER ANMELDUNG</h1>
        </div>
        <email-input
          class="w-full"
          .value=${this.email}
          @value-changed=${this.handleValueChanged}
          @error-changed=${this.handleErrorValueChanged}
        ></email-input>
        <button
          class="bg-black text-white rounded px-4 py-2 mt-2 hover:bg-[#ba0c2f] w-full"
          data-testid="submit-button"
          @click=${this._onClick}
          ?disabled=${this.error}
        >
          Newsletter abonnieren
        </button>
        ${this.sucess
          ? html`<div class="text-[#136233]" data-testid="success-message">
              Vielen Dank für Ihre Anmeldung zu unserem Newsletter!
            </div>`
          : html``}
      </div>
    `;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    "newsletter-subscription": NewsletterSubscription;
  }
}
