package nl.hanze.zuul;

import javax.swing.table.TableRowSorter;
import java.util.HashMap;
import java.util.Map;

import static nl.hanze.utils.ItemReader.placeItemsInRooms;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.  Users
 * can walk around some scenery. That's all. It should really be extended
 * to make it more interesting!
 * <p>
 * To play this game, create an instance of this class and call the "play"
 * method.
 * <p>
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game.  It also evaluates and
 * executes the commands that the parser returns.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Game {
    private Parser parser;
    private Room currentRoom;
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        parser = new Parser();
        createPlayer();
        createRooms();
    }

    private void createPlayer() {
        player = new Player(parser.getNameOfPlayer());
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room outside, theater, pub, lab, office;
        TransporterRoom transporterRoom;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        Map<Room, String> rooms = new HashMap<>();

        rooms.put(outside, "outside");
        rooms.put(theater, "theater");
        rooms.put(pub, "pub");
        rooms.put(lab, "lab");
        rooms.put(office, "office");

        transporterRoom = new TransporterRoom("in a magical room, where will we go?", rooms.keySet());


        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);
        pub.setExit("west", transporterRoom);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);


        placeItemsInRooms(rooms);



//        theater.addItem("book", "an old, dusty book bound in gray leather" ,1200,true);
//        theater.addItem("brush", "a very old silver hair brush" ,800,true);
//        theater.addItem("decor", "a nice decor of, it seems to be a Hamlet play (or not) " ,30000, false);


        currentRoom = outside;  // start game outside
    }

    /**
     * Main play routine.  Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome " + player.getName() + " to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.\n");
        System.out.println("You have a backpack in which you can carry items ");
        System.out.println("Your backpack can carry a maximum weight of " + player.getMaxCarrierAmmount() + "\n");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;

            case TAKE:
                takeItem(command);
                break;

            case DROP:
                dropItem(command);
                break;

            case EXPLORE:
                explore(command);
                break;
        }
        return wantToQuit;
    }


    // implementations of user commands:
    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String strItem = command.getSecondWord();
        Item item = player.getBackpackItem(strItem);

        if (null == item) {
            System.out.println("there is no item " + strItem + " in your backpack to drop!" + "\n");
            return;
        }

        if (null != item) {
            currentRoom.addItem(item);
            player.removeItemFromBackPack(item);
            System.out.println("You dropped " + item.getName() + " from your backpack in the room");
        }

    }

    private void explore(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Explore what?");
            System.out.println("At the moment you only can eplore a room or your backback");
            return;
        }
        switch (command.getSecondWord()) {
            case "room":
                System.out.println(currentRoom.getLongDescription());
                System.out.println(currentRoom.printItemsInRoom());
                break;
            case "backpack":
                player.printBackPack();
        }
    }

    private void takeItem(Command command) {

        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        String strItem = command.getSecondWord();

        Item item = currentRoom.getItemByString(strItem);
        if (null == item) {
            System.out.println("there is no item " + strItem + " to take!" + "\n");
            return;
        }
        if (!item.canBePickedUp()) {
            System.out.println(item.getReasonCantBePickedUp());
            return;
        }

        if (player.getBackPackWeight() + item.getWeight() < player.getMaxCarrierAmmount()) {
            System.out.println("You picked up te " + item.getName() + "\n");
            currentRoom.removeItemFromRoom(item);
            player.addItemToBackPack(item);
        } else {
            System.out.println("it seems that your backpack is full. Pleas drop some items to make space! ");
        }
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            if (currentRoom.areItemsLeftInTheRoom()) System.out.println(currentRoom.printItemsInRoom());
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Run the game.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Game().play();
    }
}
