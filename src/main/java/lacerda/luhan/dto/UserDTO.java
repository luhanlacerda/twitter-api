package lacerda.luhan.dto;

import javax.validation.constraints.NotNull;

/*
Classe modelo que representa o user vindo da request
 */
public class UserDTO {

    private Long id;

    @NotNull
    private String name;

    private Long userId;

    private Long anotherUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAnotherUserId() {
        return anotherUserId;
    }

    public void setAnotherUserId(Long anotherUserId) {
        this.anotherUserId = anotherUserId;
    }
}
