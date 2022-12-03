import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd MM yyyy");
        String inputDateTime = "22:00:00 12 12 2022";
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, dtf);

        long secondsLeft = Duration.between(LocalDateTime.now(), dateTime).toSeconds();
        
        new Frame(secondsLeft, 1600, 900);
    }
}
