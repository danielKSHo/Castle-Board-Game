// Imports the Scanner class
// Imports the Random class
// Imports the PrintWriter and BufferedReader classes
import java.util.Scanner;
import java.util.Random;
import java.io.*;

//---------------------------------------- */

class Player
{
    String playerName;
    int lives;
    int spacesMoved;
    int turnsRemaining;
    int silverCoins;
    int winningSilverCoins;
}
class Space
{
    int total;
    int [] treasure;
    int [] trapA;
    int [] trapB;
    int [] disableA;
    int [] disableB;
}
class Trap
{
    String trapName;
    boolean switchA;
    boolean switchB;
}

class Stage
{
    String fullName;
    String shortName;
    Space spaceRecord = new Space();
    Trap trapRecord = new Trap();
}
public class Castle_Game_v4
{
    public static void main(String [] a) throws IOException
    {
        sequence();
        return;
    }
    // Method that runs the sequence of methods
    public static void sequence() throws IOException
    {
        Player user = getUser();
        final int [] offStageSpaces = {0, 19, 43, 57, 75, 100};
        while (user.lives > 0)
        {
            user = playCastleGame(user, offStageSpaces);
        }
        if (getPlayerLives(user) >= 0)
        {
            printEndGameMessage(user);
        }
    }
    // --------------------------Basic Methods----------------------------
// Shortens the code to print String messages
    public static void print(String message)
    {
        System.out.println(message);
        return;
    }
    // Copies and prints a String message from the parameters
// Asks for an String
// Returns the String inputted
    public static String inputString(String message)
    {
        Scanner reader = new Scanner(System.in);
        print(message);
        String inputBox = reader.nextLine();
        return inputBox;
    }
    // Copies and prints a String message from the parameters
// Asks for an integer
// Returns the integer inputted
    public static int inputInt(String message)
    {
        Scanner reader = new Scanner(System.in);
        print(message);
        int inputBox = reader.nextInt();
        return inputBox;
    }
// -------------------------------------------------------------------
// ----------------------------------Player␣Methods----------------------------------
    // Creates a record using the Player class
// Assigns given parameters to fields from the record to be stored
    public static Player createPlayer(String playerName, int lives, int spacesMoved,
                                      int turnsRemaining, int silverCoins, int winningSilverCoins)
    {
        Player playerRecord = new Player();
        playerRecord.playerName = playerName;
        playerRecord.lives = lives;
        playerRecord.spacesMoved = spacesMoved;
        playerRecord.turnsRemaining = turnsRemaining;
        playerRecord.silverCoins = silverCoins;
        playerRecord.winningSilverCoins = winningSilverCoins;
        return playerRecord;
    }
    // Accessor method for a name of a player
    public static String getPlayerName(Player playerRecord)
    {
        return playerRecord.playerName;
    }
    // Accessor method for lives of a player
    public static int getPlayerLives(Player playerRecord)
    {
        return playerRecord.lives;
    }
    // Accessor method for spacesMoved of a player
    public static int getPlayerSpacesMoved(Player playerRecord)
    {
        return playerRecord.spacesMoved;
    }
    // Accessor method for turnsRemaining of a player
    public static int getPlayerTurnsRemaining(Player playerRecord)
    {
        return playerRecord.turnsRemaining;
    }
    // Accessor method for silverCoins of a player
    public static int getPlayerSilverCoins(Player playerRecord)
    {
        return playerRecord.silverCoins;
    }
    // Accessor method for winningSilverCoins of a player
    public static int getPlayerWinningSilverCoins(Player playerRecord)
    {
        return playerRecord.winningSilverCoins;
    }
    // Save Player Progress Procedure
    public static void userFileInput(Player playerRecord) throws IOException
    {
        PrintWriter castleGameFile = new PrintWriter(
                new FileWriter("Castle_Game_Save.txt"));
        castleGameFile.println(getPlayerName(playerRecord));
        castleGameFile.println(getPlayerLives(playerRecord));
        castleGameFile.println(getPlayerSpacesMoved(playerRecord));
        castleGameFile.println(getPlayerTurnsRemaining(playerRecord));
        castleGameFile.println(getPlayerSilverCoins(playerRecord));
        castleGameFile.println(getPlayerWinningSilverCoins(playerRecord));
        castleGameFile.close();
        return;
    }
    // Output the Save Menu options
    public static int saveMenu()
    {
        print("");
        print("Do you want to load the old save or create a new save?");
        print("1. Load Old Save");
        print("2. Create New Save");
        print("3. Exit");
        int saveOption = inputInt("Select an option (1 or 3):");
        print("");
        return saveOption;
    }
    // Asks the user for the player name then greets them by name
    public static String addPlayerName()
    {
        String playerName;
        playerName = inputString("What is your name?");
        print("");
        return playerName;
    }
    // Stores specific values within a newly created Player named user
    public static Player getUser() throws IOException
    {
        Player user;
        int saveOption = saveMenu();
// Option 1: Load Old Player
        if (saveOption == 1)
        {
            BufferedReader inputStream = new BufferedReader(
                    new FileReader("Castle_Game_Save.txt"));
            String playerName = inputStream.readLine();
            int lives = Integer.parseInt(inputStream.readLine());
            int spacesMoved = Integer.parseInt(inputStream.readLine());
            int turnsRemaining = Integer.parseInt(inputStream.readLine());
            int silverCoins = Integer.parseInt(inputStream.readLine());
            int winningSilverCoins = Integer.parseInt(inputStream.readLine());
            Player oldUser = createPlayer(playerName, lives, spacesMoved,
                    turnsRemaining, silverCoins, winningSilverCoins);
            print("Welcome back " + getPlayerName(oldUser) + "!!!");
            print("You have " + getPlayerLives(oldUser) + " lives remaining.");
            print("You're on space " + getPlayerSpacesMoved(oldUser) + ".");
            print("You have " + getPlayerTurnsRemaining(oldUser) +
                    " turns remaining for the current stage.");
            print("You hold " + getPlayerSilverCoins(oldUser) +
                    " silver coins at the moment.");
            print("While you were away, the castle guards reset all of the traps");
                    user = oldUser;
        }
// Option 2: Create New Player
        else if (saveOption == 2)
        {
// Get player name
            String playerName = addPlayerName();
            Player newUser = createPlayer(playerName, 6, 0, 9, 0, 20);
            print("Hello " + getPlayerName(newUser) +
                            ", your goal is to collect " + getPlayerWinningSilverCoins(newUser) +
                    " silver coins or to get to the top of the castle alive.");
            print("You start with " + getPlayerLives(newUser) + " lives.");
            user = newUser;
        }
// Option 3: Exit
        else
        {
            user = new Player();
            user.lives = -1;
        }
        print("");
        return user;
    }
// --------------------------------------------------------------------------------
// ----------------------------------Stage Methods---------------------------------
    // Creates a record using the Stage class
// Assigns given parameters to fields from the record to be stored.
    public static Trap createTrap(String trapName,
                                  boolean switchA, boolean switchB)
    {
        Trap trapRecord = new Trap();
        trapRecord.trapName = trapName;
        trapRecord.switchA = switchA;
        trapRecord.switchB = switchB;
        return trapRecord;
    }
    // Creates a record using the Spaces class
// Assigns given parameters to fields from the record to be stored.
    public static Space createSpaces(int total, int [] treasure,
                                     int [] trapA, int [] trapB, int [] disableA, int [] disableB)
    {
        Space spaceRecord = new Space();
        spaceRecord.total = total;
        spaceRecord.treasure = treasure;
        spaceRecord.trapA = trapA;
        spaceRecord.trapB = trapB;
        spaceRecord.disableA = disableA;
        spaceRecord.disableB = disableB;
        return spaceRecord;
    }
    // Creates a record using the Stage class
// Assigns given parameters to fields from the record to be stored.
    public static Stage createStage(String fullName, String shortName, Space spaceRecord, Trap trapRecord)
    {
        Stage stageRecord = new Stage();
        stageRecord.fullName = fullName;
        stageRecord.shortName = shortName;
        stageRecord.spaceRecord.total = spaceRecord.total;
        stageRecord.spaceRecord.treasure = spaceRecord.treasure;
        stageRecord.spaceRecord.trapA = spaceRecord.trapA;
        stageRecord.spaceRecord.trapB = spaceRecord.trapB;
        stageRecord.spaceRecord.disableA = spaceRecord.disableA;
        stageRecord.spaceRecord.disableB = spaceRecord.disableB;
        stageRecord.trapRecord.trapName = trapRecord.trapName;
        stageRecord.trapRecord.switchA = trapRecord.switchA;
        stageRecord.trapRecord.switchB = trapRecord.switchB;
        return stageRecord;
    }
    // Space Record Fields
// Accessor method for the total spaces array of a stage
    public static int getTotalSpaces(Stage stageRecord)
    {
        return stageRecord.spaceRecord.total;
    }
    // Accessor method for the treasure spaces array of a stage
    public static int [] getTreasureSpaces(Stage stageRecord)
    {
        return stageRecord.spaceRecord.treasure;
    }
    // Accessor method for a specific treasure space of a stage
    public static int getTreasureSpace(Stage stageRecord, int index)
    {
        return stageRecord.spaceRecord.treasure[index];
    }
    // Accessor method for the trap A spaces array of a stage
    public static int [] getTrapASpaces(Stage stageRecord)
    {
        return stageRecord.spaceRecord.trapA;
    }
    // Accessor method for a specific trap A space of a stage
    public static int getTrapASpace(Stage stageRecord, int index)
    {
        return stageRecord.spaceRecord.trapA[index];
    }
    // Accessor method for the trap B spaces array of a stage
    public static int [] getTrapBSpaces(Stage stageRecord)
    {
        return stageRecord.spaceRecord.trapB;
    }
    // Accessor method for a specific trap B space of a stage
    public static int getTrapBSpace(Stage stageRecord, int index)
    {
        return stageRecord.spaceRecord.trapB[index];
    }
    // Accessor method for the disable A spaces array of a stage
    public static int [] getDisableASpaces(Stage stageRecord)
    {
        return stageRecord.spaceRecord.disableA;
    }
    // Accessor method for a specific disable A space of a stage
    public static int getDisableASpace(Stage stageRecord, int index)
    {
        return stageRecord.spaceRecord.disableA[index];
    }
// Accessor method for the disable B spaces array of a stage
    public static int [] getDisableBSpaces(Stage stageRecord)
    {
        return stageRecord.spaceRecord.disableB;
    }
    // Accessor method for a specific disable B space of a stage
    public static int getDisableBSpace(Stage stageRecord, int index)
    {
        return stageRecord.spaceRecord.disableB[index];
    }
    // Trap Record Fields
// Accessor method for the name of a trap in a stage
    public static String getTrapName(Stage stageRecord)
    {
        return stageRecord.trapRecord.trapName;
    }
    // Accessor method for the identifier showing
// if trap A is activated or not in a stage
    public static boolean getTrapSwitchA(Stage stageRecord)
    {
        return stageRecord.trapRecord.switchA;
    }
    // Accessor method for the identifier showing
// if trap B is activated or not in a stage
    public static boolean getTrapSwitchB(Stage stageRecord)
    {
        return stageRecord.trapRecord.switchB;
    }
    // Stage Record Fields
// Accessor Method for the full name of a stage
    public static String getStageFullName(Stage stageRecord)
    {
        return stageRecord.fullName;
    }
    // Accessor Method for the short name of a stage
    public static String getStageShortName(Stage stageRecord)
    {
        return stageRecord.shortName;
    }
    public static Stage getBBStage(Player user)
    {
// Assigning specific numbers for the Ballista Bridge spaces
        final int totalBBSpaces = 18;
        final int [] treasureBBSpaces = {18};
        final int [] trapBBSpacesA = {9};
        final int [] trapBBSpacesB = {13};
        final int [] disableBBSpacesA = {3};
        final int [] disableBBSpacesB = {4};
// Setting up the Ballista trap for the trap spaces
        Trap Ballista = createTrap("Piercing Ballista",
                true, true);
// Setting unique actions to the spaces
        Space Ballista_Bridge_Spaces = createSpaces(totalBBSpaces, treasureBBSpaces,
                trapBBSpacesA, trapBBSpacesB, disableBBSpacesA, disableBBSpacesB);
// Creating the Ballista Bridge stage
        Stage Ballista_Bridge = createStage("on the Ballista Bridge",
                "bridge", Ballista_Bridge_Spaces, Ballista);
        return Ballista_Bridge;
    }
    public static Stage getGHStage(Player user)
    {
// Assigning specific numbers for the Great Hall spaces
        final int totalGHSpaces = 23;
        final int [] treasureGHSpaces = {20, 25, 27, 32, 36};
        final int [] trapGHSpacesA = {23, 28, 33, 34, 39};
        final int [] trapGHSpacesB = {37, 42};
        final int [] disableGHSpacesA = {22, 26, 29};
        final int [] disableGHSpacesB = {20, 31};
// Setting up the Royal Guards trap for the trap spaces
        Trap Alarm = createTrap("Royal Guards Alarm",
                true, true);
// Setting unique actions to the spaces
        Space Great_Hall_Spaces = createSpaces(totalGHSpaces, treasureGHSpaces,
                trapGHSpacesA, trapGHSpacesB,
                disableGHSpacesA, disableGHSpacesB);
// Creating the Great Hall stage
        Stage Great_Hall = createStage("in the Great Hall",
                "hall", Great_Hall_Spaces, Alarm);
        return Great_Hall;
    }
    public static Stage getKCStage(Player user)
    {
// Assigning specific numbers for the Kitchen Countdown spaces
        final int totalKCSpaces = 13;
        final int [] treasureKCSpaces = {44, 53};
        final int [] trapKCSpacesA = {45, 47, 48, 52};
        final int [] trapKCSpacesB = {51, 54, 55, 56};
        final int [] disableKCSpacesA = {46};
        final int [] disableKCSpacesB = {49, 50};
// Setting up the Shelf trap for the trap spaces
        Trap Shelf = createTrap("Falling Shelf",
                true, true);
// Setting unique actions to the spaces
        Space Kitchen_Countdown_Spaces = createSpaces(totalKCSpaces, treasureKCSpaces,
                trapKCSpacesA, trapKCSpacesB,
                disableKCSpacesA, disableKCSpacesB);
// Creating the Kitchen Countdown stage
        Stage Kitchen_Countdown = createStage("in the kitchen",
                "kitchen", Kitchen_Countdown_Spaces, Shelf);
        return Kitchen_Countdown;
    }
    public static Stage getSCStage(Player user)
    {
// Assigning specific numbers for the Solar Central spaces
        final int totalSCSpaces = 17;
        final int [] treasureSCSpaces = {58, 65, 69, 70, 74};
        final int [] trapSCSpacesA = {60, 64, 68, 72};
        final int [] trapSCSpacesB = {63, 67, 71};
        final int [] disableSCSpacesA = {};
        final int [] disableSCSpacesB = {};
// Setting up the Window trap for the trap spaces
        Trap Window = createTrap("Sun Scorching Window", true, true);
// Setting unique actions to the spaces
        Space Solar_Central_Spaces = createSpaces(totalSCSpaces, treasureSCSpaces,
                trapSCSpacesA, trapSCSpacesB,
                disableSCSpacesA, disableSCSpacesB);
// Creating the Solar Central stage
        Stage Solar_Central = createStage("in the Solar",
                "solar", Solar_Central_Spaces, Window);
        return Solar_Central;
    }
    public static Stage getPPSStage(Player user)
    {
// Assigning specific numbers for the Pressure Peak Stairs spaces
        final int totalPPSSpaces = 24;
        final int [] treasurePPSSpaces = {};
        final int [] trapPPSSpacesA = {76, 77, 81, 83, 84, 91, 92, 98};
        final int [] trapPPSSpacesB = {86, 90, 95, 97, 99};
        final int [] disablePPSSpacesA = {80};
        final int [] disablePPSSpacesB = {89};
// Setting up the Stairs trap for the trap spaces
        Trap Stairs = createTrap("Slip Slide Stairs", true, true);
// Setting unique actions to the spaces
        Space Pressure_Peak_Stairs_Spaces = createSpaces(totalPPSSpaces, treasurePPSSpaces,
                trapPPSSpacesA, trapPPSSpacesB,
                disablePPSSpacesA, disablePPSSpacesB);
// Creating the Pressure Peak Stairs stage
        Stage Great_Hall = createStage("in Pressure Peak Stairs",
                "stairs", Pressure_Peak_Stairs_Spaces, Stairs);
        return Great_Hall;
    }
// --------------------------------------------------------------------------------
// ----------------------------------Space Methods---------------------------------
    // Calculates the spaces left and then outputs the space
// number the user is on and the spaces left to exit the game area
    public static void printSpaces(Player user, Stage location)
    {
        int spacesLeft;
        spacesLeft = (getTotalSpaces(location) + 1) - getPlayerSpacesMoved(user);
        print("You are on space " + getPlayerSpacesMoved(user) + ".");
        print("");
        print("There are " + spacesLeft +
                " spaces left to the end of the stage.");
        user.turnsRemaining = getPlayerTurnsRemaining(user) - 1;
        String turnMessage;
        if (getPlayerTurnsRemaining(user) == 1)
        {
            turnMessage = " turn ";
        }
        else
        {
            turnMessage = " turns ";
        }
        print(getPlayerTurnsRemaining(user) + turnMessage +
                "remaining to get pass the " + getStageShortName(location));
        return;
    }
    // Rolls a dice by generating a random number between 1 to 6
    public static int getDiceRoll()
    {
        Random number = new Random();
        int diceRoll = number.nextInt(6) + 1;
        return diceRoll;
    }
    // Calculates the spaces moved by using iteration on itself
// and adds it with the dice roll number
    public static Player addSpacesMoved(Player user)
    {
        int diceRoll = getDiceRoll();
        print("You rolled a " + diceRoll + ".");
        user.spacesMoved = getPlayerSpacesMoved(user) + diceRoll;
        return user;
    }
    // Prints a message about a gold ring
    public static Player treasureSpace(Player user)
    {
        print("");
        print("You have landed on a Treasure Space");
        print("You find some silver coins.");
        int addedCoins = getDiceRoll() + 1;
        print("You have obtained " + addedCoins + " silver coins!");
        user.silverCoins = getPlayerSilverCoins(user) + addedCoins;
        print("Currently you have " + getPlayerSilverCoins(user) + " silver coins.");
        return user;
    }
    public static Player disableSpace(Player user,
                                      Stage location, int [] offStageSpaces)
    {
        print("");
        print("You have landed on a Disable Trap Space");
        if (offStageSpaces[0] < getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[1])
        {
// At the cost breaking the bridge,
// the user disables the Ballista
            print("You break a part of the bridge so" +
                    " you could use it like a wooden spear.");
            print("You launch the spear" +
                    " directly into the ballista and disable it.");
            print("");
// Reduces the turns remaining by 1
            user.turnsRemaining = getPlayerTurnsRemaining(user) - 1;
            print("The bridge will collapse in " +
                    getPlayerTurnsRemaining(user) + " turns.");
        }
        else if (offStageSpaces[1] < getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[2])
        {
// The user disables the alarm
            print("You found a switch.");
            print("You flipped the switch that deactivated " +
                    "the royal guards alarm.");
            print("");
        }
        else if (offStageSpaces[2] < getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[3])
        {
// The user stabilises the shelf
            print("You found a switch.");
            print("You flipped the switch that stabilised " +
                    "the falling shelf trap.");
            print("");
        }
        else if (offStageSpaces[3] < getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[4])
        {
// The user disables the window
            print("You found a switch.");
            print("You flipped the switch that deactivated " +
                    "the sun scorching window.");
            print("");
        }
        else if (offStageSpaces[4] < getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[5])
        {
// The user disables the stairs
            print("You found a switch.");
            print("You flipped the switch that deactivated " +
                    "the slip side stairs.");
            print("");
        }
// Disable Trap Space A
        for (int i = 0; i < getDisableASpaces(location).length; i++)
        {
            if (getPlayerSpacesMoved(user) == getDisableASpace(location, i))
            {
                location.trapRecord.switchA = false;
                print("You have disabled the " +
                        getTrapName(location) + " on space " +
                        getTrapASpace(location, i) + ".");
            }
        }
// Disable Trap Space B
        for (int i = 0; i < getDisableBSpaces(location).length; i++)
        {
            if (getPlayerSpacesMoved(user) == getDisableBSpace(location, i))
            {
                location.trapRecord.switchB = false;
                print("You have disabled the " +
                        getTrapName(location) + " on space " +
                        getTrapBSpace(location, i) + ".");
            }
        }
        return user;
    }
    // When the user goes onto a trap space and the trap is on (= true),
// a message will be output and a life will be removed from the player
    public static Player getTrapped(Player user,
                                    Stage location, int [] offStageSpaces)
    {
        print("");
        print("You have landed on a Trap Space");
        if (offStageSpaces[0] < getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[1])
        {
            print("You are in the line of sight of a " + getTrapName(location));
            print("It was activated and the bolts pierced you.");
        }
        else if (offStageSpaces[1] < getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[2])
        {
            print("The " + getTrapName(location) +
                    " heard you and came over.");
            print("They used an axe to slash you.");
        }
        else if (offStageSpaces[2] < getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[3])
        {
            print("A " + getTrapName(location) +
                    " immediately falls over.");
            print("The pots and pans hit you.");
        }
        else if (offStageSpaces[3] < getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[4])
        {
            print("You go in front of a " + getTrapName(location) + ".");
            print("The heat from the sun scorches your skin.");
        }
        else if (offStageSpaces[4] < getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[5])
        {
            print("You took a step that shook the staircase.");
            print("The " + getTrapName(location) + " turned into a slide.");
            print("You got resetted to the start of the stairs.");
            user.spacesMoved = 75;
            user.lives = getPlayerLives(user) + 1;
        }
        user.lives = getPlayerLives(user) - 1;
        return user;
    }
    public static Player trapSpace(Player user, Stage location, int [] offStageSpaces)
    {
        boolean trapped = true;
// Trap Space A
        for (int i = 0; i < getTrapASpaces(location).length; i++)
        {
            if (getPlayerSpacesMoved(user) == getTrapASpace(location, i))
            {
                if (getTrapSwitchA(location) == false)
                {
                    trapped = false;
                }
            }
        }
// Trap Space B
        for (int i = 0; i < getTrapBSpaces(location).length; i++)
        {
            if (getPlayerSpacesMoved(user) == getTrapBSpace(location, i))
            {
                if (getTrapSwitchB(location) == false)
                {
                    trapped = false;
                }
            }
        }
// Trapping the players who went on the trap space
// when it is on
        if (trapped == true)
        {
            user = getTrapped(user, location, offStageSpaces);
        }
// Nothing happens to the players who went on the trap space
// when it is off
        else if (trapped == false)
        {
            print("Nothing happened.");
        }
// Corrects the spelling for when the lives become a life
        String lifeMessage;
        if (getPlayerLives(user) == 1)
        {
            lifeMessage = " life ";
        }
        else
        {
            lifeMessage = " lives ";
        }
        print("You have " + user.lives + lifeMessage + "left.");
        return user;
    }
// --------------------------------------------------------------------------------
// -----------------------------------Play Methods---------------------------------
    // Output the menu options
    public static int playMenu()
    {
        print("What do you want to do?");
        print("1. Move");
        print("2. Save");
        print("3. Exit");
        int playOption = inputInt("Select an option (1 to 3):");
        print("");
        return playOption;
    }
    public static Player moveSpaces(Player user, Stage location, int [] offStageSpaces)
    {
        user = addSpacesMoved(user);
// Outputs the space number of the user and the number
// of spaces left to exit the stage
        if (getPlayerSpacesMoved(user) < getTotalSpaces(location) + 1)
        {
            printSpaces(user, location);
        }
// Action Spaces
// Treasure Space
        for (int i = 0; i < getTreasureSpaces(location).length; i++)
        {
            if (getPlayerSpacesMoved(user) == getTreasureSpace(location, i))
            {
                user = treasureSpace(user);
            }
        }
// Disable Trap Space A
        for (int i = 0; i < getDisableASpaces(location).length; i++)
        {
            if (getPlayerSpacesMoved(user) == getDisableASpace(location, i))
            {
                user = disableSpace(user, location, offStageSpaces);
            }
        }
// Disable Trap Space B
        for (int i = 0; i < getDisableBSpaces(location).length; i++)
        {
            if (getPlayerSpacesMoved(user) == getDisableBSpace(location, i))
            {
                user = disableSpace(user, location, offStageSpaces);
            }
        }
// Trap Space A
        for (int i = 0; i < getTrapASpaces(location).length; i++)
        {
            if (getPlayerSpacesMoved(user) == getTrapASpace(location, i))
            {
                user = trapSpace(user, location, offStageSpaces);
            }
        }
// Trap Space B
        for (int i = 0; i < getTrapBSpaces(location).length; i++)
        {
            if (getPlayerSpacesMoved(user) == getTrapBSpace(location, i))
            {
                user = trapSpace(user, location, offStageSpaces);
            }
        }
        return user;
    }
    public static Player ranOutOfTurns(Player user, int [] offStageSpaces)
    {
        if (offStageSpaces[0] <= getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[1])
        {
            print("The bridge collapses.");
            print("You fall into the water and drown.");
        }
        else if (offStageSpaces[1] <= getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[2])
        {
            print("A chandelier breaks from the ceiling.");
            print("It comes crashing down and crushes you.");
        }
        else if (offStageSpaces[2] <= getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[3])
        {
            print("A hanging knife falls off from a shelf.");
            print("It slices your face in half and kills you.");
        }
        else if (offStageSpaces[3] <= getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[4])
        {
            print("The heat from the sun sets fire to the room.");
            print("You get trapped and get burnt to death.");
        }
        else if (offStageSpaces[4] <= getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[5])
        {
            print("The walls crushed you.");
            print("You got reduced to flesh and bones, you died.");
        }
        return user;
    }
// --------------------------------------------------------------------------------
    // -------------------------------Game Functions--------------------------------
    public static Player playStage(Player user, Stage location, int [] offStageSpaces)
            throws IOException
    {
// Opening stage message
        print("You're currently located " + getStageFullName(location) + ".");
        print("The " + getStageShortName(location) + " is " +
                getTotalSpaces(location) + " spaces long.");
        print("");
// Increments total spaces based on the current user's spaces moved
        location.spaceRecord.total = getPlayerSpacesMoved(user) + getTotalSpaces(location) + 1;
// Will loop the menu until the user escapes the room
// or collects enough coins to win
        while (getPlayerSpacesMoved(user) < getTotalSpaces(location))
        {
            int menuOption = playMenu();
// Menu Option 1: Move Spaces
            if (menuOption == 1)
            {
                user = moveSpaces(user, location, offStageSpaces);
            }
// Menu Option 2: Save Game
            else if (menuOption == 2)
            {
                userFileInput(user);
                print("Player state saved.");
            }
// Menu Option 3: Exit Game
            else
            {
                print("You have given up, you have lost.");
                user.lives = -1;
                return user;
            }
// Automatic: Run out of lives ends the game
            if (getPlayerLives(user) <= 0)
            {
                print(getPlayerName(user) + " died, you have lost.");
                return user;
            }
// Automatic: Run out of turns ends the game
            else if (getPlayerTurnsRemaining(user) <= 0)
            {
                ranOutOfTurns(user, offStageSpaces);
                print(getPlayerName(user) + ", you have lost the game.");
                user.lives = -1;
                return user;
            }
// Automatic: Get the winning number of silver coins
            if (getPlayerSilverCoins(user) == getPlayerWinningSilverCoins(user))
            {
                print("");
                print("WELL DONE! You have obtained " +
                        getPlayerWinningSilverCoins(user) + " silver coins.");
                return user;
            }
        }
// Automatic: Cleared the stage
        print("");
        print("WELL DONE! You have cleared the " + getStageShortName(location) + ".");
        print("");
        return user;
    }
    // Runs stages based from the player spaces moved
    public static Player playCastleGame(Player user, int [] offStageSpaces)
            throws IOException
    {
        if (offStageSpaces[0] <= getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[1])
        {
// Creating the Ballista Bridge stage for the user to traverse
            Stage Ballista_Bridge = getBBStage(user);
// Starts the Ballista Bridge stage
            user = playStage(user, Ballista_Bridge, offStageSpaces);
// User total travelled spaces has been set
// to the end of the Ballista Bridge stage
            user.spacesMoved = 19;
            user.turnsRemaining = 10;
        }
        else if (offStageSpaces[1] <= getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[2])
        {
// Creating the Great Hall stage for the user to traverse
            Stage Great_Hall = getGHStage(user);
// Starts the Great Hall stage
            user = playStage(user, Great_Hall, offStageSpaces);
// User total travelled spaces has been set
// to the start of the Great Hall stage
            user.spacesMoved = 43;
            user.turnsRemaining = 5;
        }
        else if (offStageSpaces[2] <= getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[3])
        {
// Creating the Kitchen Countdown stage
// for the user to traverse
            Stage Kitchen_Countdown = getKCStage(user);
// Starts the Kitchen Countdown stage
            user = playStage(user, Kitchen_Countdown, offStageSpaces);
// User total travelled spaces has been set
// to the start of the Solar Central stage
            user.spacesMoved = 57;
            user.turnsRemaining = 8;
        }
        else if (offStageSpaces[3] <= getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[4])
        {
// Creating the Solar Central stage
// for the user to traverse
            Stage Solar_Central = getSCStage(user);
// Starts the Solar Central stage
            user = playStage(user, Solar_Central, offStageSpaces);
// User total travelled spaces has been set
// to the start of the Pressure Peak Stairs stage
            user.spacesMoved = 75;
            user.turnsRemaining = 26;
        }
        else if (offStageSpaces[4] <= getPlayerSpacesMoved(user)
                & getPlayerSpacesMoved(user) < offStageSpaces[5])
        {
// Creating the Pressure Peak Stairs stage
// for the user to traverse
            Stage Pressure_Peak_Stairs = getPPSStage(user);
// Starts the Pressure Peak Stairs stage
            user = playStage(user, Pressure_Peak_Stairs, offStageSpaces);
        }
        else if (getPlayerSpacesMoved(user) >= offStageSpaces[5])
        {
            print("Well done you made it to Rooftop Falls!!!");
            print("You have won the game!");
            user.lives = -2;
        }
        return user;
    }
    public static void printEndGameMessage(Player user)
    {
        String lifeMessage;
        if (getPlayerLives(user) == 1)
        {
            lifeMessage = " life ";
        }
        else
        {
            lifeMessage = " lives ";
        }
// End Game Message
        if (user.lives >= 0)
        {
            print(getPlayerName(user) + ", you have ended the game with " +
                    getPlayerLives(user) + lifeMessage + "left");
        }
    }
// --------------------------------------------------------------------------------
}