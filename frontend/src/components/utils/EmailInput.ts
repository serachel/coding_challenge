import { LitElement, html } from "lit";
import { customElement, property, state } from "lit/decorators.js";

@customElement("email-input")
export class EmailInput extends LitElement {
  @state()
  private emailRegex =
    /^((?:[A-Za-z0-9!#$%&'*+\-\/=?^_`{|}~]|(?<=^|\.)"|"(?=$|\.|@)|(?<=".*)[ .](?=.*")|(?<!\.)\.){1,64})(@)((?:[A-Za-z0-9.\-])*(?:[A-Za-z0-9])\.(?:[A-Za-z0-9]){2,})$/i;

  value: string = "";

  @property({ type: Boolean })
  error = false;

  @property({ type: Boolean })
  isEmpty = false;

  private validateEmail(email: string): boolean {
    return this.emailRegex.test(email);
  }
  private dispatchErrorEvent() {
    this.dispatchEvent(
      new CustomEvent("error-changed", {
        detail: { value: this.error },
        bubbles: true,
        composed: true,
      }),
    );
  }
  private handleInput(e: Event) {
    const target = e.target as HTMLInputElement;
    this.value = target.value;
    this.isEmpty = this.value === "";

    if (this.validateEmail(this.value)) {
      this.error = false;
      this.dispatchErrorEvent();
    } else {
      this.error = true;
      this.dispatchErrorEvent();
    }
    // CustomEvent feuern, damit Elternkomponente den Wert bekommt
    this.dispatchEvent(
      new CustomEvent("value-changed", {
        detail: { value: this.value },
        bubbles: true,
        composed: true,
      }),
    );
  }
  render() {
    return html`
      <link rel="stylesheet" href="./src/index.css" />
      <div>
        <label
          for="input-9"
          class="block text-sm font-medium ${this.error
            ? "text-[#ba0c2f]"
            : this.value.length > 5
              ? "text-[#136233]"
              : "text-gray-700"}"
          >E-Mail Adresse</label
        >
        <div
          class="flex items-center mt-1 ${this.error
            ? "border-2 border-[#ba0c2f]"
            : this.value.length > 5
              ? "border-2 border-[#136233]"
              : "border-2 border-gray-700"} px-4 py-2 rounded"
        >
          <input
            type="email"
            class="w-full text-gray-700 focus-visible:outline-none"
            data-testid="email-input"
            placeholder="user@mail.com"
            .value=${this.value}
            @input=${this.handleInput}
          />
          ${this.error
            ? html`<svg
                class="w-6 h-6 text-[#ba0c2f] dark:text-white"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                fill="none"
                viewBox="0 0 24 24"
              >
                <path
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="1.6"
                  d="M12 13V8m0 8h.01M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
                />
              </svg>`
            : this.value.length > 5
              ? html`<svg
                  class="w-6 h-6 text-[#136233] dark:text-white"
                  data-testid="success-icon"
                  aria-hidden="true"
                  xmlns="http://www.w3.org/2000/svg"
                  width="24"
                  height="24"
                  fill="none"
                  viewBox="0 0 24 24"
                >
                  <path
                    stroke="currentColor"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="1.6"
                    d="M5 11.917 9.724 16.5 19 7.5"
                  />
                </svg> `
              : html``}
        </div>
        ${this.error && !this.isEmpty
          ? html`<span id="1" class="text-[#ba0c2f]" data-testid="error-span-invalid-email">
              Bitte geben Sie eine gültige E-Mail-Adresse ein.
            </span>`
          : this.error && this.isEmpty
            ? html`<span id="2" class="text-[#ba0c2f]" data-testid="error-empty-input"
                >Dieses Feld ist ein Pflichtfeld.</span
              >`
            : html``}
      </div>
    `;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    "email-input": EmailInput;
  }
}
