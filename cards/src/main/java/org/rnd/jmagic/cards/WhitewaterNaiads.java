package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Whitewater Naiads")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.NYMPH})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class WhitewaterNaiads extends Card
{
	public static final class WhitewaterNaiadsAbility0 extends EventTriggeredAbility
	{
		public WhitewaterNaiadsAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Whitewater Naiads or another enchantment enters the battlefield under your control, target creature can't be blocked this turn.");
			this.addPattern(constellation());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(createFloatingEffect("Target creature can't be blocked this turn.", unblockable(target)));
		}
	}

	public WhitewaterNaiads(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Constellation \u2014 Whenever Whitewater Naiads or another
		// enchantment enters the battlefield under your control, target
		// creature can't be blocked this turn.
		this.addAbility(new WhitewaterNaiadsAbility0(state));
	}
}
