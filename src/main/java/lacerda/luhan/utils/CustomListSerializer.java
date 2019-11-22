package lacerda.luhan.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import lacerda.luhan.entity.Tweet;

@SuppressWarnings("serial")
public class CustomListSerializer extends StdSerializer<List<Tweet>> {

	public CustomListSerializer() {
		this(null);
	}

	public CustomListSerializer(Class<List<Tweet>> t) {
		super(t);
	}

	@Override
	public void serialize(List<Tweet> tweeters, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		List<String> nomes = new ArrayList<>();
		for (Tweet tweet : tweeters) {
			nomes.add(tweet.getMessage());
		}
		generator.writeObject(nomes);
	}

}
