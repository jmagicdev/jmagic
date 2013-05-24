package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Immolating Souleater")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.HOUND})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ImmolatingSouleater extends Card
{
	public static final class ImmolatingSouleaterAbility0 extends ActivatedAbility
	{
		public ImmolatingSouleaterAbility0(GameState state)
		{
			super(state, "(r/p): Immolating Souleater gets +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(r/p)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +0, "Immolating Souleater gets +1/+0 until end of turn."));
		}
	}

	public ImmolatingSouleater(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (r/p): Immolating Souleater gets +1/+0 until end of turn. ((r/p) can
		// be paid with either (R) or 2 life.)
		this.addAbility(new ImmolatingSouleaterAbility0(state));
	}
}
