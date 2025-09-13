package miku;

import miku.util.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTaskTest {
    @Test
    public void BasicUsageTest() {
        MikuBot bot = new MikuBot();
        bot.clearSave();

        String output = bot.runCommand("deadline task /by 16/9/2025 1800");
        assertEquals("Miku has added this task to your list!\n" +
                Constants.INDENT + "[D][ ] task (by: Sep 16 2025 6:00pm)\n" +
                "You now have 1 task in your list", output);
    }

    @Test
    public void BadDateTest() {
        MikuBot bot = new MikuBot();
        bot.clearSave();

        String output = bot.runCommand("deadline task /by asasas");
        assertEquals("Date parsing failed :(", output);
    }

    @Test
    public void EmptyTaskTest() {
        MikuBot bot = new MikuBot();
        bot.clearSave();

        String output = bot.runCommand("deadline");
        assertEquals("Miku cannot add an empty task :(\n" +
                Constants.INDENT + "Usage: deadline <task> /by <time>", output);
    }
}
