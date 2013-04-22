package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

import static org.rnd.jmagic.Convenience.*;

@Name("Cipher")
public final class Cipher extends Keyword
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Cipher", "Choose a creature you control on which to encode this card", true);

	public Cipher(GameState state)
	{
		super(state, "Cipher");
	}

	/**
	 * @eparam CHOICE: creatures you control (what to choose from)
	 * @eparam RESULT: the creature this ends up encoded on
	 */
	public static final EventType ENCODE = new EventType("ENCODE")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			return !parameters.get(Parameter.CHOICE).isEmpty();
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject thisCard = event.getSource();
			Player you = thisCard.getController(game.actualState);
			Set creaturesYouControl = parameters.get(Parameter.CHOICE);
			GameObject chosen = you.sanitizeAndChoose(game.actualState, 1, creaturesYouControl.getAll(GameObject.class), PlayerInterface.ChoiceType.OBJECTS, REASON).get(0);

			Event exile = exile(This.instance(), "Exile this card").createEvent(game, thisCard);
			exile.perform(event, true);

			event.setResult(new Set(chosen));
			return true;
		}
	};

	@Override
	protected java.util.List<EventFactory> createSpellAbilities()
	{
		java.util.LinkedList<EventFactory> ret = new java.util.LinkedList<EventFactory>();

		EventFactory encode = new EventFactory(ENCODE, "Exile this card encoded on a creature you control");
		encode.parameters.put(EventType.Parameter.CHOICE, CREATURES_YOU_CONTROL);
		EventFactory youMayEncode = youMay(encode, "You may exile this card encoded on a creature you control.");

		SetGenerator thisIsACard = Intersect.instance(This.instance(), Cards.instance());
		ret.add(ifThen(thisIsACard, youMayEncode, "If this spell is represented by a card, you may exile this card encoded on a creature you control."));
		return ret;
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new CipherStatic(this.state));
		return ret;
	}

	public static final class CastOnStrike extends EventTriggeredAbility
	{
		public CastOnStrike(GameState state)
		{
			super(state, "Whenever this creature deals combat damage to a player, you may copy the encoded card and you may cast the copy without paying its mana cost.");
			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			SetGenerator encodedCard = Granted.instance(This.instance());

			EventFactory copy = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy the encoded card");
			copy.parameters.put(EventType.Parameter.CAUSE, This.instance());
			copy.parameters.put(EventType.Parameter.OBJECT, encodedCard);
			copy.parameters.put(EventType.Parameter.TARGET, NonEmpty.instance());

			EventFactory youMayCopy = youMay(copy, "You may copy the encoded card");
			SetGenerator theCopy = EffectResult.instance(copy);

			EventFactory mayCast = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "You may cast the copy without paying its mana cost");
			mayCast.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayCast.parameters.put(EventType.Parameter.PLAYER, You.instance());
			mayCast.parameters.put(EventType.Parameter.OBJECT, theCopy);
			this.addEffect(ifThen(youMayCopy, mayCast, "You may copy the encoded card and you may cast the copy without paying its mana cost."));
		}
	}

	public static final class EncodeTracker extends Tracker<java.util.Map<Integer, Integer>>
	{
		// keys are spell cards' IDs, values are IDs of creatures those cards
		// are encoded on
		private java.util.HashMap<Integer, Integer> values = new java.util.HashMap<Integer, Integer>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.values);

		@Override
		public EncodeTracker clone()
		{
			EncodeTracker ret = (EncodeTracker)super.clone();
			ret.values = new java.util.HashMap<Integer, Integer>(this.values);
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.values);
			return ret;
		}

		@Override
		protected java.util.Map<Integer, Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return (event.type == ENCODE);
		}

		@Override
		protected void reset()
		{
			// never clears, since encoding lasts forever
		}

		@Override
		protected void update(GameState state, Event event)
		{
			GameObject creature = event.getResult().getOne(GameObject.class);
			GameObject spellOnTheStack = event.getSource().getPhysical();
			this.values.put(spellOnTheStack.futureSelf, creature.ID);
		}
	}

	/**
	 * Evaluates to the creature that a given spell card is encoded on
	 */
	public static final class EncodedWith extends SetGenerator
	{
		private SetGenerator spellCard;

		private EncodedWith(SetGenerator spellCard)
		{
			this.spellCard = spellCard;
		}

		public static final EncodedWith instance(SetGenerator spellCard)
		{
			return new EncodedWith(spellCard);
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			GameObject spellCard = this.spellCard.evaluate(state, thisObject).getOne(GameObject.class);
			java.util.Map<Integer, Integer> trackerValue = state.getTracker(EncodeTracker.class).getValue(state);
			if(trackerValue.containsKey(spellCard.ID))
			{
				GameObject encoded = state.getByIDObject(trackerValue.get(spellCard.ID));
				if(encoded != null)
					return new Set(encoded);
			}
			return Empty.set;
		}
	}

	public static final class CipherStatic extends StaticAbility
	{
		public CipherStatic(GameState state)
		{
			super(state, "Whenever that creature deals combat damage to a player, its controller may cast a copy of the encoded card without paying its mana cost.");

			state.ensureTracker(new EncodeTracker());
			SetGenerator thatCreature = EncodedWith.instance(This.instance());
			this.canApply = thatCreature;

			this.addEffectPart(addAbilityToObject(thatCreature, CastOnStrike.class));
		}
	}
}
