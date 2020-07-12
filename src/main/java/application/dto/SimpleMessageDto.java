package application.dto;

public class SimpleMessageDto {

    private String message;

    private long id;

    public SimpleMessageDto() {
    }

    public SimpleMessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
