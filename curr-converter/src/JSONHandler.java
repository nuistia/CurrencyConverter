import com.google.gson.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class JSONHandler {

    private final Currency[] currencies1 = new Currency[61];

    public JSONHandler() {
        try {
            this.initializeCurrencies("https://bank.gov.ua/NBU_Exchange/exchange?json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeCurrencies(String strUrl) throws IOException {

        URL url = new URL(strUrl);
        URLConnection connection = url.openConnection();
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonArray = null;
        JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
        } else if (jsonElement.isJsonArray()) {
            jsonArray = jsonElement.getAsJsonArray();
        }
        reader.close();

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.size(); i++) {
                Gson temp = new Gson();
                currencies1[i] = temp.fromJson(jsonArray.get(i).toString(), Currency.class);
            }
        }
    }

    public Currency[] getCurrencies1() {
        return currencies1;
    }
}
