package org.rnd.jmagic.util;

import java.net.*;

import javax.json.*;

public class ExpansionsFromJson
{
	public static void main(String args[]) throws java.io.IOException, URISyntaxException
	{
		java.net.URL jsonLocation = CreateFromJson.class.getResource("AllSets-x.json");
		if(null == jsonLocation)
			return;

		JsonReader jsonReader = Json.createReader(jsonLocation.openStream());
		JsonObject root = jsonReader.readObject();

		for(java.util.Map.Entry<String, JsonValue> entry: root.entrySet())
		{
			JsonObject expansion = (JsonObject)entry.getValue();
			JsonArray cards = expansion.getJsonArray("cards");
			if(cards == null)
				continue;

			java.util.List<String> cardList = new java.util.LinkedList<String>();
			for(JsonObject card: cards.getValuesAs(JsonObject.class))
			{
				String name = card.getString("name", "[card missing name]");
				String layout = card.getString("layout", "");
				if(layout.equals("flip") || layout.equals("double-faced") || layout.equals("split"))
				{
					JsonArray names = card.getJsonArray("names");
					if(null != names && !names.getString(0).equals(name))
						continue;
				}

				if(layout.equals("split"))
				{
					java.util.List<JsonString> jsonNames = card.getJsonArray("names").getValuesAs(JsonString.class);
					String splitName = jsonNames.stream().map(n -> n.getString()).reduce((left, right) -> left + " // " + right).orElse("");
					cardList.add(splitName);
				}
				else
					cardList.add(name.replace("\"", "\\\""));
			}

			if(cardList.isEmpty())
				continue;

			String expansionName = expansion.getString("name", "[no name found]");

			String className = "";
			for(String part: expansionName.replaceAll("[^a-zA-Z0-9 ]", "").split(" "))
			{
				className += part.substring(0, 1).toUpperCase() + part.substring(1);
			}

			java.io.File file = new java.io.File("..\\cards\\src\\main\\java\\org\\rnd\\jmagic\\expansions\\" + className + ".java");

			if(!file.exists())
			{
				System.out.println(expansionName);
				continue;
			}

			java.io.PrintStream write = new java.io.PrintStream(file);
			write.print("package org.rnd.jmagic.expansions;\n\nimport org.rnd.jmagic.engine.*;\n\n@Name(\"" + expansionName.replace("\"", "\\\"") + "\")\npublic final class " + className + " extends SimpleExpansion\n{\n");
			write.print("\tpublic " + className + "()\n\t{\n\t\tsuper(new String[] {\"" + org.rnd.util.SeparatedList.get("\", \"", "", cardList) + "\"});");
			write.print("\n\t}\n}\n");
			write.flush();
			write.close();
		}
	}
}