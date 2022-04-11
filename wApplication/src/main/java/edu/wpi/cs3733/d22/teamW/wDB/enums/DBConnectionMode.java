package edu.wpi.cs3733.d22.teamW.wDB.enums;

public enum DBConnectionMode {
  INSTANCE;
  boolean connectionType = true;

  /**
   * Get the selected connection type
   *
   * @return True for embedded connection, false for client-server connection
   */
  public boolean getConnectionType() {
    return connectionType;
  }

  public void setEmbeddedConnection() {
    connectionType = true;
  }

  public void setServerConnection() {
    connectionType = false;
  }
}
