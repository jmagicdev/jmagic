package org.rnd.jmagic.cardTemplates;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class TimeSpiralStorageLand extends Card
{
	public static final class Store extends ActivatedAbility
	{
		private final String cardName;

		public Store(GameState state, String cardName)
		{
			super(state, "(1), (T): Put a storage counter on " + cardName + ".");
			this.cardName = cardName;

			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;

			this.addEffect(putCountersOnThis(1, Counter.CounterType.STORAGE, cardName));
		}

		@Override
		public Store create(Game game)
		{
			return new Store(game.physicalState, this.cardName);
		}
	}

	/**
	 * @eparam SOURCE: the land
	 * @eparam MANA: {@link ManaSymbol} to add X times
	 * @eparam NUMBER: value of X of the ability
	 * @eparam PLAYER: controller of the ability
	 * @eparam RESULT: the result of ADD_MANA
	 */
	public static final EventType STORAGE_LAND_EVENT = new EventType("STORAGE_LAND_EVENT")
	{
		@Override
		public boolean addsMana()
		{
			return true;
		}

		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set source = parameters.get(Parameter.SOURCE);
			Set you = parameters.get(Parameter.PLAYER);

			int amount = parameters.get(Parameter.NUMBER).getOne(Integer.class);
			ManaSymbol manaSymbol = parameters.get(Parameter.MANA).getOne(ManaSymbol.class);

			StringBuilder mana = new StringBuilder();
			for(int i = 0; i < amount; i++)
				mana.append(manaSymbol);

			String eventName = "Add X mana in any combination of " + manaSymbol + " to your mana pool.";
			java.util.Map<Parameter, Set> manaParameters = new java.util.HashMap<Parameter, Set>();
			manaParameters.put(Parameter.SOURCE, source);
			manaParameters.put(Parameter.PLAYER, you);
			manaParameters.put(Parameter.MANA, new Set(new ManaPool(mana.toString())));
			Event makeMana = createEvent(game, eventName, ADD_MANA, manaParameters);
			makeMana.perform(event, true);

			event.setResult(Identity.instance(makeMana.getResult()));
			return true;
		}
	};

	public static final class UseStorage extends ActivatedAbility
	{
		private final String cardName;
		private final String manaColors;

		public UseStorage(GameState state, String cardName, String colors)
		{
			super(state, "(1), Remove X storage counters from " + cardName + ": Add X mana in any combination of (" + colors.substring(0, 1) + ") and/or (" + colors.substring(1, 2) + ") to your mana pool.");
			this.cardName = cardName;
			this.manaColors = colors;

			this.setManaCost(new ManaPool("(1)"));

			EventFactory removeCounters = removeCountersFromThis(ValueOfX.instance(This.instance()), Counter.CounterType.STORAGE, "Remove X storage counters from " + cardName);
			removeCounters.usesX();
			this.addCost(removeCounters);

			EventFactory effect = new EventFactory(STORAGE_LAND_EVENT, "Add X mana in any combination of (" + colors.substring(0, 1) + ") and/or (" + colors.substring(1, 2) + ") to your mana pool.");
			effect.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			effect.parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("(" + colors + ")")));
			effect.parameters.put(EventType.Parameter.NUMBER, ValueOfX.instance(This.instance()));
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(effect);
		}

		@Override
		public UseStorage create(Game game)
		{
			return new UseStorage(game.physicalState, this.cardName, this.manaColors);
		}
	}

	public TimeSpiralStorageLand(GameState state, String colors)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (1), (T): Put a storage counter on [name].
		this.addAbility(new Store(state, this.getName()));

		// (1), Remove X storage counters from [name]: Add X mana in
		// any combination of (color1) and/or (color2) to your mana pool.
		this.addAbility(new UseStorage(state, this.getName(), colors));
	}
}
