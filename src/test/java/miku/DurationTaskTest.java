package miku;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import miku.util.Constants;

public class DurationTaskTest {
    @Test
    public void basicUsageTest() {
        MikuBot bot = new MikuBot();
        bot.clearSave();

        String output = bot.runCommand("duration task /in 2 hours");
        assertEquals("Miku has added this task to your list!\n"
                + Constants.INDENT + "[F][ ] task (needs 2 hours)\n"
                + "You now have 1 task in your list", output);
    }

    @Test
    public void emptyTaskTest() {
        MikuBot bot = new MikuBot();
        bot.clearSave();

        String output = bot.runCommand("duration");
        assertEquals("Miku cannot add an empty task :(\n"
                + Constants.INDENT
                + "Usage: duration <task> /in <duration>", output);
    }
}
