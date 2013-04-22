package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Viscera Seer")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.WIZARD})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class VisceraSeer extends Card
{
	public static final class VisceraSeerAbility0 extends ActivatedAbility
	{
		public VisceraSeerAbility0(GameState state)
		{
			super(state, "Sacrifice a creature: Scry 1.");
			this.addCost(sacrificeACreature());
			this.addEffect(scry(1, "Scry 1."));
		}
	}

	public VisceraSeer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Sacrifice a creature: Scry 1. (To scry 1, look at the top card of
		// your library, then you may put that card on the bottom of your
		// library.)
		this.addAbility(new VisceraSeerAbility0(state));
	}
}
