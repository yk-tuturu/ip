package miku;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import miku.util.Constants;

public class TodoTest {
    @Test
    public void basicTest() {
        MikuBot bot = new MikuBot();
        bot.clearSave();

        String output = bot.runCommand("todo task");

        System.out.println(bot.runCommand("list"));

        assertEquals("Miku has added this task to your list!\n"
                + "    [T][ ] task\n"
                + "You now have 1 task in your list", output);
    }

    @Test
    public void emptyTaskTest() {
        MikuBot bot = new MikuBot();
        bot.clearSave();

        String output = bot.runCommand("todo");

        assertEquals("Miku cannot add an empty task!\n"
                        + Constants.INDENT
                        + "Usage: todo <task>", output);
    }
}
