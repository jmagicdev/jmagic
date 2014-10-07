package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Kruphix, God of Horizons")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.GOD})
@ManaCost("3GU")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class KruphixGodofHorizons extends Card
{
	public static final class KruphixGodofHorizonsAbility1 extends StaticAbility
	{
		public KruphixGodofHorizonsAbility1(GameState state)
		{
			super(state, "As long as your devotion to green and blue is less than seven, Kruphix isn't a creature.");

			SetGenerator notEnoughDevotion = Intersect.instance(Between.instance(null, 6), DevotionTo.instance(Color.GREEN, Color.BLUE));
			this.canApply = Both.instance(this.canApply, notEnoughDevotion);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public static final class KruphixGodofHorizonsAbility2 extends StaticAbility
	{
		public KruphixGodofHorizonsAbility2(GameState state)
		{
			super(state, "You have no maximum hand size.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_MAX_HAND_SIZE);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, ControllerOf.instance(This.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Empty.instance());
			this.addEffectPart(part);
		}
	}

	public static final class YourEmptyingMana extends SetGenerator
	{
		private YourEmptyingMana()
		{
			// singleton
		}

		private static SetGenerator _instance = null;

		public static SetGenerator instance()
		{
			if(_instance == null)
				_instance = new YourEmptyingMana();
			return _instance;
		}

		public Set evaluate(GameState state, Identified thisObject)
		{
			Player you = You.instance().evaluate(state, thisObject).getOne(Player.class);
			SetPattern doesntEmpty = state.manaThatDoesntEmpty.get(you.ID);

			return you.pool.stream()//
			.filter(symbol -> !doesntEmpty.match(state, null, new Set(symbol)))//
			.collect(java.util.stream.Collectors.toCollection(Set::new));
		}
	}

	/**
	 * @eparam MANA the mana symbols to turn colorless. this assumes this is
	 * mana that exists in a player's mana pool, which means that it necessarily
	 * has exactly one mana type (no hybrid symbols).
	 */
	public static EventType MANA_BECOMES_COLORLESS = new EventType("MANA_BECOMES_COLORLESS")
	{

		@Override
		public Parameter affects()
		{
			// TODO Auto-generated method stub
			return Parameter.MANA;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			parameters.get(Parameter.MANA).getAll(ManaSymbol.class).stream()//
			.forEach(m -> {
				if(m.colors.size() == 1)
				{
					m.colorless = 1;
					m.colors.clear();
				}
			});

			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class KruphixGodofHorizonsAbility3 extends StaticAbility
	{
		public KruphixGodofHorizonsAbility3(GameState state)
		{
			super(state, "If unused mana would empty from your mana pool, that mana becomes colorless instead.");

			SimpleEventPattern emptying = new SimpleEventPattern(EventType.EMPTY_MANA_POOL);
			emptying.put(EventType.Parameter.CAUSE, (gameState, thisObject, set) -> set.getAll(GameObject.class).isEmpty());
			emptying.put(EventType.Parameter.PLAYER, You.instance());

			EventReplacementEffect colorlessInstead = new EventReplacementEffect(state.game, "If unused mana would empty from your mana pool, that mana becomes colorless instead.", emptying);

			EventFactory becomesColorless = new EventFactory(MANA_BECOMES_COLORLESS, "That mana becomes colorless.");
			becomesColorless.parameters.put(EventType.Parameter.MANA, YourEmptyingMana.instance());
			colorlessInstead.addEffect(becomesColorless);

			this.addEffectPart(replacementEffectPart(colorlessInstead));
		}
	}

	public KruphixGodofHorizons(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(7);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// As long as your devotion to green and blue is less than seven,
		// Kruphix isn't a creature.
		this.addAbility(new KruphixGodofHorizonsAbility1(state));

		// You have no maximum hand size.
		this.addAbility(new KruphixGodofHorizonsAbility2(state));

		// If unused mana would empty from your mana pool, that mana becomes
		// colorless instead.
		this.addAbility(new KruphixGodofHorizonsAbility3(state));
	}
}
