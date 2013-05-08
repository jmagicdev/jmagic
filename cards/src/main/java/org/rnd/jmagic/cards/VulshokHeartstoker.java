package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vulshok Heartstoker")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.HUMAN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class VulshokHeartstoker extends Card
{
	public static final class VulshokHeartstokerAbility0 extends EventTriggeredAbility
	{
		public VulshokHeartstokerAbility0(GameState state)
		{
			super(state, "When Vulshok Heartstoker enters the battlefield, target creature gets +2/+0 until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(createFloatingEffect("Target creature gets +2/+0 until end of turn.", modifyPowerAndToughness(target, +2, +0)));
		}
	}

	public VulshokHeartstoker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Vulshok Heartstoker enters the battlefield, target creature gets
		// +2/+0 until end of turn.
		this.addAbility(new VulshokHeartstokerAbility0(state));
	}
}
