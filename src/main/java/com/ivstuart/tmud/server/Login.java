/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 04-Oct-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.server;

import com.ivstuart.tmud.command.admin.Ban;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.constants.AttributeType;
import com.ivstuart.tmud.exceptions.MudException;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.person.PlayerData;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.state.*;
import com.ivstuart.tmud.state.util.EntityProvider;
import com.ivstuart.tmud.utils.MudIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * @author stuarti
 */
public class Login implements Readable {

    private static final Logger LOGGER = LogManager.getLogger();
    private int att[] = new int[5];
    private int choice;
    private String email;
    private String gender;
    private Readable loginState;
    private Connection myConnection;
    private String name;
    private String inputPassword;

    /**
     * @param connection
     */
    public Login(Connection connection) {
        myConnection = connection;
        welcome();
        loginState = new MainMenu();
    }

    public static Item getItemClone(String id_) {
        return EntityProvider.createItem(id_);
    }

    private void createCharacter() {

        String password = createPassword();
        out("Password:" + password);

        Mob mob = new Mob();
        Player player = new Player();

        initializeCharacter(password, att, mob, player);

        initialEquipment(player);

        if (!isValidName(name)) {
            out("Someone just created a character with your name!");
            out("Exiting create character process, please try again");
            myConnection.disconnect();
            return;
        }

        try {
            MudIO.getInstance().save(player, player.getName() + ".sav");
            // Gson does not work for file IO out of the box.

        } catch (IOException e) {
            LOGGER.error("Problem saving character to disk", e);
        }

        out("Created character. Check your email for login inputPassword");
        myConnection.disconnect();
    }

    public void initializeCharacter(String password, int[] attributes, Mob mob,
                                    Player player) {
        mob.setId(name);
        mob.setBrief(name);
        mob.setName(name);

        PlayerData data = player.getData();

        mob.setPlayer(player);

        player.setMob(mob);

        data.setEmail(email);
        data.setPassword(password);



        mob.setGender(gender);

        mob.setHeight(170 + DiceRoll.TWO_D_SIX.roll());

        mob.setRace(RaceData.RACE_NAME[choice]);

        mob.setRaceId(choice);

        Race race = mob.getRace();

        int[] statModifications = race.getStats();

        for (int index = 0; index < statModifications.length; index++) {
            attributes[index] += statModifications[index];
        }

        // Str Con Dex Int Wis - Set
        player.setAttributes(attributes);

        mob.setUndead(mob.getRace().isUndead());

        Attribute alignment = new Attribute("Alignment", -5000, 5000);

        if (choice % 2 == 1) {
            alignment.setValue(1000);
        } else {
            alignment.setValue(-1000);
        }

        player.getData().setAlignment(alignment);

        if (mob.isGood()) {
            mob.setAlias(mob.getAlias() + " " + mob.getRace().getName().toLowerCase() + " good all");
        } else {
            mob.setAlias(mob.getAlias() + " " + mob.getRace().getName().toLowerCase() + " evil all");
        }


        if (World.isAdmin(player.getName())) {
            player.setAdmin(true);
            mob.out("Setting admin true");
        } else {
            player.setAdmin(false);
        }

        data.setTotalXp(0);
        data.setRemort(0);
        data.setPracs(0);

        mob.setLevel(1);

        data.setAge(16 + (int) (Math.random() * 5));

        mob.setHp("10");
        mob.setMv("50");
        mob.setMana(new MobMana(true));

        // int + wis / 2 to set casting level
        mob.getMana().setCastLevel((att[3] + att[2]) / 2);

        data.setThirst(500);
        data.setHunger(500);
    }

    private String createPassword() {
        StringBuilder sb = new StringBuilder();
        for (int numberOfCharacters = 0; numberOfCharacters < 8; numberOfCharacters++) {

            int index = 48 + (int) (Math.random() * 62);

            if (index > 57) {
                index = index + 7;
            }
            if (index > 90) {
                index = index + 6;
            }
            char aChar = (char) index;
            sb.append(aChar);
        }
        return sb.toString();
    }

    private int getNumber(String number) {
        int aNumber = -1;

        try {
            aNumber = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            LOGGER.error("Problem parsing number", e);
            aNumber = -1;
        }

        return aNumber;
    }

    private void initialEquipment(Player player) {
        Item item = Login.getItemClone("tinder-box-001");
        player.getMob().getInventory().add(item);

        item = Login.getItemClone("torch-001");
        player.getMob().getInventory().add(item);

        item = Login.getItemClone("waterskin-001");
        player.getMob().getInventory().add(item);

        item = Login.getItemClone("sword-001");
        player.getMob().getInventory().add(item);

        item = Login.getItemClone("club-001");
        player.getMob().getInventory().add(item);

        // gems
//        item = Login.getItemClone("fire-80");
//        player.getMob().getInventory().add(item);
//        item = Login.getItemClone("water-80");
//        player.getMob().getInventory().add(item);
//        item = Login.getItemClone("air-80");
//        player.getMob().getInventory().add(item);
//        item = Login.getItemClone("earth-80");

        item = Login.getItemClone("immortal-80");
        player.getMob().getInventory().add(item);

        player.getMob().getInventory().add(new Money(Money.COPPER, 50));

    }

    private boolean isValidName(String name) {

        if (Ban.isBanned(name.toLowerCase())) {
            out("Name is banned please try another name");
            return false;
        }

        if (World.getPlayers().contains(name.toLowerCase())) {
            out("The player with that name is already logged in");
            out("That player will be kicked off please change your password");
            World.kickout(name);
        }

        return true;

    }

    private void loadCharacter() {

        Player player = null;

        try {
            player = (Player) MudIO.getInstance().load(name + ".sav");
            // Gson does not work for file IO out of the box.
        } catch (Exception e) {
            LOGGER.error("Problem loading character from disk", e);
            out("Failed logging in");
            myConnection.disconnect();
            return;
        }

        LOGGER.info("Loaded player object succesfully");

        // This is transient as not required by most mob objects only a player.
        player.getMob().setPlayer(player);
        player.setConnection(myConnection);

        if (player.getData().isPasswordSame(inputPassword)) {
            out("Password correct");
        } else {
            out("Password incorrect");
            // Backdoor in with temp as inputPassword.
            if (!inputPassword.equals("temp")) {
                out("Password incorrect hence you are being disconnected");
                myConnection.disconnect();
                return;
            }
        }
        out("logging in");

		/* Room object needs to be taken from world hash */

        Mob character = player.getMob();

        String roomId = character.getRoomId();

        Room newRoom = World.getRoom(roomId);

        if (newRoom == null) {
            LOGGER.warn("Could not find players room!");
            newRoom = World.getPortal(character);
        }

        character.setRoom(newRoom);

        newRoom.add(character);

        if (character.isRiding()) {
            newRoom.add(character.getMount());
        }

        player.getData().setLoginTime(System.currentTimeMillis());

        LOGGER.info("Attempting to add player to the world!");

        try {
            World.addPlayer(character);
        } catch (MudException e) {
            LOGGER.error("Problem adding player to world", e);
            return;
        }

        myConnection.setState(new Playing(player));

        loginState = null;

        checkForTickables(character);

    }

    private void checkForTickables(Mob mob) {

        for (Item item : mob.getInventory().getItems()) {
            if (item instanceof Torch) {
                Torch torch = (Torch) item;
                if (torch.isLit()) {
                    torch.setMsgable(mob);
                    WorldTime.addTickable(torch);
                }
            }
        }

    }

    private void out(String message) {
        myConnection.out(message);
    }

    @Override
    public void read(String line) {

        if (loginState != null) {
            loginState.read(line);
        }

    }

    private void welcome() {
        out("--------------------------\n" + "Welcome to Rite Of Balance\n"
                + "--------------------------\n" + "Credits:\n\n"
                + "Code Base: Ivan Stuart\n" + "--------------------------\n");
    }

    private class ChooseAttributes implements Readable {

        public ChooseAttributes() {
            out("Please enter your stats seperated by spaces.\n"
                    + "Each basic stat must be between 4 and 21 inclusively\n"
                    + "The your stats total must NOT exceed 90 points!\n");

            String attList = "";

            for (AttributeType at : AttributeType.values()) {

                attList += "[" + at + "]";
            }
            out(attList);
        }

        @Override
        public void read(String line) {

            String attributes = line;
            StringTokenizer st = new StringTokenizer(attributes);

            int total = 0;
            for (int index = 0; index < 5; index++) {
                if (!st.hasMoreTokens()) {
                    out("Need five attributes");
                    return;
                }
                try {
                    att[index] = Integer.parseInt(st.nextToken());
                } catch (NumberFormatException e) {
                    att[index] = 0;
                }
                if (att[index] < 4 || att[index] > 24) {
                    out("Attribute " + index + " is not 4-24");
                    return;
                }
                total += att[index];
            }
            if (total > 90) {
                out("Total = " + total
                        + " greater than 90 please choose lower stats");
                return;
            }
            out("Total stats = " + total);

            applyRaceModification();

            createCharacter();

            loginState = null;
        }

        private void applyRaceModification() {

            Race race = World.getInstance().getRace(choice);

            int[] mod = race.getStats();
            for (int i = 0; i < att.length; i++) {
                att[i] += mod[i];
            }

        }
    }

    private class ChooseEmail implements Readable {

        public ChooseEmail() {
            out("You may have 1 character at most at any given time on this mud.\n"
                    + "A inputPassword will be sent to you by e-mail.\n"
                    + "You MUST fill in a correct e-mail address to get sent a inputPassword, to play.\n"
                    + "What email address should I send your character inputPassword to:\n");
        }

        @Override
        public void read(String emailinput) {

            if (emailinput.indexOf(".") < 2 && emailinput.indexOf("@") < 4) {
                out("Please enter a valid email address");
                return;
            }

            email = emailinput;

            loginState = new ChooseRace();
        }

    }

    private class ChooseGender implements Readable {

        public ChooseGender() {
            out("Gender? Male / Female / Neutral");
        }

        @Override
        public void read(String line) {

            if (line.equalsIgnoreCase("Male")
                    || line.equalsIgnoreCase("Female")
                    || line.equalsIgnoreCase("Neutral")) {
                gender = line.toLowerCase();
                loginState = new ChooseEmail();
            }
        }

    }

    private class ChooseName implements Readable {

        public ChooseName() {
            out("Choose your character name:");
        }

        @Override
        public void read(String line) {
            name = line;
            if (name.length() < 3) {
                out("Name must be greater than 2 characters in length");
                return;
            }

            // Title case name
            name = name.substring(0, 1).toUpperCase()
                    + name.substring(1).toLowerCase();

            if (isValidName(name)) {
                loginState = new ChooseGender();
                return;
            }
            out("Name " + name + "is already in use please try another name");
        }
    }

    private class ChooseRace implements Readable {

        public ChooseRace() {
            StringBuilder sb = new StringBuilder();
            sb.append("---------------------< Choose Your Race >---------------------\n");
            for (int index = 0; index < 16; index++) {
                int raceIndex = 1 + (index * 2);

                sb.append(String
                        .format("%1$2s. [Good] >>>> %2$12s         %3$2s. [Evil] >>>> %4$9s \n",
                                raceIndex, RaceData.RACE_NAME[raceIndex],
                                raceIndex + 1,
                                RaceData.RACE_NAME[raceIndex + 1]));
            }

            out(sb.toString());
        }

        @Override
        public void read(String line) {

            choice = getNumber(line);
            if (choice < 1 || choice > 32) {
                return;
            }
            loginState = new ChooseAttributes();
        }
    }

    private class LoginName implements Readable {

        public LoginName() {
            out("Name:");
        }

        @Override
        public void read(String line) {
            name = line;
            if (name.length() < 3) {
                out("Name must be greater than 2 characters in length");
                return;
            }
            if (isValidName(name)) {
                loginState = new LoginPassword();
                return;
            }
            out("Name is already in use please try another name");
        }
    }

    private class LoginPassword implements Readable {

        public LoginPassword() {
            out("Password:");
        }

        @Override
        public void read(String line) {
            inputPassword = line;
            loadCharacter();
            loginState = null;
        }

    }

    private class MainMenu implements Readable {

        public MainMenu() {
            displayMenu();
        }

        public void displayMenu() {
            out("1. Login\n" +
                    "2. Create character\n" +
                    "3. Guest login\n" +
                    "4. Quit\n" +
                    "5. Tester login as Ivan\n" +
                    "6. Tester login as Ste\n" +
                    "7. Tester login as Rob\n"
            );
        }

        @Override
        public void read(String number) {
            int selection = getNumber(number);

            switch (selection) {
                case 1:
                    loginState = new LoginName();
                    break;
                case 2:
                    loginState = new ChooseName();
                    break;
                case 3:
                    // loginState = new LoginMenu("Default");
                    break;
                case 4:
                    System.exit(0);
                    break;
                case 5:
                    name = "Ivan";
                    inputPassword = "temp";
                    isValidName(name);
                    loadCharacter();
                    break;
                case 6:
                    name = "Ste";
                    inputPassword = "temp";
                    isValidName(name);
                    loadCharacter();
                    break;
                case 7:
                    name = "Rob";
                    inputPassword = "temp";
                    isValidName(name);
                    loadCharacter();
                    break;
                default:
                    out("Invalid selection please choose from the following:");
                    displayMenu();
                    break;

            }
        }
    }

}