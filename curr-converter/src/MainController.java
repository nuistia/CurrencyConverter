import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Set;

public class MainController implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private Label EURInfo;

    @FXML
    private Label USDInfo;

    @FXML
    private Label dateLabel;

    @FXML
    private ListView<String> listOfCurrencies;

    @FXML
    private ToggleGroup myToggleGroup;

    @FXML
    private RadioButton purchaseCurrency;

    @FXML
    private RadioButton saleCurrency;

    @FXML
    private Label defineCurrencyPrice;

    @FXML
    private TextField markup;

    @FXML
    private Label priceWithMarkup;

    @FXML
    private TextField inputSumField;

    @FXML
    private Label totalSum;

    @FXML
    private Button resetButton;

    @FXML
    private Button confirmButton;

    private double price = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setHeader();
        dateLabel.setText(getDate());

        initList();
        addListViewListener();

        addRadioButtonsListener();
    }

    @FXML
    private void onResetButtonAction() {

        defineCurrencyPrice.setText("Оберіть валюту для початку роботи");
        clearControls();
        setDisableInControls(priceWithMarkup, "", true);
    }

    private void clearControls() {

        markup.setText("");
        inputSumField.setText("");
        totalSum.setText("0.00");
        if (myToggleGroup.getSelectedToggle() != null) {
            myToggleGroup.getSelectedToggle().setSelected(false);
        }
    }

    @FXML
    private void onEnterPressedInputSumField() {

        double units;
        String text = inputSumField.getText();
        if (text.isEmpty()
                || text.matches(".*[a-zA-Z].*")
                || Double.parseDouble(text) < 0) {
            units = 0;
        } else {
            units = Double.parseDouble(text);
        }

        double total = price * units;
        totalSum.setText("%.2f".formatted(total));
    }

    private void addRadioButtonsListener() {
        myToggleGroup.selectedToggleProperty().addListener((changed, oldValue, newValue) -> {

            Calculator calc = new Calculator();
            var selected = listOfCurrencies.getSelectionModel().getSelectedItem();
            double markupPercent;
            String markupText = markup.getText();
            inputSumField.setPromptText(selected);

            if (markupText.isEmpty()
                    || markupText.matches(".*[a-zA-Z].*")
                    || Double.parseDouble(markupText) < 0) {
                markupPercent = 0;
            } else {
                markupPercent = Double.parseDouble(markupText);
            }

            if (purchaseCurrency.isSelected()) {
                price = calc.calculateSalePrice(selected, 1,
                        markupPercent);
            }
            if (saleCurrency.isSelected()) {
                price = calc.calculatePurchasePrice(selected, 1,
                        markupPercent);
            }

            String result = "Ціна з націнкою становить %.3f".formatted(price);
            priceWithMarkup.setText(result);
        });
    }

    private void setHeader() {
        USDInfo.setText("USD = " + new CurrencyMap().getCurrency("USD"));
        EURInfo.setText("EUR = " + new CurrencyMap().getCurrency("EUR"));
    }

    private void addListViewListener() {
        listOfCurrencies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == null) {
                defineCurrencyPrice.setText("Оберіть валюту для початку роботи");
                setDisableInControls(priceWithMarkup, "", true);
            } else {
                whileElementIsSelected(newValue);
            }
        });
    }

    private void setDisableInControls(Label label, String s, boolean b) {
        label.setText(s);
        markup.setDisable(b);
        purchaseCurrency.setDisable(b);
        saleCurrency.setDisable(b);
        inputSumField.setDisable(b);
        resetButton.setDisable(b);
        confirmButton.setDisable(b);
    }

    private void whileElementIsSelected(String newValue) {

        double currencyCost = new CurrencyMap().getCurrency(newValue);

        String result = String.format("""
                Ціна %s (за НБУ) становить %.4f грн""", newValue, currencyCost);

        setDisableInControls(defineCurrencyPrice, result, false);
    }

    private static String getDate() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.now().format(formatter);
    }

    private void initList() {

        CurrencyMap currMap = new CurrencyMap();
        Set<String> keySet = currMap.getCurrencyMap().keySet();

        ObservableList<String> curs = FXCollections.observableArrayList(keySet);
        listOfCurrencies.setItems(curs);
    }
}