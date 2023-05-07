public class Calculator {

    private final CurrencyMap currencyMap = new CurrencyMap();

    public double calculatePurchasePrice(String curr, int items, double percent) {
        percent = 1.0 + (percent / 100.0);
        return currencyMap.getCurrencyMap().get(curr) * percent * items;
    }

    public double calculateSalePrice(String curr, int items, double percent) {
        percent = 1.0 - (percent / 100.0);
        return currencyMap.getCurrencyMap().get(curr) * percent * items;
    }
}
