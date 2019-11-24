package lacerda.luhan.dto;

import javax.validation.constraints.NotNull;

import lacerda.luhan.entity.User;

/*
Classe modelo que representa o tweet vindo da request
 */
public class TweetDTO {

    private Long id;

    private String message;

    @NotNull
    private Long userId;

    private String date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
