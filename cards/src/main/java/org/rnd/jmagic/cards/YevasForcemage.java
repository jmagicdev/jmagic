package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Yeva's Forcemage")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SHAMAN})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class YevasForcemage extends Card
{
	public static final class YevasForcemageAbility0 extends EventTriggeredAbility
	{
		public YevasForcemageAbility0(GameState state)
		{
			super(state, "When Yeva's Forcemage enters the battlefield, target creature gets +2/+2 until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(ptChangeUntilEndOfTurn(target, +2, +2, "Target creature gets +2/+2 until end of turn."));
		}
	}

	public YevasForcemage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Yeva's Forcemage enters the battlefield, target creature gets
		// +2/+2 until end of turn.
		this.addAbility(new YevasForcemageAbility0(state));
	}
}
