package org.rnd.jmagic.engine;

/** Represents deck construction rules. */
public class GameType
{
	private static final java.util.ServiceLoader<GameType> loader = java.util.ServiceLoader.load(GameType.class);
	private static java.util.List<GameType> list = null;

	public static java.util.List<GameType> list()
	{
		if(null == list)
		{
			java.util.List<GameType> gameTypes = new java.util.LinkedList<GameType>();

			for(GameType gameType: loader)
			{
				gameTypes.add(gameType);
			}

			list = java.util.Collections.unmodifiableList(gameTypes);
		}

		return list;
	}

	@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
	@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
	public static @interface Ignore
	{
		// Marker annotation
	}

	/**
	 * Represents a single rule which might be combined with other rules to form
	 * a unique GameType. Each GameTypeRule must follow the JavaBean standards
	 * in order to allow dynamic introspection and construction.
	 */
	public static interface GameTypeRule
	{
		/**
		 * @param card The card to check
		 * @return Whether a card is allowed to be played with.
		 */
		public boolean checkCard(Class<? extends Card> card);

		/**
		 * @param ex The expansion to check
		 * @return Whether cards from the expansion are allowed.
		 */
		public boolean checkExpansion(Expansion ex);

		/**
		 * @param deck The deck to check
		 * @return Whether the deck as a whole is allowed to be played with.
		 */
		public boolean checkDeck(java.util.Map<String, java.util.List<Class<? extends Card>>> deck);

		/**
		 * @return Decklists that are exempt from
		 * {@link GameTypeRule#checkCard(Class)}. For example, on 2011 July 1,
		 * Stoneforge Mystic was banned in Standard EXCEPT that the War of
		 * Attrition event decklist was legal if left completely intact.
		 */
		public java.util.Collection<java.util.Map<String, java.util.List<Class<? extends Card>>>> exemptDecklists();

		/**
		 * @return Whether this is a "base" card pool. Strictly for use by user
		 * interfaces; this has no meaning to the engine, and playing a
		 * "Standard Extended" game is technically possible.
		 */
		public boolean isBaseCardPool();

		/**
		 * Make any changes to the physical GameState before the game starts.
		 *
		 * @param physicalState The physical GameState to change
		 */
		public void modifyGameState(GameState physicalState);
	}

	/**
	 * This is an implementation of GameTypeRule that implements each function
	 * and so you only have to override the functions you intend to use.
	 */
	public static class SimpleGameTypeRule implements GameTypeRule
	{
		/**
		 * @return true. See {@link GameTypeRule#checkCard(Class)}.
		 */
		@Override
		public boolean checkCard(Class<? extends Card> card)
		{
			return true;
		}

		/**
		 * @return true. See {@link GameTypeRule#checkExpansion}.
		 */
		@Override
		public boolean checkExpansion(Expansion ex)
		{
			return true;
		}

		/**
		 * @return true. See {@link GameTypeRule#checkDeck(java.util.Map)}.
		 */
		@Override
		public boolean checkDeck(java.util.Map<String, java.util.List<Class<? extends Card>>> deck)
		{
			return true;
		}

		/**
		 * @return False.
		 */
		@Override
		public java.util.Collection<java.util.Map<String, java.util.List<Class<? extends Card>>>> exemptDecklists()
		{
			return java.util.Collections.emptyList();
		}

		/**
		 * @return false. See {@link GameTypeRule#isBaseCardPool()}.
		 */
		@Override
		public boolean isBaseCardPool()
		{
			return false;
		}

		/**
		 * Does nothing. See {@link GameTypeRule#modifyGameState(GameState)}.
		 */
		@Override
		public void modifyGameState(GameState physicalState)
		{
			// Do nothing
		}
	}

	private String name;
	private java.util.List<GameTypeRule> rules;
	private java.util.Map<String, Class<? extends Card>> cardpool;

	public GameType()
	{
		this(null);
	}

	public GameType(String name)
	{
		this.name = name;
		this.rules = new java.util.LinkedList<GameTypeRule>();
		this.cardpool = null;
	}

	public void addRule(GameTypeRule rule)
	{
		this.cardpool = null;
		this.rules.add(rule);
	}

	public PlayerInterface.ErrorParameters checkDeck(java.util.Map<String, java.util.List<Class<? extends Card>>> deck)
	{
		if(this.cardpool == null)
		{
			this.calculateCardPool();
		}

		for(GameTypeRule rule: this.rules)
			if(!rule.checkDeck(deck))
				return new PlayerInterface.ErrorParameters.DeckCheckError(rule.getClass());

		Class<? extends Card> cardBanned = null;
		for(java.util.List<Class<? extends Card>> deckPart: deck.values())
			for(Class<? extends Card> card: deckPart)
				if(!this.cardpool.containsKey(card.getAnnotation(Name.class).value()))
				{
					cardBanned = card;
					break;
				}

		if(cardBanned != null)
			for(GameTypeRule rule: this.rules)
				if(!checkExemptions(rule, deck))
				{
					cardBanned = null;
					break;
				}

		if(cardBanned != null)
			return new PlayerInterface.ErrorParameters.CardCheckError(cardBanned);

		return null;
	}

	private void calculateCardPool()
	{
		java.util.List<Class<? extends Expansion>> expansions = new java.util.LinkedList<Class<? extends Expansion>>();
		for(GameTypeRule rule: this.rules)
		{
			java.util.Iterator<Expansion> iter = Expansion.list().iterator();
			while(iter.hasNext())
			{
				Expansion ex = iter.next();
				if(rule.checkExpansion(ex))
					expansions.add(ex.getClass());
			}
		}

		java.util.Set<Class<? extends Card>> cards = org.rnd.jmagic.CardLoader.getCards(expansions);
		for(GameTypeRule rule: this.rules)
		{
			java.util.Iterator<Class<? extends Card>> iter = cards.iterator();
			while(iter.hasNext())
				if(!rule.checkCard(iter.next()))
					iter.remove();
		}

		this.cardpool = new java.util.HashMap<String, Class<? extends Card>>();

		for(Class<? extends Card> card: cards)
			this.cardpool.put(card.getAnnotation(Name.class).value(), card);
	}

	private static boolean checkExemptions(GameTypeRule rule, java.util.Map<String, java.util.List<Class<? extends Card>>> deck)
	{
		exemptions: for(java.util.Map<String, java.util.List<Class<? extends Card>>> exemptDeck: rule.exemptDecklists())
		{
			if(exemptDeck.size() != deck.size())
				continue;

			for(java.util.Map.Entry<String, java.util.List<Class<? extends Card>>> subDeck: deck.entrySet())
			{
				if(!exemptDeck.containsKey(subDeck.getKey()))
					continue exemptions;
				java.util.List<Class<? extends Card>> yourCardsUnsorted = subDeck.getValue();
				java.util.List<Class<? extends Card>> legalCardsUnsorted = exemptDeck.get(subDeck.getKey());
				if(legalCardsUnsorted.size() != yourCardsUnsorted.size())
					continue exemptions;

				java.util.List<Class<? extends Card>> yourCards = new java.util.ArrayList<Class<? extends Card>>(yourCardsUnsorted);
				java.util.Collections.sort(yourCards, new org.rnd.util.CompareOnToString<Class<? extends Card>>());

				java.util.List<Class<? extends Card>> legalCards = new java.util.ArrayList<Class<? extends Card>>(legalCardsUnsorted);
				java.util.Collections.sort(legalCards, new org.rnd.util.CompareOnToString<Class<? extends Card>>());

				if(!yourCards.equals(legalCards))
					continue exemptions;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		GameType other = (GameType)obj;
		if(this.name == null)
		{
			if(other.name != null)
				return false;
		}
		else if(!this.name.equals(other.name))
			return false;
		return true;
	}

	public java.util.Map<String, Class<? extends Card>> getCardPool()
	{
		if(this.cardpool == null)
			this.calculateCardPool();
		return java.util.Collections.unmodifiableMap(this.cardpool);
	}

	public String getName()
	{
		return this.name;
	}

	public GameTypeRule[] getRules()
	{
		return this.rules.toArray(new GameTypeRule[0]);
	}

	public GameTypeRule getRules(int index)
	{
		return this.rules.get(index);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	public void modifyGameState(GameState state)
	{
		for(GameTypeRule rule: this.rules)
			rule.modifyGameState(state);
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setRules(GameTypeRule[] rules)
	{
		this.rules.clear();
		this.rules.addAll(java.util.Arrays.asList(rules));
	}

	public void setRules(int index, GameTypeRule rule)
	{
		if(this.rules.size() <= index)
			throw new ArrayIndexOutOfBoundsException(index);
		this.rules.set(index, rule);
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
