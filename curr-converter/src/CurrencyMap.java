import java.util.HashMap;
import java.util.LinkedHashMap;

public class CurrencyMap {

    private final JSONHandler handler = new JSONHandler();
    private final HashMap<String, Double> currencyMap = new LinkedHashMap<>(61);

    public CurrencyMap() {
        fillMap();
    }

    private void fillMap() {

        for (Currency currency : handler.getCurrencies()) {
            currencyMap.put(currency.getCurrencyCodeL(), (currency.getAmount() / currency.getUnits()));
        }
    }

    public HashMap<String, Double> getCurrencyMap() {
        return currencyMap;
    }

    // return cost of currency
    public double getCurrency(String key) {
        return currencyMap.get(key);
    }
}
