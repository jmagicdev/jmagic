package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stoic Rebuttal")
@Types({Type.INSTANT})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class StoicRebuttal extends Card
{
	public static final class CheapRebuttal extends StaticAbility
	{
		public CheapRebuttal(GameState state)
		{
			super(state, "Stoic Rebuttal costs (1) less to cast if you control three or more artifacts.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new ManaPool("(1)")));
			this.addEffectPart(part);

			this.canApply = Metalcraft.instance();
		}
	}

	public StoicRebuttal(GameState state)
	{
		super(state);

		// Metalcraft \u2014 Stoic Rebuttal costs (1) less to cast if you
		// control three or more artifacts.
		this.addAbility(new CheapRebuttal(state));

		// Counter target spell.
		SetGenerator target = targetedBy(this.addTarget(Spells.instance(), "target spell"));
		this.addEffect(counter(target, "Counter target spell."));
	}
}
