import java.util.*;

class Stock {
    String sym ;
    double price;

    Stock(String sym, double price) {
        this.sym = sym;
        this.price = price;
    }
}

class Portfolio {
    Map<String, Integer> stocks = new HashMap<>();
    double balance = 50000.0; // Starting balance

    void buyStock(String sym, int quantity, double price) {
        double cost = quantity * price;
        if (cost > balance) {
            System.out.println("Insufficient balance to buy stocks!");
        } else {
            balance -= cost;
            stocks.put(sym, stocks.getOrDefault(sym, 0) + quantity);
            System.out.println("Successfully purchased " + quantity + " shares of " + sym + ". Happy trading!");
        }
    }

    void sellStock(String sym, int quantity, double price) {
        if (!stocks.containsKey(sym) || stocks.get(sym) < quantity) {
            System.out.println("Transaction failed: Insufficient shares available for sale!");
        } else {
            double earnings = quantity * price;
            balance += earnings;
            stocks.put(sym, stocks.get(sym) - quantity);
            if (stocks.get(sym) == 0) stocks.remove(sym);
            System.out.println("Sold " + quantity + " shares of " + sym + ".");
        }
    }

    void viewPortfolio() {
        System.out.println("Portfolio:");
        stocks.forEach((sym, quantity) -> System.out.println(sym + ": " + quantity + " shares"));
        System.out.println("Balance: $" + balance);
    }
}

public class StockTradingApp {
    static Map<String, Stock> market = new HashMap<>();

    public static void main(String[] args) {
        // Initialize market data
        market.put("TESLA", new Stock("TESLA", 150.0));
        market.put("BMW", new Stock("BMW", 2800.0));
        market.put("TATA", new Stock("TATA", 3400.0));

        Portfolio portfolio = new Portfolio();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Our Stock Trading Platform!");
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View the Portfolio");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // View Market Data
                    System.out.println("Market Data:");
                    market.forEach((sym, stock) ->
                            System.out.println(stock.sym + ": $" + stock.price));
                    break;

                case 2: // Buy Stock
                    System.out.print("Enter stock symbol: ");
                    String buySymbol = scanner.next().toUpperCase();
                    if (!market.containsKey(buySymbol)) {
                        System.out.println("Invalid stock symbol!");
                        break;
                    }
                    System.out.print("Enter quantity to buy: ");
                    int buyQuantity = scanner.nextInt();
                    portfolio.buyStock(buySymbol, buyQuantity, market.get(buySymbol).price);
                    break;

                case 3: // Sell Stock
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = scanner.next().toUpperCase();
                    if (!market.containsKey(sellSymbol)) {
                        System.out.println("Invalid stock symbol!");
                        break;
                    }
                    System.out.print("Enter quantity to sell: ");
                    int sellQuantity = scanner.nextInt();
                    portfolio.sellStock(sellSymbol, sellQuantity, market.get(sellSymbol).price);
                    break;

                case 4: // View Portfolio
                    portfolio.viewPortfolio();
                    break;

                case 5: // Exit
                    System.out.println("Exiting the platform !");
                    scanner.close();
                    return;

                default:
                System.out.println("Invalid choice. Please try again!");
            }
        }
    }
}