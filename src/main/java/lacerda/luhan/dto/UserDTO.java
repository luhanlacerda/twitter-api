package lacerda.luhan.dto;

import javax.validation.constraints.NotNull;

public class UserDTO {

	private Long id;

	@NotNull
	private String name;

	private Long userFollowId;

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

	public Long getUserFollowId() {
		return userFollowId;
	}

	public void setUserFollowId(Long userFollowId) {
		this.userFollowId = userFollowId;
	}

}
