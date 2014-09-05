package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Voltaic Key")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class VoltaicKey extends Card
{
	public static final class VoltaicKeyAbility0 extends ActivatedAbility
	{
		public VoltaicKeyAbility0(GameState state)
		{
			super(state, "(1), (T): Untap target artifact.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(untap(target, "Untap target artifact."));
		}
	}

	public VoltaicKey(GameState state)
	{
		super(state);

		// (1), (T): Untap target artifact.
		this.addAbility(new VoltaicKeyAbility0(state));
	}
}
