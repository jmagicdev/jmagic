package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vedalken Certarch")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.VEDALKEN})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class VedalkenCertarch extends Card
{
	public static final class VedalkenCertarchAbility0 extends ActivatedAbility
	{
		public VedalkenCertarchAbility0(GameState state)
		{
			super(state, "Metalcraft \u2014 (T): Tap target artifact, creature, or land. Activate this ability only if you control three or more artifacts.");
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance(), LandPermanents.instance()), "target artifact, creature, or land"));
			this.addEffect(tap(target, "Tap target artifact, creature, or land."));
			this.addActivateRestriction(Not.instance(Metalcraft.instance()));
		}
	}

	public VedalkenCertarch(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Metalcraft \u2014 (T): Tap target artifact, creature, or land.
		// Activate this ability only if you control three or more artifacts.
		this.addAbility(new VedalkenCertarchAbility0(state));
	}
}
