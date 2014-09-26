package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Auriok Sunchaser")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class AuriokSunchaser extends Card
{
	public static final class AuriokSunchaserAbility0 extends StaticAbility
	{
		public AuriokSunchaserAbility0(GameState state)
		{
			super(state, "Metalcraft \u2014 As long as you control three or more artifacts, Auriok Sunchaser gets +2/+2 and has flying.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +2));

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Flying.class));

			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public AuriokSunchaser(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Metalcraft \u2014 As long as you control three or more artifacts,
		// Auriok Sunchaser gets +2/+2 and has flying.
		this.addAbility(new AuriokSunchaserAbility0(state));
	}
}
