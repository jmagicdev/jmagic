package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Spiraling Duelist")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.HUMAN})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class SpiralingDuelist extends Card
{
	public static final class SpiralingDuelistAbility0 extends StaticAbility
	{
		public SpiralingDuelistAbility0(GameState state)
		{
			super(state, "Spiraling Duelist has double strike as long as you control three or more artifacts.");

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.DoubleStrike.class));

			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public SpiralingDuelist(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Metalcraft \u2014 Spiraling Duelist has double strike as long as you
		// control three or more artifacts.
		this.addAbility(new SpiralingDuelistAbility0(state));
	}
}
