package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Rumbling Aftershocks")
@Types({Type.ENCHANTMENT})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class RumblingAftershocks extends Card
{
	public static final class KickedSpells implements SetPattern
	{
		@Override
		public boolean match(GameState state, Identified thisObject, Set set)
		{
			for(GameObject object: set.getAll(GameObject.class))
			{
				if(!object.isSpell())
					continue;

				for(CostCollection cost: object.getOptionalAdditionalCostsChosen())
					if(cost.type.equals(org.rnd.jmagic.abilities.keywords.Kicker.COST_TYPE))
						return true;
			}
			return false;
		}

		@Override
		public void freeze(GameState state, Identified thisObject)
		{
			// pattern not affected by text changes
		}
	}

	public static final class TimesKicked extends SetGenerator
	{
		private SetGenerator spells;

		private TimesKicked(SetGenerator spells)
		{
			this.spells = spells;
		}

		public static SetGenerator instance(SetGenerator spells)
		{
			return new TimesKicked(spells);
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			int timesKicked = 0;
			for(GameObject object: this.spells.evaluate(state, thisObject).getAll(GameObject.class))
			{
				if(!object.isSpell())
					continue;

				for(CostCollection cost: object.getOptionalAdditionalCostsChosen())
					if(cost.type.equals(org.rnd.jmagic.abilities.keywords.Kicker.COST_TYPE))
						timesKicked++;
			}
			if(timesKicked == 0)
				return Empty.set;
			return new Set.Unmodifiable(timesKicked);
		}
	}

	public static final class RumblingAftershocksAbility0 extends EventTriggeredAbility
	{
		public RumblingAftershocksAbility0(GameState state)
		{
			super(state, "Whenever you cast a kicked spell, you may have Rumbling Aftershocks deal damage to target creature or player equal to the number of times that spell was kicked.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.OBJECT, new KickedSpells());
			this.addPattern(pattern);

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

			SetGenerator thatSpell = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);
			EventFactory damage = permanentDealDamage(TimesKicked.instance(thatSpell), target, "Rumbling Aftershocks deals damage to target creature or player equal to the number of times that spell was kicked");
			this.addEffect(youMay(damage, "You may have Rumbling Aftershocks deal damage to target creature or player equal to the number of times that spell was kicked."));
		}
	}

	public RumblingAftershocks(GameState state)
	{
		super(state);

		// Whenever you cast a kicked spell, you may have Rumbling Aftershocks
		// deal damage to target creature or player equal to the number of times
		// that spell was kicked.
		this.addAbility(new RumblingAftershocksAbility0(state));
	}
}
