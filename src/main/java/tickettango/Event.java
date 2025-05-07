package tickettango;

/**
 * Represents an event in the TicketTango system
 */
public class Event {
    private String eventId;
    private String name;
    private String location;
    private String date;
    private int availableTickets;
    private double price;
    private String imageUrl;
    private String description;
    
    /**
     * Constructor for Event
     * 
     * @param name Event name
     * @param location Event location
     * @param date Event date
     * @param availableTickets Number of available tickets
     * @param price Ticket price
     */
    public Event(String name, String location, String date, int availableTickets, double price) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.availableTickets = availableTickets;
        this.price = price;
    }
    
    /**
     * Constructor with full details including image and description
     * 
     * @param eventId Unique event ID
     * @param name Event name
     * @param location Event location
     * @param date Event date
     * @param availableTickets Number of available tickets
     * @param price Ticket price
     * @param imageUrl URL to event image
     * @param description Event description
     */
    public Event(String eventId, String name, String location, String date, 
                int availableTickets, double price, String imageUrl, String description) {
        this.eventId = eventId;
        this.name = name;
        this.location = location;
        this.date = date;
        this.availableTickets = availableTickets;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
    }
    
    // Getters and setters
    public String getEventId() {
        return eventId;
    }
    
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getLocation() {
        return location;
    }
    
    public String getDate() {
        return date;
    }
    
    public int getAvailableTickets() {
        return availableTickets;
    }
    
    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }
    
    public double getPrice() {
        return price;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Compare events for equality based on eventId
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Event other = (Event) obj;
        if (eventId == null && other.eventId == null) {
            // If both have null IDs, compare based on name, location, and date
            return name.equals(other.name) && 
                   location.equals(other.location) && 
                   date.equals(other.date);
        }
        
        return eventId != null && eventId.equals(other.eventId);
    }
    
    /**
     * HashCode implementation to match equals
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
        if (eventId == null) {
            result = prime * result + name.hashCode();
            result = prime * result + location.hashCode();
            result = prime * result + date.hashCode();
        }
        return result;
    }
    
    /**
     * String representation of the event
     */
    @Override
    public String toString() {
        return "Event[id=" + eventId + ", name=" + name + ", location=" + location + ", date=" + date + "]";
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Reduce available tickets when purchased
     * 
     * @param quantity Number of tickets to purchase
     * @return true if successful, false if not enough tickets
     */
    public boolean purchaseTickets(int quantity) {
        if (quantity <= availableTickets) {
            availableTickets -= quantity;
            return true;
        }
        return false;
    }
}
