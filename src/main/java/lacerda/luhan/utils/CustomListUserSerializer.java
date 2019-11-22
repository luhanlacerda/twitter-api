package lacerda.luhan.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lacerda.luhan.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CustomListUserSerializer extends StdSerializer<List<User>> {

	public CustomListUserSerializer() {
		this(null);
	}

	public CustomListUserSerializer(Class<List<User>> t) {
		super(t);
	}

	@Override
	public void serialize(List<User> users, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		List<String> nomes = new ArrayList<>();
		for (User user : users) {
			nomes.add(user.getName());
		}
		generator.writeObject(nomes);
	}

}
