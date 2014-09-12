package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Amphin Pathmage")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.SALAMANDER})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class AmphinPathmage extends Card
{
	public static final class AmphinPathmageAbility0 extends ActivatedAbility
	{
		public AmphinPathmageAbility0(GameState state)
		{
			super(state, "(2)(U): Target creature can't be blocked this turn.");
			this.setManaCost(new ManaPool("(2)(U)"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(createFloatingEffect("Target creature can't be blocked this turn.", unblockable(target)));
		}
	}

	public AmphinPathmage(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// (2)(U): Target creature can't be blocked this turn.
		this.addAbility(new AmphinPathmageAbility0(state));
	}
}
