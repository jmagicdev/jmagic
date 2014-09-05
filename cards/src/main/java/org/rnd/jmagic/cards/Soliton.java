package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Soliton")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Soliton extends Card
{
	public static final class SolitonAbility0 extends ActivatedAbility
	{
		public SolitonAbility0(GameState state)
		{
			super(state, "(U): Untap Soliton.");
			this.setManaCost(new ManaPool("(U)"));
			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap Soliton."));
		}
	}

	public Soliton(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// (U): Untap Soliton.
		this.addAbility(new SolitonAbility0(state));
	}
}
