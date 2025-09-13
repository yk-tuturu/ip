package miku;

import miku.util.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void BasicTest(){
        MikuBot bot = new MikuBot();
        bot.clearSave();

        String output = bot.runCommand("todo task");
        assertEquals("Miku has added this task to your list!\n" +
                        "    [T][ ] task\n" +
                        "You now have 1 task in your list", output);
    }

    @Test
    public void emptyTaskTest(){
        MikuBot bot = new MikuBot();
        bot.clearSave();

        String output = bot.runCommand("todo");
        assertEquals("Miku cannot add an empty task!\n" + Constants.INDENT + "Usage: todo <task>",
                output);
    }
}
