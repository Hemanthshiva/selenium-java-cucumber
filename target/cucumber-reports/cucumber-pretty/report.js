$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("cucumber/AddItemToCart.feature");
formatter.feature({
  "name": "Login to a portal and search for the an expensive item and add to cart",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "Login as a valid user and add an expensive dress to the cart and logout",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@addItemToCart"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "I login to the application using the below credentials",
  "rows": [
    {
      "cells": [
        "username",
        "password"
      ]
    },
    {
      "cells": [
        "automationguru92@gmail.com",
        "password123"
      ]
    }
  ],
  "keyword": "Given "
});
formatter.match({
  "location": "AddItemsToCartSteps.iLoginToTheApplicationUsingTheBelowCredentials(DataTable)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I search for the most expensive \"Dresses\"",
  "keyword": "When "
});
formatter.match({
  "location": "AddItemsToCartSteps.iSearchForTheMostExpensive(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I add the \"max\" price item to the cart",
  "keyword": "And "
});
formatter.match({
  "location": "AddItemsToCartSteps.iAddThePriceItemToTheCart(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "The item should be added to the cart",
  "keyword": "Then "
});
formatter.match({
  "location": "AddItemsToCartSteps.theItemShouldBeAddedToTheCart()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I should log out from the application",
  "keyword": "And "
});
formatter.match({
  "location": "AddItemsToCartSteps.iShouldLogOutFromTheApplication()"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
formatter.scenario({
  "name": "Login as a valid user and verify if the previously added item is still in the cart",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@verifyItemPresentInCart"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "I login to the application using the below credentials",
  "rows": [
    {
      "cells": [
        "username",
        "password"
      ]
    },
    {
      "cells": [
        "automationguru92@gmail.com",
        "password123"
      ]
    }
  ],
  "keyword": "Given "
});
formatter.match({
  "location": "AddItemsToCartSteps.iLoginToTheApplicationUsingTheBelowCredentials(DataTable)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I navigate to cart",
  "keyword": "When "
});
formatter.match({
  "location": "AddItemsToCartSteps.iNavigateToCart()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I should not see the item in the cart",
  "keyword": "Then "
});
formatter.match({
  "location": "AddItemsToCartSteps.iShouldNotSeeTheItemInTheCart()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I should log out from the application",
  "keyword": "And "
});
formatter.match({
  "location": "AddItemsToCartSteps.iShouldLogOutFromTheApplication()"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
formatter.scenario({
  "name": "Login as a valid user and add an expensive dress to the cart and logout",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@deleteItemFromCart"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "I login to the application using the below credentials",
  "rows": [
    {
      "cells": [
        "username",
        "password"
      ]
    },
    {
      "cells": [
        "automationguru92@gmail.com",
        "password123"
      ]
    }
  ],
  "keyword": "Given "
});
formatter.match({
  "location": "AddItemsToCartSteps.iLoginToTheApplicationUsingTheBelowCredentials(DataTable)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I search for the most expensive \"Women\"",
  "keyword": "When "
});
formatter.match({
  "location": "AddItemsToCartSteps.iSearchForTheMostExpensive(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I add the \"min\" price item to the cart",
  "keyword": "And "
});
formatter.match({
  "location": "AddItemsToCartSteps.iAddThePriceItemToTheCart(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "The item should be added to the cart",
  "keyword": "Then "
});
formatter.match({
  "location": "AddItemsToCartSteps.theItemShouldBeAddedToTheCart()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I delete the item from the cart",
  "keyword": "When "
});
formatter.match({
  "location": "AddItemsToCartSteps.iDeleteTheItemFromTheCart()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I should not see the item in the cart",
  "keyword": "Then "
});
formatter.match({
  "location": "AddItemsToCartSteps.iShouldNotSeeTheItemInTheCart()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I should log out from the application",
  "keyword": "And "
});
formatter.match({
  "location": "AddItemsToCartSteps.iShouldLogOutFromTheApplication()"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
});