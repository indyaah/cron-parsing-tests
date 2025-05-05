package pro.anuj.skunkworks.crons;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.quartz.CronExpression;

class CronTests {

    private final CronExpression cronExpression = new CronExpression("* * 22-1 * * ?");

    CronTests() throws ParseException {
    }


    @ParameterizedTest(name = "{index} => input={0}, expected={1}")
    @CsvSource({
            "'2025-05-05 11:00:00', false",
            "'2025-05-05 12:00:00', false",
            "'2025-05-05 13:00:00', false",
            "'2025-05-05 14:00:00', false",
            "'2025-05-05 15:00:00', false",
            "'2025-05-05 16:00:00', false",
            "'2025-05-05 17:00:00', false",
            "'2025-05-05 18:00:00', false",
            "'2025-05-05 19:00:00', false",
            "'2025-05-05 20:00:00', false",
            "'2025-05-05 21:00:00', false",
            "'2025-05-05 22:00:00', true",
            "'2025-05-05 23:00:00', true",
            "'2025-05-05 00:00:00', true",
            "'2025-05-05 01:00:00', true",
            "'2025-05-05 01:59:59', true",
            "'2025-05-05 02:00:00', false",
            "'2025-05-05 03:00:00', false"
    })
    void testCrons(String input, boolean expected) throws ParseException {
        // parse input to date with format yyyy-MM-dd HH:mm:ss without using SimpleDateFormat
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
        Date from = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        assert cronExpression.isSatisfiedBy(from) == expected;

    }
}
