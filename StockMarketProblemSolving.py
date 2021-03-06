import math

SCENARIO_ONE = 2.11
SCENARIO_TWO = 1.96
SCENARIO_THREE = 1.74
TOTAL_QUANTITY = 550

class Quote :
    # Remember, a "quote" defines what everyone else is doing - what is everyone
    # else buying and selling at?

    def __init__ (self, bid, ask, time) :
        self.marketBuyPrice = bid
        self.marketSellPrice = ask
        self.timeOfQuote = time


class Trade :
    # Remember, a "trade" is what YOU did, the price and time you bought shares

    def __init__ (self, price, quantity, time, name) :
        self.price = price
        self.quantity = quantity
        self.time = time
        self.name = name
        self.isBuy = True

# Start inputs
quote1 = Quote(10.00, 11.00, 1)
quote2 = Quote(10.05, 11.00, 5)
quote3 = Quote(10.10, 10.80, 6)
quote4 = Quote(10.10, 10.81, 7)
quote5 = Quote(10.10, 10.89, 8)
quote6 = Quote(10.10, 10.92, 9)
quote7 = Quote(10.03, 10.95, 14)
quote8 = Quote(10.05, 00.00, 16)
quote9 = Quote(10.17, 10.74, 19)
quote10 = Quote(10.30, 10.76, 20)
quote11 = Quote(10.33, 10.83, 21)
quote12 = Quote(10.34, 10.84, 22)
quote13 = Quote(10.33, 10.88, 23)
quote14 = Quote(10.33, 10.83, 26)
quote15 = Quote(10.40, 10.82, 33)
quote16 = Quote(10.46, 10.81, 36)
quote17 = Quote(10.45, 10.69, 39)
quote18 = Quote(10.44, 10.75, 44)
quote19 = Quote(10.42, 10.79, 49)
quote20 = Quote(10.41, 10.83, 61)
quote21 = Quote(10.37, 10.99, 63)
quote22 = Quote(10.20, 11.00, 66)

trade1 = Trade(10.22, 100, 11, "trade1")
trade2 = Trade(10.35, 200, 18, "trade2")
trade3 = Trade(10.55, 100, 43, "trade3")
trade4 = Trade(10.45, 150, 45, "trade4")
# End inputs

scenarioOneQuotes = [ quote1, quote2, quote3, quote4, quote5, quote6 ]

scenarioTwoQuotes = [ quote1, quote2, quote3, quote4, quote5, quote6, quote7, quote8, quote9, quote10, quote11, quote12, quote13, quote14 ]
    
scenarioThreeQuotes = [ quote1, quote2, quote3, quote4, quote5, quote6, quote7, quote8, quote9, quote10, quote11, quote12, quote13, quote14, quote15, quote16, quote17, quote19, quote18, quote20, quote21, quote22 ]

trades = [ trade1, trade2, trade3, trade4, trade4 ]

def getPriceImprovement(quotes) :
    # Price improvement: given the market (what everyone else is doing, defined by
    # the 'prevailing quote' or most recent information), how much better did you
    # do? Logic: marketSellPrice - your price. Yes - all of the math is correct

    priceImprovementTotal = 0
    quantityTotal = 0;
    for trade in trades :
        quote = getPrevailingQuote(trade.time, quotes)
        priceImprovementPerTrade = quote.marketSellPrice - trade.price if trade.isBuy else trade.price - quote.marketBuyPrice
        priceImprovementTotal += priceImprovementPerTrade
        quantityTotal += trade.quantity
    return Result(quantityTotal, getValue(priceImprovementTotal))

def getPrevailingQuote(tradeTime, quotes) :
    # Prevailing quote: when you made your trade, what was the most recent value of
    # the market (what everyone else was doing)?

    for quote in quotes :
        if (quote.timeOfQuote > tradeTime) :
            break
        currentQuote = quote;
    return currentQuote;

def getValue(value) :
    # this method just truncates the price improvement value for easier readability
    return round(value, 2)

class Result :

    def __init__ (self, qty, pxIm) :
        self.totalQuantity = qty
        self.totalPriceImprovement = pxIm

def runScenario(num, scenarioExpected, quotes):

    print("Starting scenario " + str(num))
    result = getPriceImprovement(quotes)

    # All of this is just printing results
    if (result.totalPriceImprovement != scenarioExpected or result.totalQuantity != TOTAL_QUANTITY) :
            print("We've got a bug!")
            print("Expected total quantity = " + str(TOTAL_QUANTITY) + ", actual = " + str(result.totalQuantity))
            print("Expected price improvement = $" + str(scenarioExpected) + ", actual = $" + str(result.totalPriceImprovement))
            quit()
    print("Scenario " + str(num) + " passed.")


runScenario(1, SCENARIO_ONE, scenarioOneQuotes)
runScenario(2, SCENARIO_TWO, scenarioTwoQuotes)
runScenario(3, SCENARIO_THREE, scenarioThreeQuotes)

print("Congratulations on solving all three! Awesome job.")