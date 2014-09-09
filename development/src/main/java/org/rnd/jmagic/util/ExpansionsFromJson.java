package org.rnd.jmagic.util;

import java.net.*;

import javax.json.*;

import org.rnd.jmagic.engine.*;

public class ExpansionsFromJson
{
	public static void main(String args[]) throws java.io.IOException, URISyntaxException
	{
		java.net.URL jsonLocation = CreateFromJson.class.getResource("AllSets-x.json");
		if(null == jsonLocation)
			return;

		JsonReader jsonReader = Json.createReader(jsonLocation.openStream());
		JsonObject root = jsonReader.readObject();

		java.util.Map<String, org.rnd.jmagic.engine.Rarity> rarityMap = new java.util.HashMap<String, Rarity>();
		rarityMap.put("Uncommon", Rarity.UNCOMMON);
		rarityMap.put("Rare", Rarity.RARE);
		rarityMap.put("Basic Land", Rarity.LAND);
		rarityMap.put("Special", Rarity.SPECIAL);
		rarityMap.put("Mythic Rare", Rarity.MYTHIC);
		rarityMap.put("Common", Rarity.COMMON);

		for(java.util.Map.Entry<String, JsonValue> entry: root.entrySet())
		{
			JsonObject expansion = (JsonObject)entry.getValue();
			JsonArray cards = expansion.getJsonArray("cards");
			if(cards == null)
				continue;

			java.util.EnumMap<Rarity, java.util.List<String>> cardList = new java.util.EnumMap<>(Rarity.class);
			for(Rarity rarity: Rarity.values())
				cardList.put(rarity, new java.util.LinkedList<>());
			for(JsonObject card: cards.getValuesAs(JsonObject.class))
			{
				String name = card.getString("name", "[card missing name]");
				for(java.util.Map.Entry<String, String> replacement: org.rnd.jmagic.CardLoader.NON_ASCII_REPLACE.entrySet())
					name = name.replace(replacement.getKey(), replacement.getValue());
				String layout = card.getString("layout", "");
				if(layout.equals("flip") || layout.equals("double-faced") || layout.equals("split"))
				{
					JsonArray names = card.getJsonArray("names");
					if(null != names && !names.getString(0).equals(name))
						continue;
				}

				String rarity = card.getString("rarity", null);

				if(layout.equals("split"))
				{
					java.util.List<JsonString> jsonNames = card.getJsonArray("names").getValuesAs(JsonString.class);
					String splitName = jsonNames.stream().map(n -> n.getString()).reduce((left, right) -> left + " // " + right).orElse("");
					cardList.get(rarityMap.get(rarity)).add(splitName);
				}
				else
					cardList.get(rarityMap.get(rarity)).add(name.replace("\"", "\\\""));
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
				continue;

			java.io.PrintStream write = new java.io.PrintStream(file);
			write.print("package org.rnd.jmagic.expansions;\n\nimport org.rnd.jmagic.engine.*;\n\n@Name(\"" + expansionName.replace("\"", "\\\"") + "\")\npublic final class " + className + " extends SimpleExpansion\n{\n");
			write.print("\tpublic " + className + "()\n\t{\n\t\tsuper();\n\n");

			for(java.util.Map.Entry<Rarity, java.util.List<String>> rarity: cardList.entrySet())
			{
				if(!rarity.getValue().isEmpty())
				{
					java.util.List<String> rarityList = rarity.getValue();

					// just in case there were some cards that are not in order;
					// AE cards might cause this
					java.util.Collections.sort(rarityList);

					write.print("\t\tthis.addCards(Rarity." + rarity.getKey().name());
					for(String card: rarityList)
						write.print(", \"" + card + "\"");
					write.print(");\n");
				}
			}
			write.print("\t}\n}\n");
			write.flush();
			write.close();
		}
	}
}