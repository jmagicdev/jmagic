package org.rnd.jmagic.engine;

import org.rnd.jmagic.sanitized.*;
import org.rnd.util.*;

/** Represents an interface a user can use to communicate with the game. */
public interface PlayerInterface
{
	/** Represents different things a user might be asked to choose. */
	public enum ChoiceType
	{
		ACTION(false), ACTIVATE_MANA_ABILITIES(false), ALTERNATE_COST(false), ATTACK(false), ATTACK_WHAT(false), BLOCK(false), BLOCK_WHAT(false), CLASS(false), COIN_FLIP(false), COLOR(false), COSTS(true), CREATURE_TYPE(false), DAMAGE(false), DAMAGE_SOURCE(false), ENUM(false), ENUM_ORDERED(true), EVENT(false), MANA_EXPLOSION(false), MANA_PAYMENT(false), MODE(false), MOVEMENT_GRAVEYARD(true), MOVEMENT_LIBRARY(true), MOVEMENT_STACK(true), NORMAL_ACTIONS(false), OBJECTS(false), OBJECTS_ORDERED(true), PILE(false), PLAYER(false), REPLACEMENT_EFFECT(false), SINGLE_TARGET(false), STRING(false), TARGETS(false), TIMESTAMPS(true), TRIGGERS(true);

		private final boolean isOrdered;

		ChoiceType(boolean isOrdered)
		{
			this.isOrdered = isOrdered;
		}

		/**
		 * @return Whether the order the user chooses things of this type is
		 * important.
		 */
		public boolean isOrdered()
		{
			return this.isOrdered;
		}
	}

	public static class ChooseParameters<T extends java.io.Serializable> implements java.io.Serializable
	{
		private static final long serialVersionUID = 1L;

		public Set number;
		public java.util.List<T> choices;
		public ChoiceType type;
		public ChooseReason reason;
		public boolean allowMultiples;
		public int whatForID;

		public int thisID;
		public java.util.List<SanitizedIdentified> ensurePresent;
		public String replacement;

		public ChooseParameters(int lowerBound, Integer upperBound, ChoiceType type, ChooseReason reason)
		{
			this(lowerBound, upperBound, new java.util.LinkedList<T>(), type, reason);
		}

		public ChooseParameters(int lowerBound, Integer upperBound, java.util.List<T> choices, ChoiceType type, ChooseReason reason)
		{
			this(new Set(), choices, type, reason);
			this.number.add(new org.rnd.util.NumberRange(lowerBound, upperBound));
		}

		public ChooseParameters(ChooseParameters<? extends T> copy)
		{
			this.number = new Set(copy.number);
			this.choices = new java.util.LinkedList<T>(copy.choices);
			this.type = copy.type;
			this.reason = copy.reason;
			this.thisID = copy.thisID;
			this.replacement = copy.replacement;
			this.ensurePresent = new java.util.LinkedList<SanitizedIdentified>(copy.ensurePresent);
		}

		public ChooseParameters(Set number, ChoiceType type, ChooseReason reason)
		{
			this(number, new java.util.LinkedList<T>(), type, reason);
		}

		public ChooseParameters(Set number, java.util.List<T> choices, ChoiceType type, ChooseReason reason)
		{
			this.number = number;
			this.choices = choices;
			this.type = type;
			this.reason = reason;
			this.allowMultiples = false;
			this.whatForID = -1;

			this.thisID = -1;
			this.ensurePresent = new java.util.LinkedList<SanitizedIdentified>();
			this.replacement = null;
		}
	}

	public static class ChooseReason implements java.io.Serializable
	{
		private static final long serialVersionUID = 1L;

		public static final String GAME = "{Game}";

		public static final ChooseReason AN_OPPONENT = new ChooseReason(GAME, "Choose an opponent.", true);
		public static final ChooseReason ATTACH = new ChooseReason(GAME, "Choose what ~ will attach to.", true);
		public static final ChooseReason ACTIVATE_MANA_ABILITIES = new ChooseReason(GAME, "Activate mana abilities.", true);
		public static final ChooseReason ACTION = new ChooseReason(GAME, "Choose an action to perform.", true);
		public static final ChooseReason BEGIN_THE_GAME_EFFECTS = new ChooseReason(GAME, "Which opening hand actions would you like to take?", true);
		public static final ChooseReason BOUNCE = new ChooseReason(GAME, "Choose object(s) to return to owner's hand.", true);
		public static final ChooseReason CHOOSE_ANOTHER_COLOR = new ChooseReason(GAME, "Choose another color.", true);
		public static final ChooseReason CHOOSE_ANOTHER_BASIC_LAND_TYPE = new ChooseReason(GAME, "Choose another basic land type.", true);
		public static final ChooseReason CHOOSE_BASIC_LAND_TYPE = new ChooseReason(GAME, "Choose a basic land type.", true);
		public static final ChooseReason CHOOSE_CARD_TO_REVEAL = new ChooseReason(GAME, "Choose card(s) to reveal.", true);
		public static final ChooseReason CHOOSE_CARD_TYPE = new ChooseReason(GAME, "Choose a card type.", true);
		public static final ChooseReason CHOOSE_COLOR = new ChooseReason(GAME, "Choose a color for ~.", true);
		public static final ChooseReason CHOOSE_COLOR_OR_BASIC_LAND_TYPE = new ChooseReason(GAME, "Choose a color or basic land type.", true);
		public static final ChooseReason CHOOSE_COUNTERS = new ChooseReason(GAME, "Choose counters.", true);
		public static final ChooseReason CHOOSE_CREATURE_TYPE = new ChooseReason(GAME, "Choose a creature type.", true);
		public static final ChooseReason CHOOSE_EVENT = new ChooseReason(GAME, "Choose one event to perform.", true);
		public static final ChooseReason CHOOSE_MANA_COST = new ChooseReason(GAME, "How do you want to pay?", true);
		public static final ChooseReason CHOOSE_PLAYER = new ChooseReason(GAME, "Choose a player.", true);
		public static final ChooseReason CLASH = new ChooseReason(GAME, "Choose an opponent to clash with.", true);
		public static final ChooseReason COST_REDUCTION = new ChooseReason(GAME, "How do you want to reduce the cost?", true);
		public static final ChooseReason COPY = new ChooseReason(GAME, "Choose what ~ will copy.", true);
		public static final ChooseReason DAMAGE_ASSIGNMENT_ORDER = new ChooseReason(GAME, "Choose a damage assignment order for ~.", true);
		public static final ChooseReason DECLARE_ATTACK_DEFENDER = new ChooseReason(GAME, "Who will ~ attack?", true);
		public static final ChooseReason DECLARE_ATTACKERS = new ChooseReason(GAME, "Which creatures will attack?", true);
		public static final ChooseReason DECLARE_BLOCKED_ATTACKER = new ChooseReason(GAME, "Which attacker will ~ block?", true);
		public static final ChooseReason DECLARE_BLOCKERS = new ChooseReason(GAME, "Which creatures will block?", true);
		public static final ChooseReason DECLARE_TARGETS = new ChooseReason(GAME, "Declare targets.", true);
		public static final ChooseReason DISCARD = new ChooseReason(GAME, "Choose cards to discard.", true);
		public static final ChooseReason EXILE = new ChooseReason(GAME, "Choose what to exile.", true);
		public static final ChooseReason FIRST_PLAYER = new ChooseReason(GAME, "Who will play first?", true);
		// TODO : hybrid_mana's text sounds really generic; how is it used?
		public static final ChooseReason HYBRID_MANA = new ChooseReason(GAME, "Choose some mana.", true);
		public static final ChooseReason MAKE_PILE = new ChooseReason(GAME, "Choose objects for one pile.", true);
		public static final ChooseReason MANIPULATE_COIN_FLIP = new ChooseReason(GAME, "Manipulate the coin flip.", true);
		public static final ChooseReason MAY_CAST = new ChooseReason(GAME, "Cast ~?", true);
		public static final ChooseReason MAY_EVENT = new ChooseReason(GAME, "~ -- yes or no?", true);
		public static final ChooseReason MULLIGAN = new ChooseReason(GAME, "Do you want to mulligan?", true);
		public static final ChooseReason NAME_A_CARD = new ChooseReason(GAME, "Name a card.", true);
		public static final ChooseReason NAME_A_NONLAND_CARD = new ChooseReason(GAME, "Name a nonland card.", true);
		public static final ChooseReason OPTIONAL_ADDITIONAL_COST = new ChooseReason(GAME, "Would you like to pay any optional additional costs?", true);
		public static final ChooseReason OPTIONAL_ALTERNATE_COST = new ChooseReason(GAME, "Pay an optional alternate cost?", true);
		public static final ChooseReason OPTIONAL_REPLACEMENT = new ChooseReason(GAME, "~", true);
		public static final ChooseReason ORDER_ATTACK_COSTS = new ChooseReason(GAME, "Choose payment order for costs to attack.", true);
		public static final ChooseReason ORDER_COSTS = new ChooseReason(GAME, "Order costs to play ~", true);
		public static final ChooseReason ORDER_GRAVEYARD = new ChooseReason(GAME, "Order cards put into your graveyard.", true);
		public static final ChooseReason ORDER_LIBRARY_TARGET = new ChooseReason(GAME, "Order cards put into target player's library.", false);
		public static final ChooseReason ORDER_LIBRARY_TOP = new ChooseReason(GAME, "Order cards put into your library.", false);
		public static final ChooseReason ORDER_LIBRARY_BOTTOM = new ChooseReason(GAME, "Order cards put on the bottom of your library.", false);
		public static final ChooseReason ORDER_STACK = new ChooseReason(GAME, "Order cards put onto the stack.", false);
		public static final ChooseReason PAY_MANA = new ChooseReason(GAME, "Pay ~.", true);
		public static final ChooseReason PLANESWALKER_REDIRECT = new ChooseReason(GAME, "Choose a planeswalker to redirect damage to.", true);
		public static final ChooseReason POPULATE = new ChooseReason(GAME, "Choose a creature token you control to copy.", true);
		public static final ChooseReason PROLIFERATE = new ChooseReason(GAME, "Choose any number of permanents and/or players with counters on them to proliferate.", true);
		public static final ChooseReason PROLIFERATE_CHOOSECOUNTER = new ChooseReason(GAME, "Choose which type of counter to add.", true);
		public static final ChooseReason PUT_COUNTER = new ChooseReason(GAME, "Choose an object to put a counter onto.", true);
		public static final ChooseReason PUT_INTO_HAND = new ChooseReason(GAME, "Put a card into your hand.", true);
		public static final ChooseReason PUT_ONTO_BATTLEFIELD = new ChooseReason(GAME, "Put a card onto the battlefield.", true);
		public static final ChooseReason PRIORITY = new ChooseReason(GAME, "You have priority.", true);
		public static final ChooseReason REMOVE_COUNTER_FROM = new ChooseReason(GAME, "Choose something to remove a counter from.", true);
		public static final ChooseReason REPLACE_DAMAGE = new ChooseReason(GAME, "Which effect will replace damage?", true);
		public static final ChooseReason REPLACE_EVENT = new ChooseReason(GAME, "Which effect will replace ~?", true);
		public static final ChooseReason REPLACE_WHICH_DAMAGE = new ChooseReason(GAME, "Which damage will that effect replace?", true);
		// TODO: Find a better way to say what zone change is happening
		public static final ChooseReason REPLACE_ZONE_CHANGE = new ChooseReason(GAME, "Which effect will replace this zone change?", true);
		public static final ChooseReason REVEAL = new ChooseReason(GAME, "Choose cards to reveal.", true);
		public static final ChooseReason SACRIFICE = new ChooseReason(GAME, "Choose what to sacrifice.", true);
		public static final ChooseReason SCRY_TO_BOTTOM = new ChooseReason(GAME, "Put any number of those cards on the bottom of your library.", false);
		public static final ChooseReason SHUFFLE_OBJECTS = new ChooseReason(GAME, "Choose what to shuffle in.", false);
		public static final ChooseReason SELECT_MODE = new ChooseReason(GAME, "Choose a mode for ~.", true);
		public static final ChooseReason STACK_TRIGGERS = new ChooseReason(GAME, "Put your triggered abilities on the stack.", true);
		public static final ChooseReason SPLICE_OBJECTS = new ChooseReason(GAME, "Choose which cards to splice onto ~.", true);
		public static final ChooseReason SPLICE_ORDER = new ChooseReason(GAME, "Choose the order in which the spliced cards' instructions will be followed.", true);
		public static final ChooseReason TAP = new ChooseReason(GAME, "Choose permanent(s) to tap.", true);
		public static final ChooseReason TARGET = new ChooseReason(GAME, "Choose a target.", true);
		public static final ChooseReason TIMESTAMP = new ChooseReason(GAME, "Determine timestamp order.", true);
		public static final ChooseReason UNTAP = new ChooseReason(GAME, "Choose permanent(s) to untap.", true);

		public final String source;
		public final String query;
		public final boolean isPublic;

		public ChooseReason(String source, String query, boolean isPublic)
		{
			this.source = source;
			this.query = query;
			this.isPublic = isPublic;
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
			ChooseReason other = (ChooseReason)obj;
			if(this.isPublic != other.isPublic)
				return false;
			if(this.query == null)
			{
				if(other.query != null)
					return false;
			}
			else if(!this.query.equals(other.query))
				return false;
			if(this.source == null)
			{
				if(other.source != null)
					return false;
			}
			else if(!this.source.equals(other.source))
				return false;
			return true;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = prime + (this.isPublic ? 1231 : 1237);
			result = prime * result + ((this.query == null) ? 0 : this.query.hashCode());
			result = prime * result + ((this.source == null) ? 0 : this.source.hashCode());
			return result;
		}
	}

	public static class ReversionParameters implements java.io.Serializable
	{
		private static final long serialVersionUID = 1L;

		public final String source;
		public final String message;

		public ReversionParameters(String source, String message)
		{
			this.source = source;
			this.message = message;
		}
	}

	/**
	 * The collection of parameters to report an error with. Currently empty as
	 * no additional information is needed, but using this, adding additional
	 * information is easy instead of having to update the method signature in
	 * many places.
	 */
	public static abstract class ErrorParameters implements java.io.Serializable
	{
		private static final long serialVersionUID = 1L;

		public static class HostError extends ErrorParameters
		{
			private static final long serialVersionUID = 1L;
		}

		public static class CardLoadingError extends ErrorParameters
		{
			private static final long serialVersionUID = 1L;

			public final java.util.Set<String> cardNames;

			public CardLoadingError(java.util.Set<String> cardNames)
			{
				this.cardNames = java.util.Collections.unmodifiableSet(new java.util.HashSet<String>(cardNames));
			}
		}

		public static class IllegalCardsError extends ErrorParameters
		{
			private static final long serialVersionUID = 1L;

			public final java.util.Set<String> cardNames;

			public IllegalCardsError(java.util.Set<String> cardNames)
			{
				this.cardNames = java.util.Collections.unmodifiableSet(new java.util.HashSet<String>(cardNames));
			}
		}

		public static class DeckCheckError extends ErrorParameters
		{
			private static final long serialVersionUID = 1L;

			public final String rule;

			public DeckCheckError(Class<? extends GameType.GameTypeRule> rule)
			{
				this.rule = rule.getName();
			}
		}

		public static class CardCheckError extends ErrorParameters
		{
			private static final long serialVersionUID = 1L;

			public final String card;

			public CardCheckError(Class<? extends Card> card)
			{
				this.card = card.getName();
			}
		}
	}

	public void alertChoice(int playerID, ChooseParameters<?> choice);

	/**
	 * Alert the player that the engine has encountered an error and will be
	 * terminating. After this is called on a PlayerInterface, the associated
	 * Game may be in an invalid state and should not be relied on.
	 * 
	 * @param parameters Any information about the error useful to players
	 */
	public void alertError(ErrorParameters parameters);

	public void alertEvent(SanitizedEvent event);

	public void alertState(SanitizedGameState sanitizedGameState);

	public void alertStateReversion(ReversionParameters parameters);

	/**
	 * @param who The player we are waiting on to make a choice, or null if we
	 * are waiting for the engine.
	 */
	public void alertWaiting(SanitizedPlayer who);

	/**
	 * Asks the user of this interface to choose from a set of elements.
	 * 
	 * @param <T> The kind of thing being chosen.
	 * @param parameterObject The parameters for the choice.
	 * @return What the user chose.
	 */
	public <T extends java.io.Serializable> java.util.List<Integer> choose(ChooseParameters<T> parameterObject);

	/**
	 * Asks the user of this interface to choose a number.
	 * 
	 * @param range The legal choices.
	 * @param description An explanation of why the user is choosing a number.
	 * @return The number the user chose.
	 */
	// TODO: Should these parameters be wrapped in a parameter object in case of
	// future additional parameters?
	public int chooseNumber(NumberRange range, String description);

	/**
	 * Asks the user of this interface to divide a quantity across a list of
	 * targets. A proper implementation of this method will directly modify the
	 * targets with the divisions, setting the user's choices in each
	 * Target.division field.
	 * 
	 * @param quantity The quantity to divide.
	 * @param minimum The minimum amount each target must receive.
	 * @param whatFrom ID of the thing causing the division (a creature in
	 * combat, a spell distributing damage or counters, etc.)
	 * @param beingDivided the thing being divided, i.e. "damage", "counters",
	 * etc.
	 * @param targets The targets to divide across.
	 */
	// TODO: Should these parameters be wrapped in a parameter object in case of
	// future additional parameters?
	public void divide(int quantity, int minimum, int whatFrom, String beingDivided, java.util.List<SanitizedTarget> targets);

	/** @return A list of cards representing the user's deck. */
	public Deck getDeck();

	/** @return What name the player wants to have. */
	public String getName();

	/**
	 * Tell this interface what player-ID it represents so it can look up the
	 * right information
	 * 
	 * @param playerID The player-ID of this interface
	 */
	public void setPlayerID(int playerID);

}
