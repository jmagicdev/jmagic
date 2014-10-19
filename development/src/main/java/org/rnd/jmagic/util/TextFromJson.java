package org.rnd.jmagic.util;

import java.net.*;

import javax.json.*;

public class TextFromJson
{
	public static void main(String args[]) throws java.io.IOException, URISyntaxException
	{
		java.net.URL jsonLocation = CreateFromJson.class.getResource("AllSets.json");
		if(null == jsonLocation)
			return;

		JsonReader jsonReader = Json.createReader(jsonLocation.openStream());
		JsonObject root = jsonReader.readObject();

		java.util.Set<String> counted = new java.util.HashSet<String>();

		// ignore these strings because...
		// piles
		counted.add("Whenever one or more creatures you control attack, each defending player divides all creatures without flying he or she controls into a \"left\" pile and a \"right\" pile. Then, for each attacking creature you control, choose \"left\" or \"right.\" That creature can't be blocked this combat except by creatures with flying and creatures in a pile with the chosen label.");
		// complicated
		counted.add("When ~ enters the battlefield, if it's on the battlefield, it loses \"enchant creature card in a graveyard\" and gains \"enchant creature put onto the battlefield with ~.\" Return enchanted creature card to the battlefield under your control and attach ~ to it. When ~ leaves the battlefield, that creature's controller sacrifices it.");
		// delayed triggers
		counted.add("When ~ is put into a graveyard from the battlefield, at the beginning of each of your upkeeps for the rest of the game, remove all mire counters from a land that a mire counter was put onto with ~ but that a mire counter has not been removed from with ~.");
		// ...height
		counted.add("{1}, {T}: If ~ is on the battlefield, flip ~ onto the battlefield from a height of at least one foot. If ~ turns over completely at least once during the flip, destroy all nontoken permanents it touches. Then destroy ~.");
		// ante
		counted.add("{T}: Ante ~. If you do, put all other cards you own from the ante into your graveyard, then draw a card.");

		for(java.util.Map.Entry<String, JsonValue> expansionEntry: root.entrySet())
		{
			JsonObject expansion = (JsonObject)expansionEntry.getValue();
			JsonArray cards = expansion.getJsonArray("cards");
			if(cards == null)
				continue;

			for(JsonObject card: cards.getValuesAs(JsonObject.class))
			{
				String text = card.getString("text", null);
				if(null != text)
				{
					JsonArray types = card.getJsonArray("types");
					if(types == null)
						throw new RuntimeException("no types found");
					boolean permanent = !(types.contains("Instant") || types.contains("Sorcery"));
					for(String ability: text.replace(card.getString("name", "[name]"), "~").split("\n"))
					{
						ability = CardShell.removeReminderText(ability);
						if(!ability.isEmpty() && !counted.contains(ability))
						{
							counted.add(ability);
							try
							{
								if(permanent)
									org.rnd.jmagic.cardscript.AbilityParser.parsePermanentAbility(ability);
								else
									org.rnd.jmagic.cardscript.AbilityParser.parseSpellAbility(ability);

							}
							catch(org.rnd.jmagic.cardscript.ParseException e)
							{
								System.out.println("Correctly Parsed: " + counted.size());
								System.out.println(ability);
								System.out.flush();
								System.err.println(e.getMessage());
								System.err.flush();
								return;
							}
						}
					}
				}
			}
		}
	}
}