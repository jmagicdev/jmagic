package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rusted Relic")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class RustedRelic extends Card
{
	public static final class RustedRelicAbility0 extends StaticAbility
	{
		public RustedRelicAbility0(GameState state)
		{
			super(state, "Rusted Relic is a 5/5 Golem artifact creature as long as you control three or more artifacts.");

			Animator animator = new Animator(This.instance(), 5, 5);
			animator.removeOldTypes();
			animator.addSubType(SubType.GOLEM);
			animator.addType(Type.ARTIFACT);
			animator.addType(Type.CREATURE);
			this.addEffectPart(animator.getParts());

			this.canApply = Metalcraft.instance();
		}
	}

	public RustedRelic(GameState state)
	{
		super(state);

		// Metalcraft \u2014 Rusted Relic is a 5/5 Golem artifact creature as
		// long as you control three or more artifacts.
		this.addAbility(new RustedRelicAbility0(state));
	}
}
