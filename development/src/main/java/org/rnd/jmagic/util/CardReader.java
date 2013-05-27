package org.rnd.jmagic.util;

public class CardReader
{
	private static int cardsMade = 0;
	private static int cardsSkipped = 0;
	private static int totalCards = 0;

	public static void main(String[] args) throws java.io.IOException
	{
		java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		String read = "";
		java.util.LinkedList<String> file = new java.util.LinkedList<String>();
		try
		{
			while(true)
			{
				while((read = in.readLine()) != null && (!read.equals("#DONE")) && ((0 != read.length()) || file.get(file.size() - 1).startsWith("Rules Text:")))
					file.add(read.replace("\u00C6", "AE").replace("\u00E6", "ae"));
				if(read == null)
					break;
				readCard(file);
				file.clear();
				if(read.equals("#DONE"))
					return;
			}
		}
		finally
		{
			file.clear();
			in.close();
			System.out.println("Cards Made:       " + CardReader.cardsMade);
			System.out.println("Cards Skipped:    " + CardReader.cardsSkipped);
			System.out.println("Total Cards Read: " + CardReader.totalCards);
			System.out.flush();
		}
	}

	private static void readCard(java.util.List<String> cardText)
	{
		CardShell card = new CardShell();

		CardReader.totalCards++;
		card.name = cardText.get(0).replaceAll("Name:\\s*", "").trim();
		card.manaCost = cardText.get(1).replaceAll("Cost:\\s*", "").trim();
		card.types = cardText.get(2).replaceAll("Type:\\s*", "");

		String pt = cardText.get(3);
		if(pt.startsWith("Pow/Tgh:"))
		{
			pt = pt.replaceAll("Pow/Tgh:\\s*", "");
			if(0 != pt.length())
			{
				card.power = pt.substring(0, pt.indexOf("/")).replace("(", "");
				card.toughness = pt.substring(pt.indexOf("/") + 1).replace(")", "");
			}
		}
		else if(pt.startsWith("Loyalty:"))
		{
			pt = pt.replaceAll("Loyalty:\\s*\\(", "").replace(")", "");
			card.loyalty = pt;
		}
		else
		{
			cardText.add(3, null);
		}

		card.abilities.add(cardText.get(4).replaceAll("Rules Text:\\s*", "").replace("{", "(").replace("}", ")").replace("\u2014", "\\u2014"));

		if(cardText.size() > 5)
			for(int i = 5; i < cardText.size() - 1; i++)
				card.abilities.add(cardText.get(i).replace("{", "(").replace("}", ")").replace("\u2014", "\\u2014"));

		card.printings = cardText.get(cardText.size() - 1).replaceAll("Set\\/Rarity:\\s*", "");

		if(card.write(false))
			CardReader.cardsMade++;
		else
		{
			System.out.println("Card already exists: " + card.name);
			CardReader.cardsSkipped++;
		}
	}
}
