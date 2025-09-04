import { NewsletterSubscriptionControllerApi, Configuration } from "../types";

const OPEN_API_CONFIG = new Configuration({
  basePath: "http://localhost:8080",
});

const NEWSLETTER_API = {
  subscription: new NewsletterSubscriptionControllerApi(OPEN_API_CONFIG),
};

export default NEWSLETTER_API;
