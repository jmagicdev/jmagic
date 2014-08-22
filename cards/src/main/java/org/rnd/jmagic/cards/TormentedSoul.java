package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tormented Soul")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.PLANECHASE_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class TormentedSoul extends Card
{
	public static final class TormentedSoulAbility0 extends StaticAbility
	{
		public TormentedSoulAbility0(GameState state)
		{
			super(state, "Tormented Soul can't block and can't be blocked.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), This.instance())));
			this.addEffectPart(part);

			this.addEffectPart(unblockable(This.instance()));
		}
	}

	public TormentedSoul(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Tormented Soul can't block and is unblockable.
		this.addAbility(new TormentedSoulAbility0(state));
	}
}
