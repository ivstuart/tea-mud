/*
 * Created on 04-Oct-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.server;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.constants.AttributeType;
import com.ivstuart.tmud.exceptions.MudException;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.person.PlayerData;
import com.ivstuart.tmud.person.carried.Money;
import com.ivstuart.tmud.person.statistics.MobMana;
import com.ivstuart.tmud.state.Attribute;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.World;
import com.ivstuart.tmud.state.util.EntityProvider;
import com.ivstuart.tmud.utils.GsonIO;
import com.ivstuart.tmud.utils.MudIO;

/**
 * @author stuarti
 * 
 */
public class Login implements Readable {

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

			createCharacter();

			loginState = null;
		}
	}

	private class ChooseEmail implements Readable {

		public ChooseEmail() {
			out("You may have 1 character at most at any given time on this mud.\n"
					+ "A password will be sent to you by e-mail.\n"
					+ "You MUST fill in a correct e-mail address to get sent a password, to play.\n"
					+ "What email address should I send your character password to:\n");
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
			// TODO String password = line;
			loadCharacter();
			loginState = null;
		}

	}

	private class MainMenu implements Readable {

		public MainMenu() {
			displayMenu();
		}

		public void displayMenu() {
			out("1. Login\n" + "2. Create character\n" + "3. Guest login\n"
					+ "4. Quit\n");
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
			default:
				out("Invalid selection please choose from the following:");
				displayMenu();
				break;

			}
		}
	}

	private static final Logger LOGGER = Logger.getLogger(Login.class);

	public static Item getItemClone(String id_) {
		return EntityProvider.createItem(id_);
	}

	private int att[] = new int[5];

	private int choice;

	private String email;

	private String gender;

	private Readable loginState;

	private Connection myConnection;

	private String name;

	/**
	 * @param connection
	 */
	public Login(Connection connection) {
		myConnection = connection;
		welcome();
		loginState = new MainMenu();
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
			// TODO remove this
			// MudIO.getInstance().save(player, player.getName() + ".sav");
			GsonIO gio = new GsonIO();
			gio.save(player, player.getName() + ".sav");
		} catch (IOException e) {
			LOGGER.error("Problem saving character to disk", e);
		}

		out("Created character. Check your email for login password");
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

		// Str Con Dex Int Wis - Set
		player.setAttributes(attributes);

		mob.setGender(gender);

		mob.setRace(RaceData.RACE_NAME[choice]);

		Attribute alignment = new Attribute("Alignment", -5000, 5000);

		if (choice % 2 == 1) {
			alignment.setValue(1000);
		} else {
			alignment.setValue(-1000);
		}

		player.getData().setAlignment(alignment);

		mob.setHeight(6);

		data.setPlayingFor(0);

		data.setTotalXp(0);
		data.setRemort(0);
		data.setPracs(0);

		mob.setLevel(1);

		data.setAge(16 + (int) (Math.random() * 5));

		mob.setHp("10");
		mob.setMv("10");
		mob.setMana(new MobMana(true));

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
		item = Login.getItemClone("fire-40");
		player.getMob().getInventory().add(item);
		item = Login.getItemClone("water-20");
		player.getMob().getInventory().add(item);
		item = Login.getItemClone("air-60");
		player.getMob().getInventory().add(item);
		item = Login.getItemClone("earth-80");
		player.getMob().getInventory().add(item);

		player.getMob().getInventory().add(new Money(Money.COPPER, 50));

		/**
		 * You are carrying: 50 copper coins. A wooden torch A waterskin (full
		 * of a clear liquid) A small tinder-box A wooden training cudgel
		 * (pristine condition) A wooden training dagger (pristine condition)
		 * The EverWar Mud players guide
		 */
	}

	private boolean isValidName(String name) {
		return !World.getPlayerNames().contains(name);
	}

	private void loadCharacter() {

		Player player = null;

		try {
//			player = (Player) MudIO.getInstance().load(name + ".sav");
//			
			String fileName = name + ".sav";
			GsonIO gio = new GsonIO();
			player = gio.loadPlayer(fileName);
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

		String password = "temp";
		if (player.getData().getPassword().matches(password)) {
			out("Password correct");
		} else {
			out("Password incorrect");
			// TODO drop connection and log ip address?
		}
		out("logging in");

		/* Room object needs to be taken from world hash */

		Mob character = player.getMob();

		// Null pointer ! System.out.println("Alignment =" +
		// character.getStats().get("ALIGNMENT").getValue());

		Room oldRoom = character.getRoom();

		String roomId = null;

		if (oldRoom == null) {
			roomId = "R-P2";
		} else {
			roomId = oldRoom.getId();
		}

		Room newRoom = World.getRoom(roomId);

		if (newRoom == null) {
			System.out.println("Could not find players room!");
			newRoom = World.getRoom("R-P2");
		}

		try {
			World.addPLayer(character);
		} catch (MudException e) {
			LOGGER.error("Problem adding player to world", e);
			return;
		}

		character.setRoom(newRoom);

		newRoom.add(character);

		myConnection.setState(new Playing(player));

		loginState = null;

	}

	private void out(String message) {
		myConnection.out(message);
	}

	@Override
	public void read(String line) {
		// TODO work out how the multi-threading...
		if (loginState != null) {
			loginState.read(line);
		}

	}

	private void welcome() {
		out("--------------------------\n" + "Welcome to Rite Of Balance\n"
				+ "--------------------------\n" + "Credits:\n\n"
				+ "Code Base: Ivan Stuart\n" + "--------------------------\n");
	}

}