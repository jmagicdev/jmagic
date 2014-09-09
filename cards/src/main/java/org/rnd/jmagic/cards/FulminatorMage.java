package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fulminator Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL, SubType.SHAMAN})
@ManaCost("1(B/R)(B/R)")
@ColorIdentity({Color.RED, Color.BLACK})
public final class FulminatorMage extends Card
{
	public static final class FulminatorMageAbility0 extends ActivatedAbility
	{
		public FulminatorMageAbility0(GameState state)
		{
			super(state, "Sacrifice Fulminator Mage: Destroy target nonbasic land.");
			this.addCost(sacrificeThis("Fulminator Mage"));

			SetGenerator nonbasicLands = RelativeComplement.instance(LandPermanents.instance(), HasSuperType.instance(SuperType.BASIC));
			SetGenerator target = targetedBy(this.addTarget(nonbasicLands, "target nonbasic land"));
			this.addEffect(destroy(target, "Destroy target nonbasic land."));
		}
	}

	public FulminatorMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Sacrifice Fulminator Mage: Destroy target nonbasic land.
		this.addAbility(new FulminatorMageAbility0(state));
	}
}
