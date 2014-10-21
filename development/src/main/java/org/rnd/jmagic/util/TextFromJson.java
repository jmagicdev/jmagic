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
		counted.add("Cast ~ only during your declare attackers step.\nThis turn, instead of declaring blockers, each defending player chooses any number of creatures he or she controls and divides them into a number of piles equal to the number of attacking creatures for whom that player is the defending player. Creatures he or she controls that can block additional creatures may likewise be put into additional piles. Assign each pile to a different one of those attacking creatures at random. Each creature in a pile that can block the creature that pile is assigned to does so.");
		// complicated
		counted.add("When ~ enters the battlefield, if it's on the battlefield, it loses \"enchant creature card in a graveyard\" and gains \"enchant creature put onto the battlefield with ~.\" Return enchanted creature card to the battlefield under your control and attach ~ to it. When ~ leaves the battlefield, that creature's controller sacrifices it.");
		// delayed triggers
		counted.add("When ~ is put into a graveyard from the battlefield, at the beginning of each of your upkeeps for the rest of the game, remove all mire counters from a land that a mire counter was put onto with ~ but that a mire counter has not been removed from with ~.");
		// ...height
		counted.add("{1}, {T}: If ~ is on the battlefield, flip ~ onto the battlefield from a height of at least one foot. If ~ turns over completely at least once during the flip, destroy all nontoken permanents it touches. Then destroy ~.");
		// ante
		counted.add("{T}: Ante ~. If you do, put all other cards you own from the ante into your graveyard, then draw a card.");
		// "the same way" may be hard to handle programmatically
		counted.add("Each player chooses a number of lands he or she controls equal to the number of lands controlled by the player who controls the fewest, then sacrifices the rest. Players discard cards and sacrifice creatures the same way.");

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
					text = CardShell.removeReminderText(text.replace(card.getString("name", "[name]"), "~"));
					if(!text.isEmpty() && !counted.contains(text))
					{
						counted.add(text);

						boolean spell = false;
						for(JsonString type: card.getJsonArray("types").getValuesAs(JsonString.class))
						{
							if(type.getString().equals("Instant") || type.getString().equals("Sorcery"))
							{
								spell = true;
								break;
							}
						}

						try
						{
							if(spell)
								org.rnd.jmagic.cardscript.AbilityParser.parseSpellAbility(text);
							else
								org.rnd.jmagic.cardscript.AbilityParser.parsePermanentAbility(text);

						}
						catch(org.rnd.jmagic.cardscript.ParseException | org.rnd.jmagic.cardscript.TokenMgrError e)
						{
							System.out.println("Correctly Parsed: " + counted.size());
							System.out.println();
							System.out.println(text);
							System.out.flush();
							try
							{
								Thread.sleep(5);
							}
							catch(InterruptedException e1)
							{
								//
							}
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