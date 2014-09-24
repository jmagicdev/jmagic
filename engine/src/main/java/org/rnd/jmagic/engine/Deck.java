package org.rnd.jmagic.engine;

public class Deck implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String MAIN_DECK = "MAIN_DECK";
	public static final String SIDEBOARD = "SIDEBOARD";

	protected final java.util.Map<String, java.util.List<String>> cards;
	public final java.util.Map<String, java.util.List<String>> publicCardMap;

	public Deck()
	{
		this.cards = new java.util.HashMap<String, java.util.List<String>>();
		this.publicCardMap = java.util.Collections.unmodifiableMap(this.cards);
		this.cards.put(MAIN_DECK, new java.util.LinkedList<String>());
		this.cards.put(SIDEBOARD, new java.util.LinkedList<String>());
	}

	public Deck(java.util.List<String> mainDeck, java.util.List<String> sideboard)
	{
		this();
		this.cards.get(MAIN_DECK).addAll(mainDeck);
		this.cards.get(SIDEBOARD).addAll(sideboard);
	}

	@SafeVarargs
	public Deck(Class<? extends Card>... mainDeck)
	{
		this();
		java.util.List<String> main = this.cards.get(MAIN_DECK);
		for(Class<? extends Card> cls: mainDeck)
			main.add(org.rnd.jmagic.Convenience.getName(cls));
	}

	public java.util.Map<String, java.util.List<String>> getCards()
	{
		return this.publicCardMap;
	}

	public void addCard(String card)
	{
		this.addCard(card, MAIN_DECK);
	}

	public void addCard(String card, String deckPart)
	{
		if(!this.cards.containsKey(deckPart))
			this.cards.put(deckPart, new java.util.LinkedList<String>());
		this.cards.get(deckPart).add(card);
	}
}
