package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Snapsail Glider")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({})
public final class SnapsailGlider extends Card
{
	public static final class SnapsailGliderAbility0 extends StaticAbility
	{
		public SnapsailGliderAbility0(GameState state)
		{
			super(state, "Snapsail Glider has flying as long as you control three or more artifacts.");

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Flying.class));

			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public SnapsailGlider(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Metalcraft \u2014 Snapsail Glider has flying as long as you control
		// three or more artifacts.
		this.addAbility(new SnapsailGliderAbility0(state));
	}
}
