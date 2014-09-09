package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tideforce Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class TideforceElemental extends Card
{
	public static final class TideforceElementalAbility0 extends ActivatedAbility
	{
		public TideforceElementalAbility0(GameState state)
		{
			super(state, "(U), (T): You may tap or untap another target creature.");
			this.setManaCost(new ManaPool("(U)"));
			this.costsTap = true;

			SetGenerator t = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(youMay(tapOrUntap(t, "another target creature."), "You may tap or untap another target creature."));
		}
	}

	public static final class TideforceElementalAbility1 extends EventTriggeredAbility
	{
		public TideforceElementalAbility1(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, you may untap Tideforce Elemental.");
			this.addPattern(landfall());
			this.addEffect(youMay(untap(ABILITY_SOURCE_OF_THIS, "Untap Tideforce Elemental."), "You may untap Tideforce Elemental."));
		}
	}

	public TideforceElemental(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (U), (T): You may tap or untap another target creature.
		this.addAbility(new TideforceElementalAbility0(state));

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may untap Tideforce Elemental.
		this.addAbility(new TideforceElementalAbility1(state));
	}
}
