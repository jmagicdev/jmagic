package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Prismatic Omen")
@Types({Type.ENCHANTMENT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class PrismaticOmen extends Card
{
	public static final class PrismaticOmenAbility0 extends StaticAbility
	{
		public PrismaticOmenAbility0(GameState state)
		{
			super(state, "Lands you control are every basic land type in addition to their other types.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance())));
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.fromCollection(SubType.getBasicLandTypes()));
			this.addEffectPart(part);
		}
	}

	public PrismaticOmen(GameState state)
	{
		super(state);

		// Lands you control are every basic land type in addition to their
		// other types.
		this.addAbility(new PrismaticOmenAbility0(state));
	}
}
