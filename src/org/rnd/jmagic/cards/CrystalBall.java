package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Crystal Ball")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class CrystalBall extends Card
{
	public static final class CrystalBallAbility0 extends ActivatedAbility
	{
		public CrystalBallAbility0(GameState state)
		{
			super(state, "(1), (T): Scry 2. ");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;
			this.addEffect(scry(2, "Scry 2."));
		}
	}

	public CrystalBall(GameState state)
	{
		super(state);

		// (1), (T): Scry 2. (To scry 2, look at the top two cards of your
		// library, then put any number of them on the bottom of your library
		// and the rest on top in any order.)
		this.addAbility(new CrystalBallAbility0(state));
	}
}
