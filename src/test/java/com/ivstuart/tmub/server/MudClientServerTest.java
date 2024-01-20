package com.ivstuart.tmub.server;

import com.ivstuart.tmud.client.LaunchClient;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.server.MudServer;
import com.ivstuart.tmud.world.World;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MudClientServerTest {

    private static MudServer mudServer;

    private LaunchClient mudClient;

    @Before
    public void setUp() throws Exception {

        if (mudServer == null) {
            System.out.println("MudServer null so creating a new one");
            Thread thread = new Thread(new RunLaunchMud());
            thread.start();
            mudServer = MudServer.getInstance();
        }

        while (!mudServer.isReady()) {
            Thread.sleep(200);
            System.out.println("Test sleeping....");
        }

        if (mudClient == null) {
            System.out.println("MudClient is null so creating a new one");
            mudClient = new LaunchClient();
            mudClient.start();

        }
        Thread.sleep(100);

    }

    @After
    public void tearDown() throws Exception {
        mudClient.close();
        mudClient = null;
    }

    @Test
    public void testCreateNewCharacter() throws InterruptedException {

        mudClient.send("2");

        Assert.assertEquals("Check login menu server response prompt", "Choose your character name:", mudClient.getLastResponseWithWait());

        mudClient.send("Ivan");

        // You can not create a player with that name it is already taken
        Assert.assertEquals("Check name server response prompt", "You can not create a player with that name it is already taken", mudClient.getLastResponseWithWait());

        /**
         Assert.assertEquals("Check name server response prompt", "Gender? Male / Female / Neutral:", mudClient.getLastResponseWithWait());

         mudClient.send("Male");

         Assert.assertEquals("Check name server response prompt", "", mudClient.getLastResponseWithWait());

         mudClient.send("emailaddress@fake.com");

         Assert.assertEquals("Check name server response prompt", "", mudClient.getLastResponseWithWait());

         mudClient.send("1"); // Human

         Assert.assertEquals("Check name server response prompt", "[STRENGTH][CONSTITUTION][INTELLIGENCE][DEXTERITY][WISDOM]", mudClient.getLastResponseWithWait());

         mudClient.send("18 18 18 18 18"); // Attributes

         Assert.assertEquals("Check name server response prompt", "Created character. Check your email for login inputPassword", mudClient.getLastResponseWithWait());

         mudClient.send("foo");

         Thread.sleep(500);
         mudClient.send("foo");
         */
    }

    @Test
    public void testLogin() {

        mudClient.send("1");

        Assert.assertEquals("Check name server response prompt", "Name:", mudClient.getLastResponseWithWait());


    }

    @Test
    public void testEnterWorld() throws InterruptedException {

        mudClient.send("1");

        Assert.assertEquals("Check name server response prompt", "Name:", mudClient.getLastResponseWithWait());

        mudClient.send("Ivan");

        Assert.assertEquals("Check name server response prompt", "Password:", mudClient.getLastResponseWithWait());

        mudClient.send("masterkey1234");

        Assert.assertEquals("Check password server response prompt", "Logging in...", mudClient.getLastResponseWithWait());

        mudClient.send("look");

        String response = mudClient.getLastResponseWithWait();
        String expected = "(0)";

        boolean result = response.endsWith(expected);

        Assert.assertTrue("Check look prompt", result);

        mudClient.send("quit");


        Assert.assertEquals("Check quit response from server", "Thank you for playing", mudClient.getLastResponseWithWait());

        mudClient.send("look"); // should get an error message on the client

    }

    @Test
    public void testPlayerTimeout() throws InterruptedException {

        mudClient.send("1");

        Assert.assertEquals("Check name server response prompt", "Name:", mudClient.getLastResponseWithWait());

        mudClient.send("Ivan");

        Assert.assertEquals("Check name server response prompt", "Password:", mudClient.getLastResponseWithWait());

        mudClient.send("masterkey1234");

        Assert.assertEquals("Check password server response prompt", "Logging in...", mudClient.getLastResponseWithWait());

        mudClient.send("look");

        String response = mudClient.getLastResponseWithWait();
        String expected = "(0)";

        boolean result = response.endsWith(expected);

        Assert.assertTrue("Check look prompt", result);

        // Set session to 5 mintues ago
        Player ivan = World.getPlayer("ivan");
        ivan.getConnection().setTimeLastRead(System.currentTimeMillis() - 800000);
        ivan.getMob().tick();

        Thread.sleep(60);
        mudClient.send("look");
        Thread.sleep(60);
        mudClient.send("look");

        boolean isRunning = mudClient.isRunning();

        Assert.assertTrue("Check client has closed", !isRunning);
    }

    @Test
    public void testEnterWorldThirdTime() throws InterruptedException {

        mudClient.send("1");

        Assert.assertEquals("Check name server response prompt", "Name:", mudClient.getLastResponseWithWait());

        mudClient.send("Ste");

        Assert.assertEquals("Check name server response prompt", "Password:", mudClient.getLastResponseWithWait());

        mudClient.send("masterkey1234");

        Assert.assertEquals("Check password server response prompt", "Logging in...", mudClient.getLastResponseWithWait());

        mudClient.send("look");

        String response = mudClient.getLastResponseWithWait();
        String expected = "(7875980)";

        boolean result = response.endsWith(expected);

        Assert.assertTrue("Check look prompt", result);
    }

}
