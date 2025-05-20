
import uk.ac.leedsbeckett.oop.LBUGraphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class TurtleGraphics extends LBUGraphics {

    // Tracks if the image has been saved
    private boolean imageSaved = false;

    // Flag to check if commands are being loaded from a file
    private boolean isLoadingFromFile = false;

    // Console-based input method
    public void consoleInput() {
        Scanner sc = new Scanner(System.in);
        boolean flagVariable = true;

        System.out.println("Enter a turtle command: ");

        while (flagVariable) {
            String command = sc.nextLine();

            if (command.equals("exit")) {
                System.out.println("Thank you for using Turtle graphics: ");
                flagVariable = false;
            } else {
                processCommand(command);
            }
        }
    }

    // Validates and splits a user-entered command
    private String[] checkCommand(String command) {
        command = command.trim().toLowerCase();
        String[] splitText = command.split(" ");

        if (splitText.length > 4) {
            JOptionPane.showMessageDialog(null, "Too many parameters. Please enter a valid command.");
            return null;
        }

        if (splitText.length == 2) {
            try {
                int parameter = Integer.parseInt(splitText[1]);
                if (parameter < 0) {
                    JOptionPane.showMessageDialog(null, "Parameter must be greater than zero");
                    return null;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid number format: " + splitText[1]);
                return null;
            }
        }

        return splitText;
    }

    // Saves the current image to a PNG fil
    private void saveImage() {
        try {
            BufferedImage image = getBufferedImage();
            File imageFile = new File("SavedImage.png");

            ImageIO.write(image, "png", imageFile);
            imageSaved = true;

            JOptionPane.showMessageDialog(null, "Image successfully saved in the project folder.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving image.");
        }
    }

    // Loads image from a file and displays it
    private void loadImage() {
        try {
            File file = new File("SavedImage.png");

            if (!file.exists()) {
                JOptionPane.showMessageDialog(null, "File does not exist");
                return;
            }

            BufferedImage image = ImageIO.read(file);
            setBufferedImage(image);

            JOptionPane.showMessageDialog(null, "Image loaded successfully.");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading image.");
        }
    }

    // Returns name details for the 'about' command
    private String getDetails() {
        String firstname = "Apurba";
        String surname = "Mishra";
        return "created by : " + firstname + " " + surname;
    }

    // Overrides LBUGraphics method
    @Override
    public void about() {
        displayMessage(getDetails());
        super.about();
    }

    // Draws a square of the given side length
    private void drawSquare(int length) {
        for (int i = 0; i < 4; i++) {
            forward(length);
            left(90);
        }
    }

    // Sets custom pen color using RGB values
    private void penColour(String[] splitText) {
        try {
            int red  = Integer.parseInt(splitText[1]);
            int green = Integer.parseInt(splitText[2]);
            int blue = Integer.parseInt(splitText[3]);

            if ((red >= 0 && red <= 255) && (green >= 0 && green <= 255) && (blue >= 0 && blue <= 255)) {
                Color c = new Color(red, green, blue);
                setPenColour(c);
            } else {
                JOptionPane.showMessageDialog(null, "RGB values should be between 0 and 255.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "RGB values should be valid integers.");
        }
    }

    // Sets pen width
    private void penWidth(int width) {
        if (width > 0) {
            setStroke(width);
        } else {
            JOptionPane.showMessageDialog(null, "Pen width must be greater than 0.");
        }
    }

    // Draws an equilateral triangle with all sides of the same size
    private void equilateralTriangle(int size) {
        for (int i = 0; i < 3; i++) {
            forward(size);
            left(240);
        }
    }

    // Draws a scalene triangle based on three sides
    private void scaleneTriangle(int s1, int s2, int s3) {
        if ((s1 + s2 > s3) && (s1 + s3 > s2) && (s2 + s3 > s1)) {
            int a = s1, b = s2, c = s3;
            int angleA = (int) Math.toDegrees(Math.acos((double)(b*b + c*c - a*a) / (2 * b * c)));
            int angleB = (int) Math.toDegrees(Math.acos((double)(a*a + c*c - b*b) / (2 * a * c)));
            int angleC = 180 - angleA - angleB;

            forward(s1);
            left(180 - angleC);
            forward(s2);
            left(180 - angleA);
            forward(s3);
        } else {
            displayMessage("Invalid triangle sides.");
        }
    }

    // Appends a typed command to the command history file
    private void saveCommand(String command) {
        try {
            FileWriter writeInFile = new FileWriter("command_history.txt", true); // Append mode
            writeInFile.write(command + "\n");
            writeInFile.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to save the commands in file.");
        }
    }

    // Loads commands from the saved file and re-executes them
    private void loadCommands() {
        File loadFile = new File("command_history.txt");

        if (!loadFile.exists()) {
            JOptionPane.showMessageDialog(null, "Command history file not found.");
            return;
        }

        isLoadingFromFile = true;

        try {
            Scanner scanner = new Scanner(loadFile);
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine().trim();
                if (!command.isEmpty() && !command.equals("loadcommands")) {
                    processCommand(command);
                }
            }
            JOptionPane.showMessageDialog(null, "Commands loaded and executed from file.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading command history file.", "File Read Error", JOptionPane.ERROR_MESSAGE);
        }

        isLoadingFromFile = false;
    }

    // Processes all turtle commands entered by the user
    @Override
    public void processCommand(String command) {

        String[] splitText = checkCommand(command);
        if (splitText == null) {
            return;
        }

        String firstCommand = splitText[0];

        // Save command to history if not loading from file
        if (!isLoadingFromFile && !firstCommand.equals("loadcommands")) {
            saveCommand(command);
        }

        int turtleDistance = 0;

        int commandLength = splitText.length;

        if (commandLength == 2) {
            turtleDistance = Integer.parseInt(splitText[1]);
        }

        // Command execution using switch-case
        switch (firstCommand) {
            case "penup":
                if (commandLength == 1) {
                    drawOff();
                    displayMessage("Pen Status: up");
                } else {
                    JOptionPane.showMessageDialog(null, "Penup command does not take any paramter.");
                }
                break;

            case "pendown":
                if (commandLength == 1) {
                    drawOn();
                    displayMessage("Pen Status: down");
                } else {
                    JOptionPane.showMessageDialog(null, "Pendown command does not take any paramter.");
                }
                break;

            case "left":
                if (commandLength !=2) {
                    left();
                    displayMessage("Turtle turned left");
                } else {
                    left(turtleDistance);
                    displayMessage("left command with parameter " + turtleDistance + " typed.");
                }
                break;

            case "right":
                if (commandLength !=2) {
                    right();
                    displayMessage("Turtle turned right");
                } else {
                    right(turtleDistance);
                    displayMessage("right command with parameter " + turtleDistance + " typed.");
                }
                break;

            case "move":
                if (commandLength == 2) {
                    forward(turtleDistance);
                    displayMessage("Move command with parameter " + turtleDistance + " typed.");
                } else {
                    JOptionPane.showMessageDialog(null, "Missing parameter. Please mention a parameter for 'move' command.");
                }
                break;

            case "reverse":
                if (commandLength == 2) {
                    forward(-turtleDistance);
                    displayMessage("Move command with parameter " + turtleDistance + " typed.");
                } else {
                    JOptionPane.showMessageDialog(null, "Missing parameter. Please mention a parameter for 'reverse' command.");
                }
                break;

            case "black":
                if (commandLength == 1) {
                    setPenColour(Color.BLACK);
                    displayMessage("Pen colour: Black");
                } else {
                    JOptionPane.showMessageDialog(null, "Black command does not take any paramter.");
                }
                break;

            case "green":
                if (commandLength == 1) {
                    setPenColour(Color.GREEN);
                    displayMessage("Pen colour: Green");
                } else {
                    JOptionPane.showMessageDialog(null, "Green command does not take any paramter.");
                }
                break;

            case "red":
                if (commandLength == 1) {
                    setPenColour(Color.RED);
                    displayMessage("Pen colour: Red");
                } else {
                    JOptionPane.showMessageDialog(null, "Red command does not take any paramter.");
                }
                break;

            case "white":
                if (commandLength == 1) {
                    setPenColour(Color.WHITE);
                    displayMessage("Pen colour: White");
                } else {
                    JOptionPane.showMessageDialog(null, "White command does not take any paramter.");
                }
                break;

            case "reset":
                if (commandLength == 1) {
                    reset();
                    setPenColour(Color.RED); // or any default
                    setStroke(1);
                    displayMessage("Reset command typed.");
                } else {
                    JOptionPane.showMessageDialog(null, "Reset command does not take any paramter.");
                }
                break;

            case "clear":
                if (!imageSaved) {
                    int userChoice = JOptionPane.showConfirmDialog(null, "The image is not saved. Do you want to save it?", "Unsaved Image", JOptionPane.YES_NO_OPTION);

                    if (userChoice == JOptionPane.YES_OPTION) {
                        saveImage();
                    }
                }

                clear();
                displayMessage("Clear command typed.");
                imageSaved = false;
                break;

            case "saveimage":
                if (commandLength == 1) {
                    saveImage();
                    displayMessage("Save image command typed");
                } else {
                    JOptionPane.showMessageDialog(null, "Save image command does not take any paramter.");
                }
                break;

            case "loadimage":
                if (commandLength == 1) {
                    loadImage();
                    displayMessage("Load image command typed.");
                } else {
                    JOptionPane.showMessageDialog(null, "Load image command does not take any paramter.");
                }
                break;

            case "about":
                if (commandLength == 1) {
                    about();
                } else {
                    JOptionPane.showMessageDialog(null, "About command does not take any paramter.");
                }
                about();
                break;

            case "square":
                if (splitText.length == 2) {
                    drawOn();
                    drawSquare(turtleDistance);
                    displayMessage("Square command typed.");
                } else {
                    JOptionPane.showMessageDialog(null, "Missing parameter. Please mention a parameter for 'square' command.");
                }
                break;

            case "pencolour":
                if (commandLength == 4) {
                    drawOn();
                    penColour(splitText);
                    displayMessage("Pen colour command typed.");
                } else {
                    JOptionPane.showMessageDialog(null, "Missing parameter. Please mention three parameters for 'pencolour' command.");
                }
                break;

            case "penwidth":
                if (commandLength == 2) {
                    int widthValue = Integer.parseInt(splitText[1]);
                    drawOn();
                    penWidth(widthValue);
                    displayMessage("Pen width command typed");
                } else {
                    JOptionPane.showMessageDialog(null,"Missing parameter. Please mention a parameter for 'penwidth' command.");
                }
                break;

            case "triangle":
                if (commandLength == 2) {
                    int size = Integer.parseInt(splitText[1]);
                    drawOn();
                    equilateralTriangle(size);
                    displayMessage("Equilateral Triangle command typed.");
                }
                else if (commandLength == 4)
                {
                    int side1 = Integer.parseInt(splitText[1]);
                    int side2 = Integer.parseInt(splitText[2]);
                    int side3 = Integer.parseInt(splitText[3]);
                    drawOn();
                    scaleneTriangle(side1, side2, side3);
                    displayMessage("Scalene Triangle command typed.");
                }
                else if (commandLength == 1) {
                    JOptionPane.showMessageDialog(null, "Missing parameter. Please mention a parameter for 'triangle' command.");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid sides for triangle.");
                }
                break;

            case "dance":
                if (commandLength == 2) {
                    int moves = Integer.parseInt(splitText[1]);
                    dance(moves);
                    displayMessage("Dance command typed.");
                } else {
                    JOptionPane.showMessageDialog(null, "Missing parameter. Please mention a parameter for 'dance' command.");
                }
                break;

            case "speed":
                if (commandLength == 2) {
                    int speedValue = Integer.parseInt(splitText[1]);
                    setTurtleSpeed(speedValue);
                    displayMessage("Speed command typed.");
                } else {
                    JOptionPane.showMessageDialog(null, "Missing parameter. Please mention a parameter for 'speed' command.");
                }
                break;

            case "circle" :
                if (commandLength == 2) {
                    int radius = Integer.parseInt(splitText[1]);
                    drawOn();
                    circle(radius);
                    displayMessage("Circle command typed.");
                } else {
                    JOptionPane.showMessageDialog(this, "Usage: circle <radius>", "Missing Parameter", JOptionPane.WARNING_MESSAGE);
                }
                break;

            case "loadcommands":
                if (commandLength == 1) {
                    loadCommands();
                    displayMessage("Load command typed.");
                } else {
                    JOptionPane.showMessageDialog(null, "Load command does not take any paramter.");
                }
                break;

            case "exit":
                JOptionPane.showMessageDialog(null, "Thank you for using turtle graphics program.");
                break;

            default:
                JOptionPane.showMessageDialog(null, "Unknown command: " + firstCommand + ". Please enter a valid command.");
                break;
        }
    }
}
