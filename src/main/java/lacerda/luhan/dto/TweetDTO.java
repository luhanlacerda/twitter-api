package lacerda.luhan.dto;

import javax.validation.constraints.NotNull;

import lacerda.luhan.entity.User;

public class TweetDTO {

	private Long id;

	@NotNull
	private String message;

	@NotNull
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
