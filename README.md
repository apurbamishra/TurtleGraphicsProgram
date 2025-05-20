# Turtle Graphics Drawing Tool

## Project Description

This project is a Java-based Turtle Graphics drawing tool developed as part of an academic coursework requirement. It simulates a virtual pen (or "turtle") that can draw on a canvas based on textual commands entered by the user. The application utilizes Java Swing and AWT for the graphical user interface, and leverages the `LBUGraphics.jar` library for drawing operations and canvas handling.

## Design Overview

The application is designed using object-oriented programming principles, with emphasis on:

- **Inheritance**: The `TurtleGraphics` class extends the `LBUGraphics` base class to customize and extend graphical behavior.
- **Encapsulation**: Command processing, validation, and execution logic are modularized for clarity and maintainability.
- **GUI Integration**: A Swing-based interface is used to accept commands through a text field and buttons, and to display dialogs for user feedback.

## Core Functionalities

- **Drawing Controls**: Supports movement (`move`), rotation (`left`, `right`, `reverse`), and pen state control (`penup`, `pendown`).
- **Shape Rendering**: Allows drawing of predefined shapes like `square`, `triangle`, and custom triangles using side lengths.
- **Customization**: Users can set pen color using basic color commands (`red`, `green`, etc.) or RGB values (`pencolour r,g,b`), and adjust pen width.
- **Canvas Operations**: Includes `clear`, `reset`, and image saving capabilities.
- **Command Management**: Features support for command history, saving/loading commands from text files, and executing saved instructions.

## Error Handling

The system includes input validation for all commands and provides informative GUI dialogs when invalid or unsupported commands are entered.

## File Management

The application allows users to:
- Save the drawing as an image file.
- Save and load command history to and from text files.
- Load and execute commands from external text files.

---

