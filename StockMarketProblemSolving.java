
import java.util.Arrays;
import java.util.List;

public class StockMarketProblemSolving {
	private static final double SCENARIO_ONE = 2.11;
	private static final double SCENARIO_TWO = 1.96;
	private static final double SCENARIO_THREE = 1.74;
	private static final int TOTAL_QUANTITY = 550;

	// Start inputs
	private final Quote quote1 = new Quote(10.00, 11.00, 1);
	private final Quote quote2 = new Quote(10.05, 11.00, 5);
	private final Quote quote3 = new Quote(10.10, 10.80, 6);
	private final Quote quote4 = new Quote(10.10, 10.81, 7);
	private final Quote quote5 = new Quote(10.10, 10.89, 8);
	private final Quote quote6 = new Quote(10.10, 10.92, 9);
	private final Quote quote7 = new Quote(10.03, 10.95, 14);
	private final Quote quote8 = new Quote(10.05, 00.00, 16);
	private final Quote quote9 = new Quote(10.17, 10.74, 19);
	private final Quote quote10 = new Quote(10.30, 10.76, 20);
	private final Quote quote11 = new Quote(10.33, 10.83, 21);
	private final Quote quote12 = new Quote(10.34, 10.84, 22);
	private final Quote quote13 = new Quote(10.33, 10.88, 23);
	private final Quote quote14 = new Quote(10.33, 10.83, 26);
	private final Quote quote15 = new Quote(10.40, 10.82, 33);
	private final Quote quote16 = new Quote(10.46, 10.81, 36);
	private final Quote quote17 = new Quote(10.45, 10.69, 39);
	private final Quote quote18 = new Quote(10.44, 10.75, 44);
	private final Quote quote19 = new Quote(10.42, 10.79, 49);
	private final Quote quote20 = new Quote(10.41, 10.83, 61);
	private final Quote quote21 = new Quote(10.37, 10.99, 63);
	private final Quote quote22 = new Quote(10.20, 11.00, 66);

	private final Trade trade1 = new Trade(10.22, 100, 11, "trade1");
	private final Trade trade2 = new Trade(10.35, 200, 18, "trade2");
	private final Trade trade3 = new Trade(10.55, 100, 43, "trade3");
	private final Trade trade4 = new Trade(10.45, 150, 45, "trade4");
	// End inputs

	private final List<Quote> scenarioOneQuotes;
	private final List<Quote> scenarioTwoQuotes;
	private final List<Quote> scenarioThreeQuotes;
	private final List<Trade> trades;

	public StockMarketProblemSolving() {

		this.scenarioOneQuotes = Arrays.asList(new Quote[] { quote1, quote2, quote3, quote4, quote5, quote6 });

		this.scenarioTwoQuotes = Arrays.asList(new Quote[] { quote1, quote2, quote3, quote4, quote5, quote6, quote7,
				quote8, quote9, quote10, quote11, quote12, quote13, quote14 });

		this.scenarioThreeQuotes = Arrays.asList(new Quote[] { quote1, quote2, quote3, quote4, quote5, quote6, quote7,
				quote8, quote9, quote10, quote11, quote12, quote13, quote14, quote15, quote16, quote17, quote19,
				quote18, quote20, quote21, quote22 });

		this.trades = Arrays.asList(new Trade[] { trade1, trade2, trade3, trade4, trade4 });
	}

	private void run() {

		runScenario(1, SCENARIO_ONE, this.scenarioOneQuotes);
		runScenario(2, SCENARIO_TWO, this.scenarioTwoQuotes);
		runScenario(3, SCENARIO_THREE, this.scenarioThreeQuotes);

		System.out.println("Congratulations on solving all three! Awesome job.");
	}

	private void runScenario(int num, double scenarioExpected, List<Quote> quotes) {

		System.out.println("Starting scenario " + num);
		Result result = getPriceImprovement(quotes);

		// All of this is just printing results
		if (result.totalPriceImprovement != scenarioExpected || result.totalQuantity != TOTAL_QUANTITY) {
			System.out.println("We've got a bug!");
			System.out.println("Expected total quantity = " + TOTAL_QUANTITY + ", actual = " + result.totalQuantity);
			System.out.println("Expected price improvement = $" + scenarioExpected + ", actual = $"
					+ result.totalPriceImprovement);
			System.exit(-1);
		}
		System.out.println("Scenario " + num + " passed.");
	}

	private Result getPriceImprovement(List<Quote> quotes) {
		/*
		 * Price improvement: given the market (what everyone else is doing, defined by
		 * the 'prevailing quote' or most recent information), how much better did you
		 * do? Logic: marketSellPrice - your price. Yes - all of the math is correct
		 */

		double priceImprovementTotal = 0;
		int quantityTotal = 0;
		for (Trade trade : trades) {
			Quote quote = getPrevailingQuote(trade.time, quotes);
			double priceImprovementPerTrade = trade.isBuy ? quote.marketSellPrice - trade.price
					: trade.price - quote.marketBuyPrice;
			priceImprovementTotal += priceImprovementPerTrade;
			quantityTotal += trade.quantity;
		}

		return new Result(quantityTotal, getValue(priceImprovementTotal));
	}

	private Quote getPrevailingQuote(long tradeTime, List<Quote> quotes) {
		// Prevailing quote: when you made your trade, what was the most recent value of
		// the market (what everyone else was doing)?

		Quote currentQuote = null;

		for (Quote quote : quotes) {
			if (quote.timeOfQuote > tradeTime) {
				break;
			}
			currentQuote = quote;
		}
		return currentQuote;
	}

	private static double getValue(double value) {
		// this method just truncates the price improvement value for easier readability
		double scale = Math.pow(10, 4);
		return Math.round(value * scale) / scale;
	}

	class Trade {
		// Remember, a "trade" is what YOU did, the price and time you bought shares
		double price;
		int quantity;
		int time;
		String name;
		boolean isBuy;

		public Trade(double price, int quantity, int time, String name) {
			this.price = price;
			this.quantity = quantity;
			this.time = time;
			this.name = name;
			this.isBuy = true;
		}
	}

	class Quote {
		// Remember, a "quote" defines what everyone else is doing - what is everyone
		// else buying and selling at?
		double marketBuyPrice;
		double marketSellPrice;
		int timeOfQuote;

		public Quote(double bid, double ask, int time) {
			this.marketBuyPrice = bid;
			this.marketSellPrice = ask;
			this.timeOfQuote = time;
		}
	}

	class Result {
		int totalQuantity;
		double totalPriceImprovement;

		public Result(int qty, double pxIm) {
			this.totalQuantity = qty;
			this.totalPriceImprovement = pxIm;
		}
	}

	public static void main(String args[]) {
		StockMarketProblemSolving runner = new StockMarketProblemSolving();
		runner.run();

		System.exit(1);
	}
}
