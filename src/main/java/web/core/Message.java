package web.core;

public final class Message {
  private String type;
  private String content;

  public Message(Type type, String content) {
    this.type = type.toString();
    this.content = content;
  }

  public String getType() {
    return type;
  }

  public String getContent() {
    return content;
  }

  public enum Type {
    INFO("info"),
    SUCCESS("success"),
    WARNING("warning");

    private String value;

    private Type(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }
}
