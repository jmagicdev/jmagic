package org.rnd.jmagic.util;

import javax.json.*;

import org.rnd.jmagic.engine.*;

public class ColorIdentityUpdater
{
	public static void main(String args[]) throws java.io.IOException
	{
		java.net.URL jsonLocation = CreateFromJson.class.getResource("AllSets.json");
		if(null == jsonLocation)
			jsonLocation = new java.net.URL("http://mtgjson.com/json/AllSets.json");
		JsonReader jsonReader = Json.createReader(jsonLocation.openStream());
		JsonObject root = jsonReader.readObject();

		final java.util.Set<String> processed = new java.util.HashSet<>();
		root.forEach((expansionName, expansionValue) -> {
			JsonObject expansionObject = (JsonObject)expansionValue;
			JsonArray cardList = expansionObject.getJsonArray("cards");
			if(null == cardList)
				return;

			cardList.forEach(cardValue -> {
				JsonObject cardObject = (JsonObject)cardValue;
				String cardName = cardObject.getString("name", "");
				if(processed.contains(cardName))
					return;

				processed.add(cardName);

				String className = org.rnd.jmagic.CardLoader.formatName(cardName);
				java.io.File cardFile = new java.io.File("../cards/src/main/java/org/rnd/jmagic/cards/" + className + ".java");
				if(!cardFile.exists())
					return;

				java.util.Set<String> colorIdentity = new java.util.TreeSet<String>((l, r) -> {
					Color lc = Color.valueOf(l);
					Color rc = Color.valueOf(r);
					if(lc == rc)
						return 0;
					if(null == lc && null == rc)
						return l.compareTo(r);
					if(null == lc)
						return -1;
					if(null == rc)
						return 1;
					return lc.ordinal() - rc.ordinal();
				});

				if(cardObject.containsKey("colors"))
					for(JsonString color: cardObject.getJsonArray("colors").getValuesAs(JsonString.class))
						colorIdentity.add(color.getString().toUpperCase());

				String text = cardObject.getString("text", null);
				if(null != text)
				{
					java.util.List<String> abilities = new java.util.LinkedList<>();
					String abilityText = text.replace("\\\"", "\"").replace("−", "-").replace("{", "(").replace("}", ")");
					for(String abilityLine: abilityText.split("\n"))
						abilities.add(abilityLine);

					java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\([WUBRG/0-9P]+\\)");
					for(String ability: abilities)
					{
						java.util.regex.Matcher matcher = pattern.matcher(ability);
						while(matcher.find())
						{
							String match = matcher.group();
							for(Color color: Color.values())
								if(match.contains(color.getLetter()))
									colorIdentity.add(color.toString().toUpperCase());
						}
					}
				}

				try
				{
					StringBuilder newContent = new StringBuilder();
					java.io.BufferedReader read = new java.io.BufferedReader(new java.io.FileReader(cardFile));
					String line;
					while(null != (line = read.readLine()))
					{
						if(line.startsWith("@ColorIdentity"))
						{
							newContent.append("@ColorIdentity({");
							if(!colorIdentity.isEmpty())
							{
								newContent.append("Color.");
								newContent.append(org.rnd.util.SeparatedList.get(", Color.", "", colorIdentity));
							}
							newContent.append("})\r\n");
						}
						else
						{
							newContent.append(line);
							newContent.append("\r\n");
						}
					}

					read.close();

					java.io.FileWriter write = new java.io.FileWriter(cardFile, false);
					write.write(newContent.toString());
					write.flush();
					write.close();
				}
				catch(Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		});
	}
}
