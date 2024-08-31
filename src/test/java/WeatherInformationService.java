import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherInformationService {
    private static final String API_KEY = "dab33d567b7f71cb9e56e4daf3c48bab";
    private static final String API_URL = "http://api.weatherstack.com/current?access_key=" + API_KEY + "&query=";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter city or location: ");
        String city = input.nextLine();

        try {
            String weatherInfo = getWeather(city);
            System.out.println(weatherInfo);
        } catch (Exception e) {
            System.err.println("Error fetching weather information: " + e.getMessage());
        } finally {
            input.close();
        }
    }

    public static String getWeather(String city) throws Exception {

        String urlString = API_URL + city.replace(" ", "%20");
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        JSONObject jRes = new JSONObject(response.toString());
        JSONObject currentCity = jRes.getJSONObject("current");
        Integer temperature = currentCity.getInt("temperature");

        String weatherDescr = currentCity.getJSONArray("weather_descriptions").getString(0);

        return String.format("Weather in: %s\nTemperature: %s°C\nDescription: %s", city, temperature, weatherDescr);
    }
}
