/* global trade */
const SCENARIO_ONE = 2.11;
const SCENARIO_TWO = 1.96;
const SCENARIO_THREE = 1.74;
const TOTAL_QUANTITY = 550;
function Quote(bid, ask, time) {
  // Remember, a "quote" defines what everyone else is doing - what is everyone
  // else buying and selling at?
  return {
    marketBuyPrice: bid,
    marketSellPrice: ask,
    timeOfQuote: time
  };
}
function Trade(price, quantity, time, name) {
  // Remember, a "trade" is what YOU did, the price and time you bought shares
  return {
    price: price,
    quantity: quantity,
    time: time,
    name: name,
    isBuy: true
  };
}
// Start inputs
let quote1 = Quote(10.00, 11.00, 1);
let quote2 = Quote(10.05, 11.00, 5);
let quote3 = Quote(10.10, 10.80, 6);
let quote4 = Quote(10.10, 10.81, 7);
let quote5 = Quote(10.10, 10.89, 8);
let quote6 = Quote(10.10, 10.92, 9);
let quote7 = Quote(10.03, 10.95, 14);
let quote8 = Quote(10.05, 0.00, 16);
let quote9 = Quote(10.17, 10.74, 19);
let quote10 = Quote(10.30, 10.76, 20);
let quote11 = Quote(10.33, 10.83, 21);
let quote12 = Quote(10.34, 10.84, 22);
let quote13 = Quote(10.33, 10.88, 23);
let quote14 = Quote(10.33, 10.83, 26);
let quote15 = Quote(10.40, 10.82, 33);
let quote16 = Quote(10.46, 10.81, 36);
let quote17 = Quote(10.45, 10.69, 39);
let quote18 = Quote(10.44, 10.75, 44);
let quote19 = Quote(10.42, 10.79, 49);
let quote20 = Quote(10.41, 10.83, 61);
let quote21 = Quote(10.37, 10.99, 63);
let quote22 = Quote(10.20, 11.00, 66);
let trade1 = Trade(10.22, 100, 11, "trade1");
let trade2 = Trade(10.35, 200, 18, "trade2");
let trade3 = Trade(10.55, 100, 43, "trade3");
let trade4 = Trade(10.45, 150, 45, "trade4");
// End inputs
let scenarioOneQuotes = [quote1, quote2, quote3, quote4, quote5, quote6];
let scenarioTwoQuotes = [quote1, quote2, quote3, quote4, quote5, quote6, quote7, quote8, quote9, quote10, quote11, quote12, quote13, quote14];
let scenarioThreeQuotes = [quote1, quote2, quote3, quote4, quote5, quote6, quote7, quote8, quote9, quote10, quote11, quote12, quote13, quote14, quote15, quote16, quote17, quote19, quote18, quote20, quote21, quote22];
let trades = [trade1, trade2, trade3, trade4, trade4];
function Result(qty, pxIm) {
  return {
    totalQuantity: qty,
    totalPriceImprovement: pxIm
  }
}
function getPriceImprovement(quotes) {
  // Price improvement: given the market (what everyone else is doing, defined by
  // the 'prevailing quote' or most recent information), how much better did you
  // do? Logic: marketSellPrice - your price. Yes - all of the math is correct
  let priceImprovementTotal = 0;
  let quantityTotal = 0;
  for (let idx in trades) {
    let trade = trades[idx];
    let quote = getPrevailingQuote(trade.time, quotes);
    let priceImprovementPerTrade = trade.isBuy ? quote.marketSellPrice - trade.price : trade.price - quote.marketBuyPrice;
    priceImprovementTotal += priceImprovementPerTrade;
    quantityTotal += trade.quantity;
  }
  let result = Result(quantityTotal, getValue(priceImprovementTotal));
  return result;
}
function getPrevailingQuote(tradeTime, quotes) {
  // Prevailing quote: when you made your trade, what was the most recent value of
  // the market (what everyone else was doing)?
  let currentQuote;
  for (let idx in quotes) {
    let quote = quotes[idx];
    if (quote.timeOfQuote > tradeTime)
      break;
    currentQuote = quote;
  }
  return currentQuote;
}
function getValue(value) {
  // this method just truncates the price improvement value for easier readability
  return Math.round(value * 100) / 100;
}
function runScenario(num, scenarioExpected, quotes) {
  console.log("Starting scenario " + num);
  let result = getPriceImprovement(quotes);
  // All of this is just printing results
  if (result.totalPriceImprovement !== scenarioExpected || result.totalQuantity !== TOTAL_QUANTITY) {
    console.log("We've got a bug!");
    console.log("Expected total quantity = " + TOTAL_QUANTITY + ", actual = " + result.totalQuantity);
    console.log("Expected price improvement = $" + scenarioExpected + ", actual = $" + result.totalPriceImprovement);
    throw Error("Unexpected results!");
  }
  console.log("Scenario " + num + " passed.");
}
runScenario(1, SCENARIO_ONE, scenarioOneQuotes);
runScenario(2, SCENARIO_TWO, scenarioTwoQuotes);
runScenario(3, SCENARIO_THREE, scenarioThreeQuotes);
console.log("Congratulations on solving all three! Awesome job.");