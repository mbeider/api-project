package request;

import org.json.JSONObject;

public class CountriesRequest extends BaseRequest{
    public static JSONObject getWebhookBody (String url) {
        return new JSONObject().put("URL", url);
    }
}
