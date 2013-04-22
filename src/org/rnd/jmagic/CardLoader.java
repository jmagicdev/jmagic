package org.rnd.jmagic;

import org.rnd.jmagic.engine.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.*;
import org.springframework.core.type.filter.*;

public class CardLoader extends java.net.URLClassLoader
{
	public static class CardLoaderException extends Exception
	{
		private static final long serialVersionUID = 1L;

		public final java.util.Set<String> cardNames;

		public CardLoaderException()
		{
			super("Unrecognized Cards");

			this.cardNames = new java.util.HashSet<String>();
		}

		public CardLoaderException(String cardName)
		{
			this();

			this.addCard(cardName);
		}

		public void addCard(String cardName)
		{
			this.cardNames.add(cardName);
		}

		public void combine(CardLoaderException ex)
		{
			this.cardNames.addAll(ex.cardNames);
		}

		public PlayerInterface.ErrorParameters.CardLoadingError getErrorParameters()
		{
			return new PlayerInterface.ErrorParameters.CardLoadingError(this.cardNames);
		}

		@Override
		public String getMessage()
		{
			return "Unrecognized Cards: " + org.rnd.util.SeparatedList.get("", this.cardNames);
		}
	}

	private static java.util.regex.Pattern DECK_LINE_PATTERN;

	private static CardLoader _instance = new CardLoader();

	private static java.util.Map<String, Class<? extends Card>> cardClasses = new java.util.HashMap<String, Class<? extends Card>>();

	private static java.util.Map<String, String> NON_ASCII_REPLACE;

	static
	{
		NON_ASCII_REPLACE = new java.util.HashMap<String, String>();
		// Upper-case combined a-e
		NON_ASCII_REPLACE.put("\u00C6", "AE");
		// Lower-case e with an accent
		NON_ASCII_REPLACE.put("\u00E9", "e");
		// Lower-case o with an umlaut
		NON_ASCII_REPLACE.put("\u00F6", "o");
	}

	public static void addPackages(String... packages)
	{
		for(String pkg: packages)
			if(pkg != null)
				_instance.addPackage(pkg);
	}

	public static void addURLs(java.net.URL... urls)
	{
		for(java.net.URL url: urls)
			if(url != null)
				_instance.addURL(url);
	}

	/**
	 * Format a card name into a name understood by the engine
	 */
	public static String formatName(String name)
	{
		String ret = org.rnd.util.CamelCase.removeNonAlpha(name);
		for(java.util.Map.Entry<String, String> entry: NON_ASCII_REPLACE.entrySet())
			ret = ret.replace(entry.getKey(), entry.getValue());
		return ret;
	}

	public static java.util.Set<String> getAllCardNames()
	{
		return java.util.Collections.unmodifiableSet(cardClasses.keySet());
	}

	public static java.util.Set<Class<? extends Card>> getAllCards()
	{
		return getAllCards(false);
	}

	public static java.util.Set<Class<? extends Card>> getAllCards(boolean includeAlternateCards)
	{
		java.util.Set<Class<? extends Card>> ret = new java.util.HashSet<Class<? extends Card>>();

		for(Class<? extends Card> card: cardClasses.values())
			if(includeAlternateCards || !AlternateCard.class.isAssignableFrom(card))
				ret.add(card);

		return ret;
	}

	/**
	 * Find a card's class file by name. TODO: when we get around to using this
	 * for multiple interfaces, all the exception catching should either: a)
	 * throw a common error, or b) silently return null.
	 * 
	 * @param name The name of the card, no spaces, in camel-case
	 * @return The class object associated with that card
	 */
	public static Class<? extends Card> getCard(String name) throws CardLoaderException
	{
		if(!cardClasses.containsKey(name))
			throw new CardLoaderException(name);
		return cardClasses.get(name);
	}

	/**
	 * @param sets The sets to get cards from
	 * @return A set containing the class object of every card printed in at
	 * least one of the given sets
	 * @throws CardLoaderException
	 */
	public static java.util.Set<Class<? extends Card>> getCards(java.util.Collection<Expansion> sets)
	{
		return getCards(sets, false);
	}

	/**
	 * @param sets The sets to get cards from
	 * @return A set containing the class object of every card printed in at
	 * least one of the given sets
	 */
	public static java.util.Set<Class<? extends Card>> getCards(java.util.Collection<Expansion> sets, boolean includeAlternateCards)
	{
		java.util.Set<Class<? extends Card>> ret = new java.util.HashSet<Class<? extends Card>>();

		for(Class<? extends Card> c: getAllCards(includeAlternateCards))
		{
			java.util.Map<Expansion, Rarity> printingsArray = getPrintings(c);

			for(Expansion printing: printingsArray.keySet())
				if(sets.contains(printing))
				{
					ret.add(c);
					break;
				}
		}

		return ret;
	}

	public static int getCardsLoaded()
	{
		return _instance.cardsLoaded;
	}

	public static Deck getDeck(java.io.BufferedReader in) throws java.io.IOException
	{
		java.util.List<String> deck = new java.util.LinkedList<String>();
		java.util.List<String> sideboard = new java.util.LinkedList<String>();

		String line = in.readLine();
		while(null != line)
		{
			java.util.regex.Matcher matcher = getDeckLinePattern().matcher(line);
			// The deck-line pattern matches every possible input, so it's a
			// major error if it doesn't match
			if(!matcher.matches())
				throw new RuntimeException("Regex that matches everything didn't match " + line);

			boolean mainDeck = (null == matcher.group(3));

			String numberString = matcher.group(5);
			int number;
			if(null == numberString)
				number = 1;
			else
				number = Integer.parseInt(numberString);

			String cardName = matcher.group(6);
			if(null != cardName)
			{
				if(mainDeck)
				{
					for(int i = 0; i < number; ++i)
						deck.add(cardName);
				}
				else
				{
					for(int i = 0; i < number; ++i)
						sideboard.add(cardName);
				}
			}

			line = in.readLine();
		}

		in.close();
		return new Deck(deck, sideboard);
	}

	/**
	 * Provide a pattern that matches every possible input but provides groups
	 * to get card information out of. The location of the card is in group 3,
	 * the number of cards is in group 5, and the name of the card is in group
	 * 6.
	 */
	private static java.util.regex.Pattern getDeckLinePattern()
	{
		if(null == DECK_LINE_PATTERN)
			DECK_LINE_PATTERN = java.util.regex.Pattern.compile("\\s*(((SB):\\s*)?((\\d+)\\s+)?([^#/]*[^#/\\s]))?\\s*(#.*)?(//.*)?");
		return DECK_LINE_PATTERN;
	}

	public static CardLoader getInstance()
	{
		return _instance;
	}

	public static Printings.Printed[] getPrintedAnnotation(Class<? extends Card> c)
	{
		if(!c.isAnnotationPresent(Printings.class))
			throw new java.lang.annotation.AnnotationFormatError(c.getSimpleName() + " has no @Printed annotation");

		return c.getAnnotation(Printings.class).value();
	}

	public static java.util.SortedMap<Expansion, Rarity> getPrintings(Class<? extends Card> c)
	{
		java.util.SortedMap<Expansion, Rarity> ret = new java.util.TreeMap<Expansion, Rarity>();

		Printings.Printed[] printings = getPrintedAnnotation(c);
		for(Printings.Printed print: printings)
			ret.put(print.ex(), print.r());
		return ret;
	}

	public static java.util.Map<Rarity, java.util.Set<Class<? extends Card>>> getRarityMap(Expansion ex)
	{
		java.util.Map<Rarity, java.util.Set<Class<? extends Card>>> ret = new java.util.HashMap<Rarity, java.util.Set<Class<? extends Card>>>();

		for(Class<? extends Card> card: getCards(java.util.Collections.singleton(ex)))
		{
			Rarity rarity = null;
			for(java.util.Map.Entry<Expansion, Rarity> entry: getPrintings(card).entrySet())
				if(entry.getKey().equals(ex))
				{
					rarity = entry.getValue();
					break;
				}

			if(rarity == null)
				throw new RuntimeException("getCards(" + ex + ") returned a card such that getPrintings(card) didn't contain '" + ex + "'");

			if(!ret.containsKey(rarity))
				ret.put(rarity, new java.util.HashSet<Class<? extends Card>>());
			ret.get(rarity).add(card);
		}

		return ret;
	}

	public static void main(String[] args)
	{
		addPackages("org.rnd.jmagic.cards");

		for(Expansion ex: Expansion.values())
		{
			java.util.Collection<Class<? extends Card>> cards = getCards(java.util.Collections.singleton(ex));

			System.out.println(ex + ": \t" + cards.size());

			// for(Class<? extends Card> card: cards)
			// System.out.println("\t" + card.getSimpleName());
		}
	}

	private final java.util.Set<String> cardPackages;

	private int cardsLoaded;

	// Singleton constructor
	private CardLoader()
	{
		super(new java.net.URL[] {});

		this.cardPackages = new java.util.HashSet<String>();
		this.cardsLoaded = 0;
	}

	public void addPackage(String pkg)
	{
		if(this.cardPackages.add(pkg))
		{
			loadCardsFromClassLoader(org.rnd.jmagic.engine.Card.class.getClassLoader(), pkg);
			for(java.net.URL url: this.getURLs())
				loadCardsFromClassLoader(new java.net.URLClassLoader(new java.net.URL[] {url}), pkg);
		}
	}

	@Override
	public void addURL(java.net.URL url)
	{
		super.addURL(url);

		loadCardsFromURL(url);
	}

	@Override
	protected Class<?> findClass(String className) throws ClassNotFoundException
	{
		if(cardClasses.containsKey(className))
			return cardClasses.get(className);
		return super.findClass(className);
	}

	@SuppressWarnings("unchecked")
	private void loadCardsFromClassLoader(ClassLoader temploader, String pkg)
	{
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.setResourceLoader(new org.springframework.core.io.support.PathMatchingResourcePatternResolver(temploader));
		scanner.addIncludeFilter(new AssignableTypeFilter(Card.class));

		java.util.Set<BeanDefinition> components = scanner.findCandidateComponents(pkg);
		for(BeanDefinition bean: components)
		{
			try
			{
				Class<?> cls = temploader.loadClass(bean.getBeanClassName());

				String name = cls.getAnnotation(Name.class).value();

				java.util.List<String> additionalNames = new java.util.LinkedList<String>();
				for(java.util.Map.Entry<String, String> entry: NON_ASCII_REPLACE.entrySet())
					additionalNames.add(name.replace(entry.getKey(), entry.getValue()));

				if(bean instanceof ScannedGenericBeanDefinition && !((ScannedGenericBeanDefinition)bean).getMetadata().hasEnclosingClass())
				{
					this.resolveClass(cls);
					cardClasses.put(name, (Class<? extends Card>)cls);
					for(String additionalName: additionalNames)
						cardClasses.put(additionalName, (Class<? extends Card>)cls);
					++this.cardsLoaded;
				}
			}
			catch(ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void loadCardsFromURL(java.net.URL url)
	{
		for(String pkg: this.cardPackages)
			loadCardsFromClassLoader(new java.net.URLClassLoader(new java.net.URL[] {url}), pkg);
	}
}
