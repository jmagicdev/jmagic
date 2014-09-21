package org.rnd.jmagic.util;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.abilities.keywords.*;
import org.rnd.jmagic.engine.*;

public class CardShell
{
	private static final java.util.Map<java.util.regex.Pattern, String> keywords = new java.util.HashMap<>();
	private static final java.util.Map<String, String> convenienceMethods = new java.util.HashMap<String, String>();

	private static String getConvenienceMethod(String string)
	{
		if(convenienceMethods.isEmpty())
			populateConvenienceMethods();

		StringBuilder newString = new StringBuilder();
		for(char c: string.toCharArray())
			if(!Character.isWhitespace(c))
				newString.append(Character.toLowerCase(c));

		return convenienceMethods.get(newString.toString().replace("whenever", "when"));
	}

	private static String getCostLine(String cost, String thisName)
	{
		String convenienceMethod = getConvenienceMethod(cost.replace(thisName, "This"));
		if(convenienceMethod != null)
			return "this.addCost(" + convenienceMethod + "(" + (cost.contains(thisName) ? "\"" + thisName + "\"" : "") + "));";

		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^pay (\\d) life$");
		java.util.regex.Matcher matcher = pattern.matcher(cost.toLowerCase());
		if(matcher.lookingAt())
		{
			String amount = matcher.group(1);
			return "this.addEffect(payLife(You.instance(), " + amount + ", \"Pay " + amount + " life.\"));";
		}

		return "// " + cost;
	}

	private static java.util.List<java.util.Set<?>> getTypes(String typeLine)
	{
		java.util.List<java.util.Set<?>> ret = new java.util.LinkedList<java.util.Set<?>>();

		String left = typeLine;
		String right = "";

		boolean hasSubTypes = typeLine.contains(" - ");
		if(hasSubTypes)
		{
			left = left.substring(0, typeLine.indexOf(" - "));
			right = typeLine.replace(left, "");
			while(right.contains(" - "))
				right = right.replace(" - ", " ");
			right = right.trim();
		}

		hasSubTypes = typeLine.contains(" \u2014 ");
		if(hasSubTypes)
		{
			left = left.substring(0, typeLine.indexOf(" \u2014 "));
			right = typeLine.replace(left, "");
			while(right.contains(" \u2014 "))
				right = right.replace(" \u2014 ", " ");
			right = right.trim();
		}

		java.util.Set<SuperType> superTypes = new java.util.HashSet<SuperType>();
		java.util.Set<Type> types = new java.util.HashSet<Type>();

		java.util.Set<SubType> subTypes = new java.util.HashSet<SubType>();

		for(String type: left.split(" "))
		{
			try
			{
				types.add(Type.valueOf(type.toUpperCase()));
			}
			catch(IllegalArgumentException e1)
			{
				try
				{
					superTypes.add(SuperType.valueOf(type.toUpperCase()));
				}
				catch(IllegalArgumentException e2)
				{
					System.out.println("Type/Supertype not found: " + type);
				}
			}
		}

		if(hasSubTypes)
		{
			for(String sub: right.split(" "))
			{
				// God damn this stupid fake apostrophe...
				sub = sub.replace("'", "").replace("�", "");
				try
				{
					subTypes.add(SubType.valueOf(sub.toUpperCase()));
				}
				catch(IllegalArgumentException e)
				{
					System.out.println("Subtype not found: " + sub);
				}
			}
		}

		ret.add(superTypes); // 0
		ret.add(types); // 1
		ret.add(subTypes); // 2

		return ret;
	}

	private static void populateConvenienceMethods()
	{
		for(java.lang.reflect.Method method: org.rnd.jmagic.Convenience.class.getDeclaredMethods())
		{
			StringBuilder newString = new StringBuilder();
			for(char c: method.getName().toCharArray())
				if(!Character.isWhitespace(c))
					newString.append(Character.toLowerCase(c));
			convenienceMethods.put(newString.toString(), method.getName());
		}
		convenienceMethods.put("whenthisoranotherallyentersthebattlefieldunderyourcontrol", "allyTrigger");
		convenienceMethods.put("whenalandentersthebattlefieldunderyourcontrol", "landfall");
		convenienceMethods.put("whenthisandatleasttwoothercreaturesattack", "battalion");
		convenienceMethods.put("whenyoucastaspellthattargetsthis", "heroic");
		convenienceMethods.put("whenthisbecomesuntapped", "inspired");
	}

	private static String simpleInstantiation(Class<? extends Keyword> k)
	{
		return "new " + k.getName().replace('$', '.') + "(state)";
	}

	private static String keywordAndNumber(String keyword)
	{
		return keyword + " (\\d*)";
	}

	private static String keywordAndManaCost(String keyword)
	{
		// Literal () inside capturing () so that the backreference in
		// complexInstantiation picks up the literal ()
		return keyword + " (\\(.*\\))";
	}

	private static String complexInstantiation(Class<? extends Keyword> k)
	{
		return complexInstantiation(k, false);
	}

	private static String complexInstantiation(Class<? extends Keyword> k, boolean includeQuotes)
	{
		String quotes = includeQuotes ? "\"" : "";
		return "new " + k.getName().replace('$', '.') + "(state, " + quotes + "\\1" + quotes + ")";
	}

	private static String withThisNameInstantiation(Class<? extends Identified> ability)
	{
		return "new " + ability.getName() + "(state, this.getName())";
	}

	private static void populateKeywords()
	{
		java.util.Map<String, String> newKeywords = new java.util.HashMap<String, String>();

		// Simple keywords
		newKeywords.put("Battle cry", simpleInstantiation(BattleCry.class));
		newKeywords.put("Cascade", simpleInstantiation(Cascade.class));
		newKeywords.put("Changeling", simpleInstantiation(Changeling.class));
		newKeywords.put("Cipher", simpleInstantiation(Cipher.class));
		newKeywords.put("Convoke", simpleInstantiation(Convoke.class));
		newKeywords.put("Deathtouch", simpleInstantiation(Deathtouch.class));
		newKeywords.put("Defender", simpleInstantiation(Defender.class));
		newKeywords.put("Delve", simpleInstantiation(Delve.class));
		newKeywords.put("Double strike", simpleInstantiation(DoubleStrike.class));
		newKeywords.put("Enchant creature", simpleInstantiation(Enchant.Creature.class));
		newKeywords.put("Enchant land", simpleInstantiation(Enchant.Land.class));
		newKeywords.put("Enchant player", simpleInstantiation(Enchant.Player.class));
		newKeywords.put("Evolve", simpleInstantiation(Evolve.class));
		newKeywords.put("Exalted", simpleInstantiation(Exalted.class));
		newKeywords.put("Extort", simpleInstantiation(Extort.class));
		newKeywords.put("Fear", simpleInstantiation(Fear.class));
		// newKeywords.put("Fuse", simpleInstantiation(Fuse.class));
		newKeywords.put("First strike", simpleInstantiation(FirstStrike.class));
		newKeywords.put("Flanking", simpleInstantiation(Flanking.class));
		newKeywords.put("Flash", simpleInstantiation(Flash.class));
		newKeywords.put("Flying", simpleInstantiation(Flying.class));
		newKeywords.put("Forestwalk", simpleInstantiation(Landwalk.Forestwalk.class));
		newKeywords.put("Haste", simpleInstantiation(Haste.class));
		newKeywords.put("Hexproof", simpleInstantiation(Hexproof.class));
		newKeywords.put("Hideaway", simpleInstantiation(Hideaway.class));
		newKeywords.put("Horsemanship", simpleInstantiation(Horsemanship.class));
		newKeywords.put("Infect", simpleInstantiation(Infect.class));
		newKeywords.put("Indestructible", simpleInstantiation(Indestructible.class));
		newKeywords.put("Intimidate", simpleInstantiation(Intimidate.class));
		newKeywords.put("Islandwalk", simpleInstantiation(Landwalk.Islandwalk.class));
		newKeywords.put("Lifelink", simpleInstantiation(Lifelink.class));
		newKeywords.put("Living weapon", simpleInstantiation(LivingWeapon.class));
		newKeywords.put("Mountainwalk", simpleInstantiation(Landwalk.Mountainwalk.class));
		newKeywords.put("Persist", simpleInstantiation(Persist.class));
		newKeywords.put("Plainswalk", simpleInstantiation(Landwalk.Plainswalk.class));
		newKeywords.put("Provoke", simpleInstantiation(Provoke.class));
		newKeywords.put("Prowess", simpleInstantiation(Prowess.class));
		newKeywords.put("Reach", simpleInstantiation(Reach.class));
		newKeywords.put("Rebound", simpleInstantiation(Rebound.class));
		newKeywords.put("Shadow", simpleInstantiation(Shadow.class));
		newKeywords.put("Shroud", simpleInstantiation(Shroud.class));
		newKeywords.put("Soulbond", simpleInstantiation(Soulbond.class));
		newKeywords.put("Split second", simpleInstantiation(SplitSecond.class));
		newKeywords.put("Storm", simpleInstantiation(Storm.class));
		newKeywords.put("Sunburst", simpleInstantiation(Sunburst.class));
		newKeywords.put("Swampwalk", simpleInstantiation(Landwalk.Swampwalk.class));
		newKeywords.put("Totem armor", simpleInstantiation(TotemArmor.class));
		newKeywords.put("Trample", simpleInstantiation(Trample.class));
		newKeywords.put("Undying", simpleInstantiation(Undying.class));
		newKeywords.put("Unleash", simpleInstantiation(Unleash.class));
		newKeywords.put("Vigilance", simpleInstantiation(Vigilance.class));
		newKeywords.put("Wither", simpleInstantiation(Wither.class));

		// Complex keywords
		newKeywords.put(keywordAndNumber("Absorb"), complexInstantiation(Absorb.class));
		newKeywords.put(keywordAndNumber("Annihilator"), complexInstantiation(Annihilator.class));
		newKeywords.put(keywordAndManaCost("Bestow"), complexInstantiation(Bestow.class, true));
		newKeywords.put(keywordAndNumber("Bloodthirst"), complexInstantiation(Bloodthirst.Final.class));
		newKeywords.put(keywordAndNumber("Bushido"), complexInstantiation(Bushido.class));
		newKeywords.put(keywordAndManaCost("Cumulative upkeep"), complexInstantiation(CumulativeUpkeep.ForMana.class, true));
		newKeywords.put(keywordAndManaCost("Cycling"), complexInstantiation(Cycling.class, true));
		newKeywords.put(keywordAndNumber("Devour"), complexInstantiation(Devour.class));
		newKeywords.put(keywordAndNumber("Dredge"), complexInstantiation(Dredge.class));
		newKeywords.put(keywordAndManaCost("Echo"), complexInstantiation(Echo.class, true));
		newKeywords.put(keywordAndManaCost("Entwine"), complexInstantiation(Entwine.class, true));
		newKeywords.put(keywordAndManaCost("Equip"), complexInstantiation(Equip.class, true));
		newKeywords.put(keywordAndManaCost("Evoke"), complexInstantiation(Evoke.class, true));
		newKeywords.put(keywordAndManaCost("Flashback"), complexInstantiation(Flashback.class, true));
		newKeywords.put(keywordAndManaCost("Fortify"), complexInstantiation(Fortify.class, true));
		newKeywords.put(keywordAndNumber("Fading"), complexInstantiation(Fading.class));
		newKeywords.put(keywordAndNumber("Frenzy"), complexInstantiation(Frenzy.class));
		newKeywords.put(keywordAndNumber("Graft"), complexInstantiation(Graft.class));
		newKeywords.put(keywordAndManaCost("Level up"), complexInstantiation(LevelUp.class, true));
		newKeywords.put(keywordAndManaCost("Miracle"), complexInstantiation(Miracle.class, true));
		newKeywords.put(keywordAndNumber("Modular"), complexInstantiation(Modular.class));
		newKeywords.put(keywordAndManaCost("Morph"), complexInstantiation(Morph.class, true));
		// the "true" in multikicker's constructor throws a wrench in this
		// little scheme
		newKeywords.put("Multikicker \\((.*)\\)", "new org.rnd.jmagic.abilities.keywords.Kicker(state, true, \"(\\1)\")");
		newKeywords.put(keywordAndManaCost("Ninjutsu"), complexInstantiation(Ninjutsu.class, true));
		newKeywords.put(keywordAndManaCost("Outlast"), complexInstantiation(Outlast.class, true));
		newKeywords.put(keywordAndManaCost("Overload"), complexInstantiation(Overload.class, true));
		newKeywords.put(keywordAndNumber("Poisonous"), complexInstantiation(Poisonous.class));
		newKeywords.put(keywordAndManaCost("Prowl"), complexInstantiation(Prowl.class, true));
		newKeywords.put(keywordAndNumber("Rampage"), complexInstantiation(Rampage.class));
		newKeywords.put(keywordAndManaCost("Recover"), complexInstantiation(Recover.ForMana.class, true));
		newKeywords.put(keywordAndManaCost("Replicate"), complexInstantiation(Replicate.class, true));
		newKeywords.put(keywordAndManaCost("Scavenge"), complexInstantiation(Scavenge.class, true));
		newKeywords.put(keywordAndNumber("Soulshift"), complexInstantiation(Soulshift.class));
		newKeywords.put(keywordAndManaCost("Transmute"), complexInstantiation(Transmute.class, true));
		newKeywords.put(keywordAndNumber("Tribute"), complexInstantiation(Tribute.class));
		newKeywords.put(keywordAndManaCost("Unearth"), complexInstantiation(Unearth.class, true));

		for(java.util.Map.Entry<String, String> entry: newKeywords.entrySet())
			keywords.put(java.util.regex.Pattern.compile(entry.getKey(), java.util.regex.Pattern.CASE_INSENSITIVE), entry.getValue());

		// Not really keywords
		java.util.Map<String, String> newAbilityWords = new java.util.HashMap<String, String>();

		newAbilityWords.put("This enters the battlefield tapped.", withThisNameInstantiation(EntersTheBattlefieldTapped.class));
		newAbilityWords.put("This can't block.", withThisNameInstantiation(CantBlock.class));
		newAbilityWords.put("This is unblockable.", withThisNameInstantiation(Unblockable.class));
		/* This doesn't work because it has a comma */
		// keywords.put("If this is in your opening hand, you may begin the game with it on the battlefield.",
		// withThisNameInstantiation(LeylineAbility.class));
		newAbilityWords.put("This costs (\\(.*\\)) more to cast for each target beyond the first.", "new Strive(state, this.getName(), \"\\1\")");

		for(java.util.Map.Entry<String, String> entry: newAbilityWords.entrySet())
			keywords.put(java.util.regex.Pattern.compile(entry.getKey(), java.util.regex.Pattern.CASE_INSENSITIVE), entry.getValue());
	}

	private static String removeReminderText(String ability)
	{
		int start = 0;
		outer: while(true)
		{
			int found = -1;
			boolean anotherFound = false;
			for(int i = start; i < ability.length(); ++i)
			{
				char c = ability.charAt(i);
				if(found == -1)
				{
					if(c == '(')
						found = i;
				}
				else
				{
					if(c == ')')
					{
						if(anotherFound)
							anotherFound = false;
						else
						{
							// Don't catch things like (u/r)
							if(i - found > 4)
							{
								// Leave off the space character before the
								// reminder text as well if it isn't the first
								// character
								if(0 != found)
									--found;

								ability = ability.substring(0, found) + ability.substring(i + 1);
								start = found;
								continue outer;
							}
							found = -1;
						}
					}
					else if(c == '(')
						anotherFound = true;
				}
			}
			break;
		}

		int dashIndex = ability.indexOf("\\u2014");
		if(dashIndex >= 0 && !ability.startsWith("Choose "))
		{
			ability = ability.substring(dashIndex + 6).trim();
		}

		return ability;
	}

	private static String replaceIllegalCharacters(String string)
	{
		String ret = string;
		for(java.util.Map.Entry<String, String> entry: org.rnd.jmagic.CardLoader.NON_ASCII_REPLACE.entrySet())
			ret = ret.replace(entry.getKey(), entry.getValue());
		return ret;
	}

	public java.util.LinkedList<String> abilities = new java.util.LinkedList<String>();
	public java.util.Set<String> colorIndicator = new java.util.HashSet<String>();
	public java.util.Set<String> colors = null;
	public String loyalty = null;
	public String manaCost = null;
	public String name = null;
	public String power = null;
	public String printings = null;
	public String toughness = null;
	public String types = null;

	private java.util.Set<String> colorIdentity()
	{
		java.util.Set<String> ret = new java.util.HashSet<String>();

		if(null != this.colors)
		{
			ret.addAll(this.colors);
		}
		else
		{
			if(this.manaCost != null)
				for(char c: this.manaCost.toUpperCase().toCharArray())
				{
					for(Color color: Color.values())
						if(color.getLetter().equals(Character.toString(c)))
							ret.add(color.toString().toUpperCase());
				}

			for(String color: this.colorIndicator)
				ret.add(color.toUpperCase());
		}

		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\([WUBRG/0-9P]+\\)");
		for(String string: this.abilities)
		{
			java.util.regex.Matcher matcher = pattern.matcher(string);
			while(matcher.find())
			{
				String match = matcher.group();
				for(Color color: Color.values())
					if(match.contains(color.getLetter()))
						ret.add(color.toString().toUpperCase());
			}
		}

		return ret;
	}

	/**
	 * Write this CardShell to the correct Java source file.
	 *
	 * @return Whether or not the card already existed
	 */
	@SuppressWarnings("unchecked")
	public boolean write(boolean updatePrintings)
	{
		for(int i = 0; i < this.abilities.size(); ++i)
			this.abilities.set(i, replaceIllegalCharacters(this.abilities.get(i)));
		String nameInFile = replaceIllegalCharacters(this.name);

		String className = org.rnd.jmagic.CardLoader.formatName(this.name);
		java.io.File card = new java.io.File("../cards/src/main/java/org/rnd/jmagic/cards/" + className + ".java");
		if(card.exists())
			return false;

		java.util.List<java.util.Set<?>> info = getTypes(this.types);

		String anyNumber = "";
		if(info.get(0).contains(SuperType.BASIC))
			anyNumber = " implements AnyNumberInADeck";

		java.io.BufferedWriter out = null;
		try
		{
			boolean addable = false;

			java.util.Set<SuperType> superTypeSet = (java.util.Set<SuperType>)info.get(0);
			java.util.Set<Type> typeSet = (java.util.Set<Type>)info.get(1);
			java.util.Set<SubType> subTypeSet = (java.util.Set<SubType>)info.get(2);

			java.util.SortedSet<String> imports = new java.util.TreeSet<>();
			imports.add("org.rnd.jmagic.engine.*");
			imports.add("org.rnd.jmagic.engine.generators.*");

			StringBuilder abilityCode = new StringBuilder();

			int abilityNumber = 0;
			java.util.Map<Integer, java.util.List<String>> generatedAbilities = new java.util.HashMap<Integer, java.util.List<String>>();
			if(this.types.startsWith("Instant") || this.types.startsWith("Sorcery"))
			{
				java.util.Iterator<String> abilityIterator = this.abilities.iterator();
				while(abilityIterator.hasNext())
				{
					String ability = removeReminderText(abilityIterator.next());
					if(0 == ability.length())
					{
						abilityIterator.remove();
						continue;
					}

					java.util.List<String> abilityConstructors = new java.util.LinkedList<String>();

					ability = ability.trim().replace(nameInFile, "this");

					// Try handling keywords
					if(keywords.isEmpty())
						populateKeywords();

					for(java.util.regex.Pattern pattern: keywords.keySet())
					{
						java.util.regex.Matcher match = pattern.matcher(ability);
						if(match.matches())
						{
							String construction = keywords.get(pattern);
							for(int i = 1; i <= match.groupCount(); ++i)
								construction = construction.replace("\\" + i, match.group(i));
							abilityConstructors.add(construction);
						}
					}

					if(!abilityConstructors.isEmpty())
						generatedAbilities.put(abilityNumber, abilityConstructors);
					// If it's not a keyword ability, assume it's an effect and
					// don't generate anything

					++abilityNumber;
				}
			}
			else
			{
				addable = true;
				java.util.Iterator<String> abilityIterator = this.abilities.iterator();
				while(abilityIterator.hasNext())
				{
					String ability = removeReminderText(abilityIterator.next());
					if(0 == ability.length())
					{
						abilityIterator.remove();
						continue;
					}

					int firstQuote = ability.indexOf("\"");
					int firstColon = ability.indexOf(":");
					boolean triggered = ability.startsWith("When ") || ability.startsWith("Whenever ") || ability.startsWith("At ") || ability.startsWith("Landfall ");
					if((-1 != firstColon) && ((-1 == firstQuote) || (firstColon < firstQuote)))
					{
						addable = false;
						String abilityClassName = className + "Ability" + abilityNumber;
						if(typeSet.contains(Type.PLANESWALKER))
							abilityCode.append("\tpublic static final class " + abilityClassName + " extends LoyaltyAbility\r\n");
						else
							abilityCode.append("\tpublic static final class " + abilityClassName + " extends ActivatedAbility\r\n");
						abilityCode.append("\t{\r\n");
						abilityCode.append("\t\tpublic " + abilityClassName + "(GameState state)\r\n");
						abilityCode.append("\t\t{\r\n");
						if(typeSet.contains(Type.PLANESWALKER))
							abilityCode.append("\t\t\tsuper(state, " + ability.substring(0, firstColon) + ", \"" + ability.substring(firstColon + 2).replace("\"", "\\\"") + "\");\r\n");
						// Only process costs if this isn't a loyalty ability
						else
						{
							abilityCode.append("\t\t\tsuper(state, \"" + ability.replace("\"", "\\\"") + "\");\r\n");

							String costs = ability.substring(0, firstColon);
							for(String cost: costs.split(","))
							{
								cost = cost.trim();
								if(cost.equals("(T)"))
									abilityCode.append("\t\t\tthis.costsTap = true;\r\n");
								else if(cost.equals("(Q)"))
									abilityCode.append("\t\t\tthis.costsUntap = true;\r\n");
								else if(cost.startsWith("("))
									abilityCode.append("\t\t\tthis.setManaCost(new ManaPool(\"" + cost + "\"));\r\n");
								else
									abilityCode.append("\t\t\t" + getCostLine(cost, nameInFile) + "\r\n");
							}
						}

						abilityCode.append("\t\t}\r\n");
						abilityCode.append("\t}\r\n");
						abilityCode.append("\r\n");
						generatedAbilities.put(abilityNumber, java.util.Collections.singletonList("new " + abilityClassName + "(state)"));
					}
					else if(triggered)
					{
						addable = false;
						String abilityClassName = className + "Ability" + abilityNumber;
						abilityCode.append("\tpublic static final class " + abilityClassName + " extends EventTriggeredAbility\r\n");
						abilityCode.append("\t{\r\n");
						abilityCode.append("\t\tpublic " + abilityClassName + "(GameState state)\r\n");
						abilityCode.append("\t\t{\r\n");
						abilityCode.append("\t\t\tsuper(state, \"" + ability.replace("\"", "\\\"") + "\");\r\n");

						String replacedAbility = ability.replace(nameInFile, "This");
						String triggerCondition = replacedAbility.substring(0, replacedAbility.indexOf(","));
						String method = getConvenienceMethod(triggerCondition);
						if(method != null)
							abilityCode.append("\t\t\tthis.addPattern(" + method + "());\r\n");

						abilityCode.append("\t\t}\r\n");
						abilityCode.append("\t}\r\n");
						abilityCode.append("\r\n");
						generatedAbilities.put(abilityNumber, java.util.Collections.singletonList("new " + abilityClassName + "(state)"));
					}
					else
					{
						java.util.List<String> abilityConstructors = new java.util.LinkedList<String>();
						for(String seperateAbility: ability.split(","))
						{
							seperateAbility = seperateAbility.trim().replace(nameInFile, "this");

							// Try handling keywords
							if(keywords.isEmpty())
								populateKeywords();

							boolean found = false;
							for(java.util.regex.Pattern pattern: keywords.keySet())
							{
								java.util.regex.Matcher match = pattern.matcher(seperateAbility);
								if(match.matches())
								{
									String construction = keywords.get(pattern);
									for(int i = 1; i <= match.groupCount(); ++i)
										construction = construction.replace("\\" + i, match.group(i));
									abilityConstructors.add(construction);
								}
							}

							// If we don't find one, quit. This avoids problems
							// with abilities like "Enchanted creature gains
							// fear, haste, and vigilance" being parsed for
							// commas incorrectly.
							if(!found)
								break;
						}

						if(!abilityConstructors.isEmpty())
							generatedAbilities.put(abilityNumber, abilityConstructors);
						else
						{
							addable = false;
							// If it's not an activated ability, triggered
							// ability, or keyword ability, assume it's a static
							// ability
							String abilityClassName = className + "Ability" + abilityNumber;
							abilityCode.append("\tpublic static final class " + abilityClassName + " extends StaticAbility\r\n");
							abilityCode.append("\t{\r\n");
							abilityCode.append("\t\tpublic " + abilityClassName + "(GameState state)\r\n");
							abilityCode.append("\t\t{\r\n");
							abilityCode.append("\t\t\tsuper(state, \"" + ability.replace("\"", "\\\"") + "\");\r\n");
							abilityCode.append("\t\t}\r\n");
							abilityCode.append("\t}\r\n");
							abilityCode.append("\r\n");
							generatedAbilities.put(abilityNumber, java.util.Collections.singletonList("new " + abilityClassName + "(state)"));
						}
					}
					++abilityNumber;
				}
			}

			out = new java.io.BufferedWriter(new java.io.FileWriter(card));
			out.write("package org.rnd.jmagic.cards;\r\n\r\n");
			out.write("import static org.rnd.jmagic.Convenience.*;\r\n\r\n");

			for(String pkg: imports)
				out.write("import " + pkg + ";\r\n");

			out.write("\r\n");

			out.write("@Name(\"" + nameInFile + "\")\n");

			if(!superTypeSet.isEmpty())
			{
				out.write("@SuperTypes({");
				boolean first = true;
				for(SuperType type: superTypeSet)
				{
					if(first)
						first = false;
					else
						out.write(",");
					out.write("SuperType." + type.name());
				}
				out.write("})\r\n");
			}

			if(!typeSet.isEmpty())
			{
				out.write("@Types({");
				boolean first = true;
				for(Type type: typeSet)
				{
					if(first)
						first = false;
					else
						out.write(",");
					out.write("Type." + type.name());
				}
				out.write("})\r\n");
			}

			if(!subTypeSet.isEmpty())
			{
				out.write("@SubTypes({");
				boolean first = true;
				for(SubType type: subTypeSet)
				{
					if(first)
						first = false;
					else
						out.write(",");
					out.write("SubType." + type.name());
				}
				out.write("})\r\n");
			}

			if(this.manaCost != null)
			{
				out.write("@ManaCost(\"" + this.manaCost + "\")\r\n");
			}

			out.write("@ColorIdentity({");
			java.util.Set<String> identity = this.colorIdentity();
			if(!identity.isEmpty())
			{
				out.write("Color." + org.rnd.util.SeparatedList.get(", Color.", "", this.colorIdentity()).toString());
			}
			out.write("})\r\n");

			out.write("public final class " + className + " extends Card" + anyNumber + "\r\n{\r\n");

			out.write(abilityCode.toString());

			out.write("\tpublic " + className + "(GameState state)\r\n\t{\r\n");
			out.write("\t\tsuper(state);\r\n\r\n");

			if(this.power != null)
			{
				out.write("\t\tthis.setPower(" + this.power + ");\r\n");
			}
			if(this.toughness != null)
			{
				out.write("\t\tthis.setToughness(" + this.toughness + ");\r\n");
			}
			if(this.loyalty != null)
			{
				out.write("\t\tthis.setPrintedLoyalty(" + this.loyalty + ");\r\n");
			}
			if(!this.colorIndicator.isEmpty())
			{
				out.write("\r\n\t\tthis.setColorIndicator(");
				boolean first = true;
				for(String color: this.colorIndicator)
				{
					if(first)
						first = false;
					else
						out.write(", ");
					out.write("Color." + color.toUpperCase());
				}
				out.write(");\r\n");
			}

			abilityNumber = 0;
			for(String ability: this.abilities)
			{
				if(0 != ability.length())
				{
					out.write("\r\n");
					out.write("\t\t// " + ability + "\r\n");
					if(generatedAbilities.containsKey(abilityNumber))
						for(String string: generatedAbilities.get(abilityNumber))
							out.write("\t\tthis.addAbility(" + string + ");\r\n");
				}
				++abilityNumber;
			}

			out.write("\t}\r\n");
			out.write("}\r\n");
			out.flush();
			out.close();

			if(addable)
			{
				System.out.println("Adding " + this.name);
				try
				{
					Process p = Runtime.getRuntime().exec("git add " + card.getPath());
					p.waitFor();

					for(java.io.InputStream stream: new java.io.InputStream[] {p.getInputStream(), p.getErrorStream()})
					{
						java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(stream));

						String line;
						while((line = reader.readLine()) != null)
							System.out.println(line);
					}
				}
				catch(InterruptedException ex)
				{
					System.out.println("Interrupted");
				}
			}

			return true;
		}
		catch(java.io.IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
