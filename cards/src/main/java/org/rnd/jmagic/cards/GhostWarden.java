package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ghost Warden")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Guildpact.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class GhostWarden extends Card
{
	public static final class DonateStats extends ActivatedAbility
	{
		public DonateStats(GameState state)
		{
			super(state, "(T): Target creature gets +1/+1 until end of turn.");

			this.costsTap = true;

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (+1), (+1), "Target creature gets +1/+1 until end of turn."));
		}
	}

	public GhostWarden(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new DonateStats(state));
	}
}
