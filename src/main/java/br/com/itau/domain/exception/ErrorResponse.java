package br.com.itau.domain.exception;

public class ErrorResponse {

    private String code;
    private String description;
    private String message;

    public ErrorResponse(String code, String description, String message) {
        this.code = code;
        this.description = description;
        this.message = message;
    }

    public ErrorResponse(String cause) {
        String codeString = "\"code\":\"";
        String descriptionString = "\"description\":\"";
        String messageString = "\"message\":\"";
        String separator = "\",\"";

        if (cause.contains(codeString) && cause.contains(descriptionString) && cause.contains(messageString)) {
            String content = cause.substring(cause.indexOf("[{")+2, cause.lastIndexOf("}]"));
            this.code = content.substring(content.indexOf(codeString)+codeString.length(), content.indexOf(separator));

            content = content.substring(content.indexOf(separator)+1);
            this.description = content.substring(content.indexOf(descriptionString)+descriptionString.length(), content.indexOf(separator));

            content = content.substring(content.indexOf(separator)+1);
            this.message = content.substring(content.indexOf(messageString)+messageString.length(), content.lastIndexOf("\""));
        } else {
            throw new IllegalArgumentException(String.format("Não foi posssível obter o ErrorResponse com base na causa: %s", cause));
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
