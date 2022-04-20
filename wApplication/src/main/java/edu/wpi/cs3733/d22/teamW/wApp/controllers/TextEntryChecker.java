package edu.wpi.cs3733.d22.teamW.wApp.controllers;

import java.util.ArrayList;
import java.util.Arrays;

public class TextEntryChecker {
  static ArrayList<String> illegalCharacters =
      new ArrayList<String>(
          Arrays.asList(
              new String[] {
                "=", "\'", "\"", "¬", "\\", "-", ";", "[", "]", "{", "}", ":", ",", "."
              }));

  public static boolean check(String string) {

    for (String character : illegalCharacters) {
      if (string.contains(character)) {
        return false;
      }
    }
    return true;
  }
}
