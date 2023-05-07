public class Currency {

    private final String StartDate;
    private final String TimeSign;
    private final String CurrencyCode;
    private final String CurrencyCodeL;
    private final int Units;
    private final double Amount;


    public Currency(String startDate, String timeSign, String currencyCode, String currencyCodeL, int units, double amount) {
        this.StartDate = startDate;
        this.TimeSign = timeSign;
        this.CurrencyCode = currencyCode;
        this.CurrencyCodeL = currencyCodeL;
        this.Units = units;
        this.Amount = amount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nCurrency: ").append("\t").append(CurrencyCodeL).append("\n");
        sb.append("Units: ").append("\t").append(Units).append("\n");
        sb.append("Amount: ").append("\t").append(Amount).append("\n");
        return sb.toString();
    }

    public String getCurrencyCodeL() {
        return CurrencyCodeL;
    }

    public int getUnits() {
        return Units;
    }

    public double getAmount() {
        return Amount;
    }
}
