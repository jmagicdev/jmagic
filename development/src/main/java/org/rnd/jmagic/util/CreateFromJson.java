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
			jsonLocation = new java.net.URL("http://mtgjson.com/json/AllSets-x.json");
		JsonReader jsonReader = Json.createReader(jsonLocation.openStream());
		JsonObject root = jsonReader.readObject();

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

		cards.forEach(new JsonConsumer());
	}

	public static class JsonConsumer implements Consumer<JsonValue>
	{
		@Override
		public void accept(JsonValue jsonValue)
		{
			JsonObject json = (JsonObject)jsonValue;
			CardShell card = new CardShell();

			card.name = json.getString("name");

			JsonValue manaCost = json.getOrDefault("manaCost", JsonValue.NULL);
			if(!manaCost.equals(JsonValue.NULL))
			{
				card.manaCost = manaCost.toString().replace('{', '(').replace('}', ')').replaceAll("\\((.)\\)", "$1");
				card.manaCost = card.manaCost.substring(1, card.manaCost.length() - 1);
			}

			JsonValue type = json.getOrDefault("type", JsonValue.NULL);
			if(!type.equals(JsonValue.NULL))
			{
				card.types = type.toString();
				card.types = card.types.substring(1, card.types.length() - 1);
			}

			JsonValue text = json.getOrDefault("text", JsonValue.NULL);
			if(!text.equals(JsonValue.NULL))
			{
				String abilityText = text.toString().replace("\\\"", "\"").replace("−", "-");
				// Remove quotes
				abilityText = abilityText.substring(1, abilityText.length() - 1);
				for(String abilityLine: abilityText.split("(\\\\[rn])+"))
					card.abilities.add(abilityLine);
			}

			JsonValue power = json.getOrDefault("power", JsonValue.NULL);
			if(!power.equals(JsonValue.NULL))
			{
				card.power = power.toString().replace("−", "-");
				card.power = card.power.substring(1, card.power.length() - 1);
			}

			JsonValue toughness = json.getOrDefault("toughness", JsonValue.NULL);
			if(!toughness.equals(JsonValue.NULL))
			{
				card.toughness = toughness.toString().replace("−", "-");
				card.toughness = card.toughness.substring(1, card.toughness.length() - 1);
			}

			JsonValue loyalty = json.getOrDefault("loyalty", JsonValue.NULL);
			if(!loyalty.equals(JsonValue.NULL))
				card.loyalty = loyalty.toString().replace("−", "-");

			card.expansions = new HashMap<String, String>();
			String rarity = json.getString("rarity");
			for(JsonValue value: json.getJsonArray("printings"))
			{
				String ex = value.toString();
				card.expansions.put(ex.substring(1, ex.length() - 1), rarity);
			}

			card.colors = new HashSet<String>();
			if(json.containsKey("colors"))
				for(JsonValue color: json.getJsonArray("colors"))
				{
					String c = color.toString();
					c = c.substring(1, c.length() - 1);
					card.colors.add(c.toUpperCase());
				}

			card.write(true);
		}
	}
}
