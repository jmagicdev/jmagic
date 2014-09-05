package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Reliquary Tower")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Conflux.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ReliquaryTower extends Card
{
	public static final class SpellbookAbility extends StaticAbility
	{
		public SpellbookAbility(GameState state)
		{
			super(state, "You have no maximum hand size.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_MAX_HAND_SIZE);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, ControllerOf.instance(This.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Empty.instance());
			this.addEffectPart(part);
		}
	}

	public ReliquaryTower(GameState state)
	{
		super(state);

		this.addAbility(new SpellbookAbility(state));

		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));
	}
}
