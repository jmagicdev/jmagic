package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Wandering Wolf")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class WanderingWolf extends Card
{
	public static final class WanderingWolfAbility0 extends StaticAbility
	{
		public WanderingWolfAbility0(GameState state)
		{
			super(state, "Creatures with power less than Wandering Wolf's power can't block it.");

			SetGenerator smallGuys = HasPower.instance(Between.instance(Empty.instance(), PowerOf.instance(This.instance())));
			SetGenerator blockedBySmall = Intersect.instance(Blocking.instance(This.instance()), smallGuys);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockedBySmall));
			this.addEffectPart(part);
		}
	}

	public WanderingWolf(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Creatures with power less than Wandering Wolf's power can't block it.
		this.addAbility(new WanderingWolfAbility0(state));
	}
}
