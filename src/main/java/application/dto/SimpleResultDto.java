package application.dto;

public class SimpleResultDto extends SimpleMessageDto {

    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
