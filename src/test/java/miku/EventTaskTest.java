package miku;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import miku.util.Constants;

public class EventTaskTest {
    @Test
    public void basicUsageTest() {
        MikuBot bot = new MikuBot();
        bot.clearSave();

        String output = bot.runCommand("event task /from 16/9/2025 1800 /to 2025/10/12 7:00pm");
        assertEquals("Miku has added this task to your list!\n"
                + Constants.INDENT + "[E][ ] task (from: Sep 16 2025 6:00pm to: Oct 12 2025 7:00pm)\n"
                + "You now have 1 task in your list", output);
    }

    @Test
    public void badDateTest() {
        MikuBot bot = new MikuBot();
        bot.clearSave();

        String output = bot.runCommand("event task /from asasas /to sdsdsd");
        assertEquals("Failed to parse date: asasas", output);
    }

    @Test
    public void emptyTaskTest() {
        MikuBot bot = new MikuBot();
        bot.clearSave();

        String output = bot.runCommand("event");
        assertEquals("Miku cannot add an empty task :(\n"
                + Constants.INDENT
                + "Usage: event <task> /from <time> /to <time>", output);
    }
}
