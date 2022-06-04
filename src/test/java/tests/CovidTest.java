package tests;

import config.APIConfig;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import request.CountriesRequest;
import responses.CountriesResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class CovidTest extends APIConfig{
    private static final List<String> listOfCoutries = new ArrayList<>(Arrays.asList("Colombia", "Norway", "Palau", "Bulgaria", "San Marino", "Sudan",
            "Zimbabwe", "Mozambique", "Senegal", "Jamaica", "Seychelles", "Ghana", "Liberia", "Pakistan", "Thailand",
            "Timor-Leste", "Turkmenistan", "Dominican Republic", "Montserrat", "Cape Verde", "Cayman Islands",
            "Guadeloupe", "Qatar", "Sao Tome and Principe", "Hungary", "Iraq", "Albania", "Iceland", "Moldova",
            "Russian Federation", "Turkey", "Western Sahara", "Nauru", "Syrian Arab Republic (Syria)",
            "Tanzania, United Republic of", "Bhutan", "Bosnia and Herzegovina", "Rwanda", "Ethiopia", "Guernsey",
            "Guyana", "Lithuania", "Panama", "Aruba", "Gibraltar", "Lebanon", "Malawi", "Mauritius", "Cameroon",
            "Kyrgyzstan", "Luxembourg", "Romania", "United Kingdom", "Andorra", "Brunei Darussalam", "Cocos (Keeling) Islands",
            "Mauritania", "Namibia", "Netherlands Antilles", "Peru", "Tajikistan", "Uganda", "Austria", "Congo (Kinshasa)",
            "Estonia", "Faroe Islands", "Guinea", "Heard and Mcdonald Islands", "Indonesia", "Macao, SAR China", "Malaysia",
            "Denmark", "Latvia", "Chad", "Fiji", "French Southern Territories", "Norfolk Island", "Finland",
            "Holy See (Vatican City State)", "Singapore", "Canada", "Saint-Martin (French part)", "Switzerland",
            "Benin", "Bermuda", "Costa Rica", "Egypt", "Iran, Islamic Republic of", "Kazakhstan", "Kuwait", "Libya",
            "Maldives", "Togo", "Belize", "Monaco", "South Georgia and the South Sandwich Islands", "Vanuatu",
            "British Virgin Islands", "Korea (South)", "Oman", "Puerto Rico", "Saint Kitts and Nevis", "Jersey",
            "Mali", "Micronesia, Federated States of", "Swaziland", "United Arab Emirates", "Angola", "Bahamas",
            "Burkina Faso", "Croatia", "Netherlands", "Niger", "Papua New Guinea", "Antigua and Barbuda", "Brazil",
            "Cambodia", "France", "Anguilla", "Equatorial Guinea", "Guam", "Italy", "Kiribati", "Tokelau", "Ukraine",
            "Botswana", "Gabon", "Grenada", "Martinique", "Niue", "Yemen", "Lao PDR", "Nicaragua", "Samoa", "Tunisia",
            "Turks and Caicos Islands", "Armenia", "Dominica", "Hong Kong, SAR China", "China", "Czech Republic", "Réunion",
            "El Salvador", "Madagascar", "Sweden", "Algeria", "Côte d'Ivoire", "Isle of Man", "Lesotho", "Suriname",
            "New Caledonia", "Paraguay", "Saudi Arabia", "Svalbard and Jan Mayen Islands", "Tonga", "Australia",
            "Azerbaijan", "Macedonia, Republic of", "Saint-Barthélemy", "Tuvalu", "Uruguay", "Montenegro", "Slovenia",
            "American Samoa", "Belgium", "Germany", "Japan", "Solomon Islands", "Georgia", "Uzbekistan", "Spain",
            "Venezuela (Bolivarian Republic)", "Argentina", "India", "Korea (North)", "US Minor Outlying Islands",
            "Bouvet Island", "Comoros", "Zambia", "Guinea-Bissau", "Honduras", "Portugal", "Saint Lucia", "South Africa",
            "Trinidad and Tobago", "Cuba", "Mongolia", "Sierra Leone", "Cook Islands", "Eritrea", "Gambia", "Liechtenstein",
            "Greenland", "Malta", "Morocco", "Nepal", "Nigeria", "Saint Pierre and Miquelon", "Somalia", "Burundi", "Djibouti",
            "French Guiana", "Palestinian Territory", "Saint Helena", "Virgin Islands, US", "ALA Aland Islands",
            "Central African Republic", "Congo (Brazzaville)", "French Polynesia", "Kenya", "British Indian Ocean Territory",
            "Ireland", "Jordan", "Mexico", "Myanmar", "Pitcairn", "Republic of Kosovo",
            "Taiwan, Republic of China", "Barbados", "Cyprus", "Falkland Islands (Malvinas)", "Bolivia", "Chile",
            "Guatemala", "Serbia", "Afghanistan", "Wallis and Futuna Islands", "Belarus", "Greece", "Poland",
            "Viet Nam", "Bahrain", "Bangladesh","Ecuador", "Marshall Islands", "Philippines", "United States of America",
            "Haiti", "South Sudan", "Antarctica", "Christmas Island", "Israel", "Mayotte", "New Zealand",
            "Northern Mariana Islands","Saint Vincent and Grenadines", "Slovakia", "Sri Lanka"));

    private static final List<String> listOfSlugs = new ArrayList<>(Arrays.asList("colombia", "norway", "palau", "bulgaria", "san-marino",
            "sudan", "zimbabwe", "mozambique", "senegal", "jamaica", "seychelles", "ghana", "liberia", "pakistan",
            "thailand", "timor-leste", "turkmenistan", "dominican-republic", "montserrat", "cape-verde", "cayman-islands",
            "guadeloupe", "qatar", "sao-tome-and-principe", "hungary", "iraq", "albania", "iceland", "moldova", "russia",
            "turkey", "western-sahara", "nauru", "syria", "tanzania", "bhutan", "bosnia-and-herzegovina", "rwanda",
            "ethiopia", "guernsey", "guyana", "lithuania", "panama", "aruba", "gibraltar", "lebanon", "malawi", "mauritius",
            "cameroon", "kyrgyzstan", "luxembourg", "romania", "united-kingdom", "andorra", "brunei", "cocos-keeling-islands",
            "mauritania", "namibia", "netherlands-antilles", "peru", "tajikistan", "uganda", "austria", "congo-kinshasa",
            "estonia", "faroe-islands", "guinea", "heard-and-mcdonald-islands", "indonesia", "macao-sar-china", "malaysia",
            "denmark", "latvia", "chad", "fiji", "french-southern-territories", "norfolk-island", "finland",
            "holy-see-vatican-city-state", "singapore", "canada", "saint-martin-french-part", "switzerland", "benin",
            "bermuda", "costa-rica", "egypt", "iran", "kazakhstan", "kuwait", "libya", "maldives", "togo", "belize",
            "monaco", "south-georgia-and-the-south-sandwich-islands", "vanuatu", "british-virgin-islands", "korea-south",
            "oman", "puerto-rico", "saint-kitts-and-nevis", "jersey", "mali", "micronesia", "swaziland", "united-arab-emirates",
            "angola", "bahamas", "burkina-faso", "croatia", "netherlands", "niger", "papua-new-guinea", "antigua-and-barbuda",
            "brazil", "cambodia", "france", "anguilla", "equatorial-guinea", "guam", "italy", "kiribati", "tokelau",
            "ukraine", "botswana", "gabon", "grenada", "martinique", "niue", "yemen", "lao-pdr", "nicaragua", "samoa",
            "tunisia", "turks-and-caicos-islands", "armenia", "dominica", "hong-kong-sar-china", "china", "czech-republic",
            "réunion", "el-salvador", "madagascar", "sweden", "algeria", "cote-divoire", "isle-of-man", "lesotho", "suriname",
            "new-caledonia", "paraguay", "saudi-arabia", "svalbard-and-jan-mayen-islands", "tonga", "australia", "azerbaijan",
            "macedonia", "saint-barthélemy", "tuvalu", "uruguay", "montenegro", "slovenia", "american-samoa", "belgium",
            "germany", "japan", "solomon-islands", "georgia", "uzbekistan", "spain", "venezuela", "argentina", "india",
            "korea-north", "us-minor-outlying-islands", "bouvet-island", "comoros", "zambia", "guinea-bissau", "honduras",
            "portugal", "saint-lucia", "south-africa", "trinidad-and-tobago", "cuba", "mongolia", "sierra-leone",
            "cook-islands", "eritrea", "gambia", "liechtenstein", "greenland", "malta", "morocco", "nepal", "nigeria",
            "saint-pierre-and-miquelon", "somalia", "burundi", "djibouti", "french-guiana", "palestine", "saint-helena",
            "virgin-islands", "ala-aland-islands", "central-african-republic", "congo-brazzaville", "french-polynesia",
            "kenya", "british-indian-ocean-territory", "ireland", "jordan", "mexico", "myanmar", "pitcairn", "kosovo",
            "taiwan", "barbados", "cyprus", "falkland-islands-malvinas", "bolivia", "chile", "guatemala", "serbia",
            "afghanistan", "wallis-and-futuna-islands", "belarus", "greece", "poland", "vietnam", "bahrain", "bangladesh",
            "ecuador", "marshall-islands", "philippines", "united-states", "haiti", "south-sudan", "antarctica",
            "christmas-island", "israel", "mayotte", "new-zealand", "northern-mariana-islands", "saint-vincent-and-the-grenadines",
            "slovakia", "sri-lanka"));

    private static final List<String> listOfIso= new ArrayList<>(Arrays.asList("CO", "NO", "PW", "BG", "SM", "SD", "ZW", "MZ", "SN", "JM",
            "SC", "GH", "LR", "PK", "TH", "TL", "TM", "DO", "MS", "CV", "KY", "GP", "QA", "ST", "HU", "IQ", "AL", "IS",
            "MD", "RU", "TR", "EH", "NR", "SY", "TZ", "BT", "BA", "RW", "ET", "GG", "GY", "LT", "PA", "AW", "GI", "LB",
            "MW", "MU", "CM", "KG", "LU", "RO", "GB", "AD", "BN", "CC", "MR", "NA", "AN", "PE", "TJ", "UG", "AT", "CD",
            "EE", "FO", "GN", "HM", "ID", "MO", "MY", "DK", "LV", "TD", "FJ", "TF", "NF", "FI", "VA", "SG", "CA", "MF",
            "CH", "BJ", "BM", "CR", "EG", "IR", "KZ", "KW", "LY", "MV", "TG", "BZ", "MC", "GS", "VU", "VG", "KR", "OM",
            "PR", "KN", "JE", "ML", "FM", "SZ", "AE", "AO", "BS", "BF", "HR", "NL", "NE", "PG", "AG", "BR", "KH", "FR",
            "AI", "GQ", "GU", "IT", "KI", "TK", "UA", "BW", "GA", "GD", "MQ", "NU", "YE", "LA", "NI", "WS", "TN", "TC",
            "AM", "DM", "HK", "CN", "CZ", "RE", "SV", "MG", "SE", "DZ", "CI", "IM", "LS", "SR", "NC", "PY", "SA", "SJ",
            "TO", "AU", "AZ", "MK", "BL", "TV", "UY", "ME", "SI", "AS", "BE", "DE", "JP", "SB", "GE", "UZ", "ES", "VE",
            "AR", "IN", "KP", "UM", "BV", "KM", "ZM", "GW", "HN", "PT", "LC", "ZA", "TT", "CU", "MN", "SL", "CK", "ER",
            "GM", "LI", "GL", "MT", "MA", "NP", "NG", "PM", "SO", "BI", "DJ", "GF", "PS", "SH", "VI", "AX", "CF", "CG",
            "PF", "KE", "IO", "IE", "JO", "MX", "MM", "PN", "XK", "TW", "BB", "CY", "FK", "BO", "CL", "GT", "RS", "AF",
            "WF", "BY", "GR", "PL", "VN", "BH", "BD", "EC", "MH", "PH", "US", "HT", "SS", "AQ", "CX", "IL", "YT", "NZ",
            "MP", "VC", "SK", "LK"));
    private static String regUppercaseStartStringValidation = "^[A-Z]*\\s*[A-Za-z]*$";
    private static String regDateValidation = "^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z)$";
    private static String regFloatValidation = "^-*[0-9]{1,2}.[0-9]{1,2}$";
    private static String regDateTimeValidation = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))\\s(\\d{2}:\\d{2}:\\d{2}.\\d{1,3})\\s(\\+\\d{4})\\sUTC$";


    @Test
    public void Countries01_getCountriesTest() {

        Response countriesResponse = CountriesResponse.getCountries();

        /** Verification of successful response status code */
        countriesResponse.then().assertThat().statusCode(HttpStatus.SC_OK);

        /** Verification of JSON object content */
        Iterator<Object> countries = new JSONArray(countriesResponse.getBody().asString()).iterator();
        while(countries.hasNext()){
            Object country = countries.next();
            assertThat(listOfCoutries.contains(new JsonPath(country.toString()).getString("Country")), equalTo(true));
            assertThat(listOfSlugs.contains(new JsonPath(country.toString()).getString("Slug")), equalTo(true));
            assertThat(listOfIso.contains(new JsonPath(country.toString()).getString("ISO2")), equalTo(true));
        }
    }

    @Test
    public void Countries02_getWebhookTest() {

        Response shoutResponse = CountriesResponse.postWebhook(CountriesRequest.getWebhookBody("https://wh.ksred.me"));

        /** Verification of successful response status code */
        shoutResponse.then().assertThat().statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void Countries03_getConfirmedByOneDayTest() {

        Response confirmedDayOneResponse = CountriesResponse.getDayOneConfirmed("united-kingdom");

        /** Verification of successful response status code */
        confirmedDayOneResponse.then().assertThat().statusCode(HttpStatus.SC_OK);
        Iterator<Object> provinces = new JSONArray(confirmedDayOneResponse.getBody().asString()).iterator();
        while(provinces.hasNext()){
            Object province = provinces.next();
            System.out.println("Province:" + new JsonPath(province.toString()).getString("Province"));
            assertThat(new JsonPath(province.toString()).getString("Country"), equalTo("United Kingdom"));
            assertThat(new JsonPath(province.toString()).getString("CountryCode"), equalTo("GB"));
            assertThat(new JsonPath(province.toString()).getString("City"), equalTo(""));
            assertThat(new JsonPath(province.toString()).getString("CityCode"), equalTo(""));
            assertThat(new JsonPath(province.toString()).getString("Lat").matches(regFloatValidation), equalTo(true));
            assertThat(new JsonPath(province.toString()).getString("Lon").matches(regFloatValidation), equalTo(true));
            assertThat(new JsonPath(province.toString()).getInt("Cases"), not(0));
            assertThat(new JsonPath(province.toString()).getString("Status"), equalTo("confirmed"));
            assertThat(new JsonPath(province.toString()).getString("Date").matches(regDateValidation), equalTo(true));
        }
    }

    @Test
    public void Countries04_getConfirmedByCountriesTest() {

        Response confirmedByCountryResponse = CountriesResponse.getByCountryConfirmed("spain");

        /** Verification of successful response status code */
        confirmedByCountryResponse.then().assertThat().statusCode(HttpStatus.SC_OK);
        Iterator<Object> provinces = new JSONArray(confirmedByCountryResponse.getBody().asString()).iterator();
        while(provinces.hasNext()){
            Object province = provinces.next();
            System.out.println("Province:" + new JsonPath(province.toString()).getString("Province"));
            assertThat(new JsonPath(province.toString()).getString("Country"), equalTo("Spain"));
            assertThat(new JsonPath(province.toString()).getString("CountryCode"), equalTo("ES"));
            assertThat(new JsonPath(province.toString()).getString("City"), equalTo(""));
            assertThat(new JsonPath(province.toString()).getString("CityCode"), equalTo(""));
            assertThat(new JsonPath(province.toString()).getString("Lat").matches(regFloatValidation), equalTo(true));
            assertThat(new JsonPath(province.toString()).getString("Lon").matches(regFloatValidation), equalTo(true));
            assertThat(new JsonPath(province.toString()).getInt("Cases"), not(0));
            assertThat(new JsonPath(province.toString()).getString("Status"), equalTo("confirmed"));
            assertThat(new JsonPath(province.toString()).getString("Date").matches(regDateValidation), equalTo(true));
        }
    }

    @Test
    public void Countries05_getStatsTest() {

        Response statsResponse = CountriesResponse.getStats();

        /** Verification of successful response status code */
        statsResponse.then().assertThat().statusCode(HttpStatus.SC_OK);

        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("Total"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("All"), equalTo(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("AllUpdated"), equalTo(""));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("Countries"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("CountriesUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("ByCountry"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("ByCountryUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("ByCountryAllStatus"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("ByCountryAllStatusUpdated"), equalTo(""));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("ByCountryLive"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("ByCountryLiveUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("ByCountryTotal"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("ByCountryTotalUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("ByCountryTotalAllStatus"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("ByCountryTotalAllStatusUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("DayOne"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("DayOneUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("DayOneLive"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("DayOneLiveUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("DayOneTotal"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("DayOneTotalUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("DayOneAllStatus"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("DayOneAllStatusUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("CountryDayOneTotalAllStatus"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("CountryDayOneTotalAllStatusUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("LiveCountryStatus"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("LiveCountryStatusUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("LiveCountryStatusAfterDate"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("LiveCountryStatusAfterDateUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("WorldTotal"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("WorldTotalDateUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("WorldDaily"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("WorldDailyDateUpdated"), equalTo(""));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("Stats"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("StatsUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("Default"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("DefaultUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("SubmitWebhook"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("SubmitWebhookUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("Summary"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("SummaryUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("PremiumCountry"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("PremiumCountryUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("PremiumSummaryCountry"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("PremiumSummaryCountryUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("PremiumCountryData"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("PremiumCountryDataUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("PremiumCountryTests"), not(0));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getString("PremiumCountryTestsUpdated").matches(regDateTimeValidation), equalTo(true));
        assertThat(new JSONObject(statsResponse.getBody().asString()).getInt("PremiumTravelAdvice"), not(0));
        assertThat(new JsonPath(statsResponse.getBody().asString()).getString("PremiumTravelAdviceUpdated").matches(regDateTimeValidation), equalTo(true));
    }
}
