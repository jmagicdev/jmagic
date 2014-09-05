package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Torch Fiend")
@Types({Type.CREATURE})
@SubTypes({SubType.DEVIL})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class TorchFiend extends Card
{
	public static final class TorchFiendAbility0 extends ActivatedAbility
	{
		public TorchFiendAbility0(GameState state)
		{
			super(state, "(R), Sacrifice Torch Fiend: Destroy target artifact.");
			this.setManaCost(new ManaPool("(R)"));
			this.addCost(sacrificeThis("Torch Fiend"));
			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(destroy(target, "Destroy target artifact."));
		}
	}

	public TorchFiend(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (R), Sacrifice Torch Fiend: Destroy target artifact.
		this.addAbility(new TorchFiendAbility0(state));
	}
}
