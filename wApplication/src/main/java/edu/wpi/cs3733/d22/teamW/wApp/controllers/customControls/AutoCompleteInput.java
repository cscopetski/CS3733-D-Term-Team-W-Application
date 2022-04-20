package edu.wpi.cs3733.d22.teamW.wApp.controllers.customControls;

import edu.wpi.cs3733.d22.teamW.wDB.Errors.InValidRequestType;
import edu.wpi.cs3733.d22.teamW.wDB.enums.RequestType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import javafx.scene.control.ComboBox;

public class AutoCompleteInput extends ComboBox<String> {
  private static class Trie {
    private static class Node {
      public Character value;
      public ArrayList<Node> children = new ArrayList<>();

      public Node(Character value) {
        this.value = value;
      }

      public void add(String input) {
        if (input.isEmpty()) {
          return;
        }
        boolean noMatch = true;
        for (Node n : children) {
          if (n.value == input.charAt(0) && input.length() != 1) {
            n.add(input.substring(1));
            noMatch = false;
          }
        }
        if (noMatch) {
          Node n1 = new Node(input.charAt(0));
          children.add(n1);
          if (input.length() != 1) {
            n1.add(input.substring(1));
          }
        }
      }

      public ArrayList<String> getListIgnoreCase(String input) {
        ArrayList<String> result = new ArrayList<>();
        if (!input.isEmpty()
            && !value
                .toString()
                .equalsIgnoreCase(((Character) input.charAt(input.length() - 1)).toString())) {
          return result;
        }

        if (children.size() == 0) {
          result.add(input);
        } else {
          for (Node n : children) {
            result.addAll(n.getList(input + n.value));
          }
        }
        return result;
      }

      public ArrayList<String> getList(String input) {
        ArrayList<String> result = new ArrayList<>();
        if (!input.isEmpty() && value != input.charAt(input.length() - 1)) {
          return result;
        }

        if (children.size() == 0) {
          result.add(input);
        } else {
          for (Node n : children) {
            result.addAll(n.getList(input + n.value));
          }
        }
        return result;
      }
    }

    private Node head;

    public Trie(ArrayList<String> input) {
      loadValues(input);
    }

    public void loadValues(ArrayList<String> values) {
      head = new Node('\0');
      for (String s : values) {
        head.add(s);
      }
    }

    public ArrayList<String> getList() {
      return getList("");
    }

    public ArrayList<String> getList(String input) {
      Node n = head;
      for (Character c : input.toCharArray()) {
        Node newN = n;
        for (Node node : n.children) {
          if (node.value.equals(c)) {
            newN = node;
            break;
          }
        }
        if (newN.equals(n)) {
          return new ArrayList<>();
        }
        n = newN;
      }

      return n.getList(input);
    }

    public ArrayList<String> getListIgnoreCase(String input) {
      Node n = head;
      for (Character c : input.toCharArray()) {
        Node newN = n;
        for (Node node : n.children) {
          if (node.value.toString().equalsIgnoreCase(c.toString())) {
            newN = node;
            break;
          }
        }
        if (newN.equals(n)) {
          return new ArrayList<>();
        }
        n = newN;
      }

      return n.getList(input);
    }

    public boolean contains(String input) {
      int i = 0;
      Node h = head;
      while (true) {
        boolean found = false;
        for (Node n : h.children) {
          if (n.value.equals(input.charAt(i))) {
            h = n;
            found = true;
            i++;
            if (i == input.length()) {
              return true;
            }
            break;
          }
        }
        if (!found) {
          return false;
        }
      }
    }

    public boolean containsIgnoreCase(String input) {
      int i = 0;
      Node h = head;
      while (true) {
        boolean found = false;
        for (Node n : h.children) {
          if (n.value.toString().equalsIgnoreCase(((Character) input.charAt(i)).toString())) {
            h = n;
            found = true;
            i++;
            if (i == input.length()) {
              return true;
            }
            break;
          }
        }
        if (!found) {
          return false;
        }
      }
    }
  }

  private Trie trie;

  public AutoCompleteInput() {
    setEditable(true);
    getEditor()
        .setOnKeyTyped(
            e -> {
              getItems().clear();
              getItems().addAll(trie.getList(getText()));
              if (!trie.contains(getText())) {
                getEditor().setStyle("-fx-text-fill: red");
              } else {
                getEditor().setStyle("-fx-text-fill: black");
              }
            });
  }

  public void loadValues(String[] values) {
    loadValues((ArrayList<String>) Arrays.stream(values).collect(Collectors.toList()));
  }

  public void loadValues(ArrayList<String> values) {
    trie = new Trie(values);
    getItems().clear();
    getItems().addAll(trie.getList(getValue() != null ? getValue() : ""));
  }

  private RequestType getSelection() throws InValidRequestType {
    return RequestType.getRequestType(getSelectionModel().getSelectedItem());
  }

  private String getText() {
    return getEditor().getText();
  }
}
