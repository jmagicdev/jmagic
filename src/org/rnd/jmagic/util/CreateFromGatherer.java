package org.rnd.jmagic.util;

import org.rnd.jmagic.engine.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.*;
import org.springframework.core.type.filter.*;

public class CreateFromGatherer
{
	public static void main(String args[]) throws java.io.IOException, java.net.URISyntaxException
	{
		if(args.length < 1)
		{
			System.err.println("Must specify --deck, --set, --path or --expansions");
			return;
		}

		String path = null;
		boolean deckMode = false;
		boolean setMode = false;
		boolean expansionMode = false;

		java.util.Set<String> names = null;

		if(args[0].equals("--deck"))
		{
			System.out.println("Please input one card name per line and press Ctrl+D on Linux or Ctrl+Z on Windows to finish");
			deckMode = true;
		}
		else if(args[0].equals("--path"))
		{
			if(args.length < 2)
			{
				System.err.println("Must specify either a file to read or a directory to recursively descend");
				return;
			}

			path = args[1];
			deckMode = true;
		}
		else if(args[0].equals("--set"))
		{
			System.out.println("Please input one set name per line and press Ctrl+D on Linux or Ctrl+Z on Windows to finish");
			setMode = true;
		}
		else if(args[0].equals("--expansions"))
		{
			System.out.println("Updating expansions for existing cards...");
			expansionMode = true;

			names = new java.util.HashSet<String>();

			ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
			scanner.addIncludeFilter(new AssignableTypeFilter(Card.class));
			scanner.addExcludeFilter(new AssignableTypeFilter(AlternateCard.class));

			java.util.Set<BeanDefinition> components = scanner.findCandidateComponents("org.rnd.jmagic.cards");
			for(BeanDefinition bean: components)
				try
				{
					names.add(Class.forName(bean.getBeanClassName()).getAnnotation(Name.class).value());
				}
				catch(ClassNotFoundException e)
				{
					//
				}
		}
		else
		{
			System.err.println("Invalid option; must specify --deck, --path, --set or --expansions");
			return;
		}

		if(names == null)
		{
			if(null == path)
				names = readNames(new java.io.BufferedReader(new java.io.InputStreamReader(System.in)));
			else
				names = readPath(new java.io.File(path));
		}

		java.util.Map<String, CardShell> cardsSkipped = new java.util.TreeMap<String, CardShell>();
		java.util.Map<String, CardShell> cardsWritten = new java.util.TreeMap<String, CardShell>();

		// Eliminate any files we already have unless we're updating due to a
		// new set
		if(deckMode)
		{
			java.util.Iterator<String> i = names.iterator();
			while(i.hasNext())
			{
				String cardName = i.next();
				if(new java.io.File("src/org/rnd/jmagic/cards/" + org.rnd.jmagic.CardLoader.formatName(cardName) + ".java").exists())
				{
					cardsSkipped.put(cardName, null);
					i.remove();
				}
			}
		}

		int queryCount = 0;
		StringBuilder query = new StringBuilder();
		for(String name: names)
		{
			if(0 == queryCount)
			{
				if(setMode)
					query.append("set=");
				else
					query.append("name=");
			}
			query.append("|[\"");
			query.append(name);
			query.append("\"]");
			++queryCount;

			if(20 == queryCount)
			{
				if(setMode)
					readQuery(cardsSkipped, cardsWritten, null, query.toString(), true);
				else
					readQuery(cardsSkipped, cardsWritten, names, query.toString(), expansionMode);

				queryCount = 0;
				query = new StringBuilder();
			}
		}
		if(0 != queryCount)
		{
			if(setMode)
				readQuery(cardsSkipped, cardsWritten, null, query.toString(), true);
			else
				readQuery(cardsSkipped, cardsWritten, names, query.toString(), expansionMode);
		}

		System.out.println(cardsSkipped.size() + " cards skipped:");
		for(java.util.Map.Entry<String, CardShell> entry: cardsSkipped.entrySet())
		{
			String name = entry.getKey();
			names.remove(name);
			System.out.println("\t" + name);
		}

		System.out.println(cardsWritten.size() + " cards written:");
		for(java.util.Map.Entry<String, CardShell> entry: cardsWritten.entrySet())
		{
			String name = entry.getKey();
			names.remove(name);
			System.out.println("\t" + name);
		}

		if(deckMode)
		{
			System.out.println(names.size() + " cards missing: (probably misspelled)");
			for(String name: names)
				System.out.println("\t" + name);
		}
	}

	private static java.util.Set<String> readNames(java.io.BufferedReader reader) throws java.io.IOException
	{
		java.util.Set<String> names = new java.util.HashSet<String>();

		org.rnd.jmagic.engine.Deck deck = org.rnd.jmagic.CardLoader.getDeck(reader);
		for(java.util.List<String> section: deck.publicCardMap.values())
			names.addAll(section);

		return names;
	}

	private static java.util.Set<String> readPath(java.io.File path) throws java.io.IOException
	{
		java.util.Set<String> names;
		if(path.isDirectory())
		{
			names = new java.util.HashSet<String>();
			for(java.io.File sub: path.listFiles())
				if(!sub.getAbsolutePath().endsWith(".svn") && !sub.getAbsolutePath().endsWith("_svn"))
					names.addAll(readPath(sub));
		}
		else
			names = readNames(new java.io.BufferedReader(new java.io.FileReader(path)));
		return names;
	}

	private static void readQuery(java.util.Map<String, CardShell> cardsSkipped, java.util.Map<String, CardShell> cardsWritten, java.util.Set<String> names, String query, boolean updatePrintings) throws java.io.IOException, java.net.URISyntaxException
	{
		java.net.URI unencodedURI = new java.net.URI("http", null, "gatherer.wizards.com", -1, "/Pages/Search/Default.aspx", query, null);
		java.net.URI encodedURI = new java.net.URI(unencodedURI.toASCIIString());
		java.net.URLConnection connection = encodedURI.toURL().openConnection();
		// Somewhere in this cookie, the text-output format and not redirecting
		// to the card's page on a single result are specified
		connection.setRequestProperty("Cookie", "CardDatabaseSettings=0=1&1=28&2=0&14=1&3=13&4=0&5=1&6=15&7=0&8=1&9=1&10=17&11=4&12=8&15=1&16=1&13=");
		java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream(), "UTF-8"));

		CardShell card = null;
		String lineType = null;
		boolean inSpoiler = false;

		String line = input.readLine();
		while(null != line)
		{
			if(!inSpoiler)
			{
				if(line.matches(".*<div class=\"textspoiler\">.*"))
					inSpoiler = true;
			}
			else
			{
				if(line.matches(".*</div>.*"))
					inSpoiler = false;
				else if(line.matches(".*</tr>.*"))
					lineType = null;
				else
				{
					// Get rid of any HTML tags
					line = line.replaceAll("<.*?>", "").trim();
					if(0 != line.length())
					{
						if("Name".equals(lineType))
						{
							if((null != card) && ((null == names) || names.contains(card.name)))
							{
								if(card.write(updatePrintings))
									cardsWritten.put(card.name, card);
								else
									cardsSkipped.put(card.name, card);
							}
							card = new CardShell();
							card.name = line;
						}
						else if("Cost:".equals(lineType))
						{
							int num = -1;
							String newManaCost = "";
							for(char c: line.toCharArray())
							{
								if(Character.isDigit(c))
								{
									if(num == -1)
										num = 0;
									num = num * 10 + Integer.parseInt(Character.toString(c));
								}
								else
								{
									if(num >= 0)
									{
										if(num >= 10)
											newManaCost += "(" + num + ")";
										else
											newManaCost += num;
										num = -1;
									}
									newManaCost += c;
								}
							}

							if(num >= 0)
							{
								if(num >= 10)
									newManaCost += "(" + num + ")";
								else
									newManaCost += num;
								num = -1;
							}

							card.manaCost = newManaCost;
						}
						else if("Type:".equals(lineType))
							card.types = line;
						else if("Pow/Tgh:".equals(lineType))
						{
							card.power = line.substring(0, line.indexOf("/")).replace("(", "");
							card.toughness = line.substring(line.indexOf("/") + 1).replace(")", "");
						}
						else if("Loyalty:".equals(lineType))
							card.loyalty = line;
						else if("Rules Text:".equals(lineType))
							card.abilities.add(line.replace("{(", "(").replace(")}", ")").replace('{', '(').replace('}', ')').replace("\u2014", "\\u2014"));
						else if("Set/Rarity:".equals(lineType))
							card.printings = line;
						else if("Color:".equals(lineType))
							for(String color: line.split("/"))
								card.colorIndicator.add(color.toUpperCase());
						else
							lineType = line;
					}
				}
			}
			line = input.readLine();
		}

		if((null != card) && ((null == names) || names.contains(card.name)))
		{
			if(card.write(updatePrintings))
				cardsWritten.put(card.name, card);
			else
				cardsSkipped.put(card.name, card);
		}
	}
}
