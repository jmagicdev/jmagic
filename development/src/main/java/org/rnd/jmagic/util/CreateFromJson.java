package org.rnd.jmagic.util;

import java.net.*;
import java.util.*;
import java.util.function.*;

import javax.json.*;

public class CreateFromJson
{
	public static void main(String args[]) throws java.io.IOException, URISyntaxException
	{
		java.net.URL jsonLocation = CreateFromJson.class.getResource("AllSets-x.json");
		if(null == jsonLocation)
			return;

		JsonReader jsonReader = Json.createReader(jsonLocation.openStream());
		JsonObject root = jsonReader.readObject();

		for(Map.Entry<String, JsonValue> entry: root.entrySet())
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
				if(layout.equals("flip") || layout.equals("double-faced"))
				{
					JsonArray names = card.getJsonArray("names");
					if(null != names && !names.getString(0).equals(name))
						continue;
				}

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

	public static void main2(String args[]) throws java.io.IOException, URISyntaxException
	{
		boolean deck = false;
		if(args.length > 0)
		{
			if(args[0].equals("--deck"))
				deck = true;
			else
			{
				System.out.println("May specify --deck to list individual cards; otherwise give no cmdline params to be asked for an expansion.");
				return;
			}
		}

		java.net.URL jsonLocation = CreateFromJson.class.getResource("AllSets-x.json");
		if(null == jsonLocation)
			jsonLocation = new java.net.URL("http://mtgjson.com/json/AllSets-x.json");
		JsonReader jsonReader = Json.createReader(jsonLocation.openStream());
		JsonObject root = jsonReader.readObject();

		if(deck)
		{
			System.out.println("Type the name of each card you want, one per line, then give end-of-file (Ctrl-Z on windows)");
			Set<String> cardNames = new HashSet<String>();
			java.io.BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			while(true)
			{
				String line = inputReader.readLine();
				if(line == null)
					break;
				cardNames.add(line.trim());
			}

			JsonArrayBuilder cardsToWrite = Json.createArrayBuilder();
			for(JsonValue expansion: root.values())
				for(JsonValue cardValue: ((JsonObject)expansion).getJsonArray("cards"))
				{
					JsonObject card = (JsonObject)cardValue;
					if(cardNames.contains(card.getString("name")))
						cardsToWrite.add(card);
				}

			System.out.println("Writing card shells...");
			cardsToWrite.build().forEach(new JsonConsumer(root));
			System.out.println("Done.");
			return;
		}

		// --deck not given, process an expansion

		List<String> expansions = new LinkedList<String>(root.keySet());

		for(int i = 0; i < expansions.size(); ++i)
			System.out.println(i + ": " + expansions.get(i));
		System.out.print("Expansion? ");

		java.io.BufferedReader inputReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		String input = inputReader.readLine();

		int choice = -1;
		try
		{
			choice = Integer.parseInt(input, 10);
		}
		catch(NumberFormatException e)
		{
			choice = expansions.indexOf(input);
		}

		if(choice < 0 || choice >= expansions.size())
		{
			System.out.println("Illegal input.  Must enter either expansion index or name (case-sensitive).");
			return;
		}

		String expansion = expansions.get(choice);
		JsonObject expansionDetails = root.getJsonObject(expansion);
		JsonArray cards = expansionDetails.getJsonArray("cards");

		System.out.println("Writing card shells...");
		cards.forEach(new JsonConsumer(root));
		System.out.println("Done.");
	}

	public static class JsonConsumer implements Consumer<JsonValue>
	{
		private JsonObject root;
		private Map<String, List<JsonObject>> expansions;

		public JsonConsumer(JsonObject root)
		{
			this.root = root;
			this.expansions = new java.util.HashMap<String, List<JsonObject>>();
		}

		@Override
		public void accept(JsonValue jsonValue)
		{
			JsonObject json = (JsonObject)jsonValue;
			CardShell card = new CardShell();

			card.name = json.getString("name");

			String manaCost = json.getString("manaCost", null);
			if(null != manaCost)
				card.manaCost = manaCost.replace('{', '(').replace('}', ')').replaceAll("\\((.)\\)", "$1");

			String type = json.getString("type", null);
			if(null != type)
				card.types = type.toString();

			String text = json.getString("text", null);
			if(null != text)
			{
				String abilityText = text.replace("\\\"", "\"").replace("−", "-").replace("{", "(").replace("}", ")");
				for(String abilityLine: abilityText.split("\n"))
					card.abilities.add(abilityLine);
			}

			String power = json.getString("power", null);
			if(null != power)
				card.power = power.toString().replace("−", "-");

			String toughness = json.getString("toughness", null);
			if(null != toughness)
				card.toughness = toughness.toString().replace("−", "-");

			int loyalty = json.getInt("loyalty", Integer.MIN_VALUE);
			if(Integer.MIN_VALUE != loyalty)
				card.loyalty = Integer.toString(loyalty);

			card.expansions = new HashMap<String, String>();
			for(JsonString printing: json.getJsonArray("printings").getValuesAs(JsonString.class))
			{
				String expansion = printing.getString();
				String rarity = this.getRarity(expansion, card.name);
				if(null != rarity)
					card.expansions.put(expansion, rarity);
			}

			card.colors = new HashSet<String>();
			if(json.containsKey("colors"))
				for(JsonString color: json.getJsonArray("colors").getValuesAs(JsonString.class))
					card.colors.add(color.getString().toUpperCase());

			card.write(true);
		}

		private List<JsonObject> getExpansionCards(String ex)
		{

			if(this.expansions.containsKey(ex))
				return this.expansions.get(ex);

			List<JsonObject> found = null;
			for(JsonValue expansionValue: this.root.values())
			{
				JsonObject expansion = (JsonObject)expansionValue;
				if(expansion.getString("name", "").equals(ex))
				{
					found = expansion.getJsonArray("cards").getValuesAs(JsonObject.class);
					break;
				}
			}

			this.expansions.put(ex, found);
			return found;
		}

		private String getRarity(String ex, String name)
		{
			List<JsonObject> cards = getExpansionCards(ex);
			if(null != cards)
				for(JsonObject card: cards)
					if(card.getString("name", "").equals(name))
						return card.getString("rarity", null);

			return null;
		}
	}
}
