import java.util.*;

class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    void updatePrice() {
        // Simulate price change
        double change = (Math.random() - 0.5) * 10;
        price = Math.max(1, price + change);
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    double balance = 10000;

    void buy(String symbol, int qty, double price) {
        double cost = qty * price;
        if (cost <= balance) {
            balance -= cost;
            holdings.put(symbol, holdings.getOrDefault(symbol, 0) + qty);
            System.out.println("Bought " + qty + " of " + symbol);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    void sell(String symbol, int qty, double price) {
        if (holdings.getOrDefault(symbol, 0) >= qty) {
            balance += qty * price;
            holdings.put(symbol, holdings.get(symbol) - qty);
            System.out.println("Sold " + qty + " of " + symbol);
        } else {
            System.out.println("Not enough shares.");
        }
    }

    void showPortfolio(Map<String, Stock> market) {
        System.out.println("\n--- Portfolio ---");
        for (String sym : holdings.keySet()) {
            int qty = holdings.get(sym);
            double price = market.get(sym).price;
            System.out.printf("%s: %d shares @ %.2f = %.2f\n", sym, qty, price, qty * price);
        }
        System.out.printf("Cash Balance: %.2f\n", balance);
        System.out.println("------------------\n");
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Stock> market = new HashMap<>();
        market.put("AAPL", new Stock("AAPL", 150));
        market.put("GOOG", new Stock("GOOG", 2800));
        market.put("TSLA", new Stock("TSLA", 700));

        Portfolio portfolio = new Portfolio();

        while (true) {
            // Update market prices
            for (Stock s : market.values()) s.updatePrice();

            // Display market
            System.out.println("---- Market ----");
            for (Stock s : market.values())
                System.out.printf("%s: $%.2f\n", s.symbol, s.price);

            System.out.println("\nOptions: 1-Buy 2-Sell 3-Portfolio 4-Exit");
            int choice = sc.nextInt();

            if (choice == 1 || choice == 2) {
                System.out.print("Enter Symbol: ");
                String sym = sc.next().toUpperCase();
                if (!market.containsKey(sym)) {
                    System.out.println("Invalid stock symbol.");
                    continue;
                }
                System.out.print("Enter Quantity: ");
                int qty = sc.nextInt();
                Stock stock = market.get(sym);
                if (choice == 1) portfolio.buy(sym, qty, stock.price);
                else portfolio.sell(sym, qty, stock.price);
            } else if (choice == 3) {
                portfolio.showPortfolio(market);
            } else {
                break;
            }
        }

        sc.close();
    }
}