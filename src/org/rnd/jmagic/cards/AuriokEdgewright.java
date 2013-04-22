package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Auriok Edgewright")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("WW")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class AuriokEdgewright extends Card
{
	public static final class AuriokEdgewrightAbility0 extends StaticAbility
	{
		public AuriokEdgewrightAbility0(GameState state)
		{
			super(state, "Auriok Edgewright has double strike as long as you control three or more artifacts.");

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.DoubleStrike.class));

			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public AuriokEdgewright(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Metalcraft \u2014 Auriok Edgewright has double strike as long as you
		// control three or more artifacts.
		this.addAbility(new AuriokEdgewrightAbility0(state));
	}
}
