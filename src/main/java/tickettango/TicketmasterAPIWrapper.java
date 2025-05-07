package tickettango;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Wrapper for the Ticketmaster API to fetch event data
 */
public class TicketmasterAPIWrapper {
    // Ticketmaster API credentials
    private static final String API_KEY = "P9HyDTYvePvojSsTGNtdegJ70m7beIXM";
    private static final String BASE_URL = "https://app.ticketmaster.com/discovery/v2/events.json";
    
    /**
     * Constructor for TicketmasterAPIWrapper
     */
    public TicketmasterAPIWrapper() {
        // Initialization if needed
    }
    
    /**
     * Get upcoming events from Ticketmaster
     * 
     * @param limit Maximum number of events to return
     * @return List of events
     * @throws Exception if API call fails
     */
    public ArrayList<Event> getUpcomingEvents(int limit) throws Exception {
        String url = BASE_URL + "?apikey=" + API_KEY + "&size=" + limit;
        return sendRequest(url);
    }
    
    /**
     * Search for events by keyword
     * 
     * @param keyword Search keyword
     * @param limit Maximum number of events to return
     * @return List of matching events
     * @throws Exception if API call fails
     */
    public ArrayList<Event> searchEvents(String keyword, int limit) throws Exception {
        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.toString());
        String url = BASE_URL + "?apikey=" + API_KEY + "&keyword=" + encodedKeyword + "&size=" + limit;
        ArrayList<Event> results = sendRequest(url);
        
        // If we have events directly from the API, return them
        if (!results.isEmpty()) {
            return results;
        }
        
        // Fallback for exact title matching - search our existing events
        // This simulates a more comprehensive search that would prioritize title matches
        ArrayList<Event> titleMatches = new ArrayList<>();
        
        try {
            // First get all events
            ArrayList<Event> allEvents = getUpcomingEvents(100);
            
            // Filter for title matches
            for (Event event : allEvents) {
                if (event.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    titleMatches.add(event);
                }
            }
        } catch (Exception ex) {
            // If fallback fails, just return original (empty) results
            System.out.println("Fallback search failed: " + ex.getMessage());
        }
        
        return titleMatches.isEmpty() ? results : titleMatches;
    }
    
    /**
     * Search for events by location
     * 
     * @param city City name
     * @param limit Maximum number of events to return
     * @return List of matching events
     * @throws Exception if API call fails
     */
    public ArrayList<Event> searchByLocation(String city, int limit) throws Exception {
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
        String url = BASE_URL + "?apikey=" + API_KEY + "&city=" + encodedCity + "&size=" + limit;
        return sendRequest(url);
    }
    
    /**
     * Send API request and parse response
     * 
     * @param urlString URL for the API request
     * @return List of events from response
     * @throws Exception if request fails
     */
    private ArrayList<Event> sendRequest(String urlString) throws Exception {
        ArrayList<Event> events = new ArrayList<>();
        
        // Create URL and open connection
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        // Read response
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        
        // Parse JSON response
        JSONObject jsonResponse = new JSONObject(response.toString());
        if (jsonResponse.has("_embedded") && jsonResponse.getJSONObject("_embedded").has("events")) {
            JSONArray jsonEvents = jsonResponse.getJSONObject("_embedded").getJSONArray("events");
            
            // Process each event
            for (int i = 0; i < jsonEvents.length(); i++) {
                JSONObject jsonEvent = jsonEvents.getJSONObject(i);
                
                // Extract event details
                String eventId = jsonEvent.getString("id");
                String name = jsonEvent.getString("name");
                
                // Get date
                String date = "TBD";
                if (jsonEvent.has("dates") && jsonEvent.getJSONObject("dates").has("start")) {
                    JSONObject startDate = jsonEvent.getJSONObject("dates").getJSONObject("start");
                    if (startDate.has("localDate")) {
                        date = startDate.getString("localDate");
                    }
                }
                
                // Get venue/location
                String location = "Various Locations";
                if (jsonEvent.has("_embedded") && jsonEvent.getJSONObject("_embedded").has("venues")) {
                    JSONArray venues = jsonEvent.getJSONObject("_embedded").getJSONArray("venues");
                    if (venues.length() > 0) {
                        JSONObject venue = venues.getJSONObject(0);
                        if (venue.has("name")) {
                            String venueName = venue.getString("name");
                            String city = "";
                            
                            if (venue.has("city") && venue.getJSONObject("city").has("name")) {
                                city = venue.getJSONObject("city").getString("name");
                            }
                            
                            if (!city.isEmpty()) {
                                location = venueName + ", " + city;
                            } else {
                                location = venueName;
                            }
                        }
                    }
                }
                
                // Get price and availability
                double price = 0.0;
                if (jsonEvent.has("priceRanges") && jsonEvent.getJSONArray("priceRanges").length() > 0) {
                    JSONObject priceRange = jsonEvent.getJSONArray("priceRanges").getJSONObject(0);
                    if (priceRange.has("min")) {
                        price = priceRange.getDouble("min");
                    }
                } else {
                    // Default price if not specified
                    price = 49.99;
                }
                
                // Randomize available tickets between 10 and 100
                int availableTickets = 10 + (int) (Math.random() * 90);
                
                // Get image URL
                String imageUrl = "";
                if (jsonEvent.has("images") && jsonEvent.getJSONArray("images").length() > 0) {
                    JSONObject image = jsonEvent.getJSONArray("images").getJSONObject(0);
                    if (image.has("url")) {
                        imageUrl = image.getString("url");
                    }
                }
                
                // Get description
                String description = "Join us for this amazing event!";
                if (jsonEvent.has("info")) {
                    description = jsonEvent.getString("info");
                } else if (jsonEvent.has("description")) {
                    description = jsonEvent.getString("description");
                }
                
                // Create event and add to list
                Event event = new Event(eventId, name, location, date, 
                                       availableTickets, price, imageUrl, description);
                events.add(event);
            }
        }
        
        return events;
    }
}
