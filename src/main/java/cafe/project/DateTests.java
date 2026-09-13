package cafe.project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTests {

	public static void main(String[] args) {
		LocalDateTime datetime = LocalDateTime.now();
		LocalTime time = LocalTime.now();
		LocalDate date = LocalDate.now();
		
		System.out.println(datetime);
		System.out.println(time);
		System.out.println(date);
	}

}
