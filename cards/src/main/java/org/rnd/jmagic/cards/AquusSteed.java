package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aquus Steed")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class AquusSteed extends Card
{
	public static final class AquusSteedAbility0 extends ActivatedAbility
	{
		public AquusSteedAbility0(GameState state)
		{
			super(state, "(2)(U), (T): Target creature gets -2/-0 until end of turn.");
			this.setManaCost(new ManaPool("(2)(U)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(ptChangeUntilEndOfTurn(target, (-2), (-0), "Target creature gets -2/-0 until end of turn."));
		}
	}

	public AquusSteed(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// (2)(U), (T): Target creature gets -2/-0 until end of turn.
		this.addAbility(new AquusSteedAbility0(state));
	}
}
