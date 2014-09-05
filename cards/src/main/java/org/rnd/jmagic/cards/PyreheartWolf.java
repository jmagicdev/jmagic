package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Pyreheart Wolf")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class PyreheartWolf extends Card
{
	public static final class PyreheartRestriction extends SetGenerator
	{
		private SetGenerator creatures;

		private PyreheartRestriction(SetGenerator creatures)
		{
			this.creatures = creatures;
		}

		public static SetGenerator instance(SetGenerator creatures)
		{
			return new PyreheartRestriction(creatures);
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			for(GameObject object: this.creatures.evaluate(state, thisObject).getAll(GameObject.class))
			{
				java.util.List<Integer> blockedByIDs = object.getBlockedByIDs();
				if(null != blockedByIDs && blockedByIDs.size() == 1)
					return NonEmpty.set;
			}
			return Empty.set;
		}
	}

	public static final class PyreheartWolfAbility0 extends EventTriggeredAbility
	{
		public PyreheartWolfAbility0(GameState state)
		{
			super(state, "Whenever Pyreheart Wolf attacks, each creature you control can't be blocked this turn except by two or more creatures.");
			this.addPattern(whenThisAttacks());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(PyreheartRestriction.instance(CREATURES_YOU_CONTROL)));
			this.addEffect(createFloatingEffect("Each creature you control can't be blocked this turn except by two or more creatures.", part));
		}
	}

	public PyreheartWolf(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever Pyreheart Wolf attacks, each creature you control can't be
		// blocked this turn except by two or more creatures.
		this.addAbility(new PyreheartWolfAbility0(state));

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}
