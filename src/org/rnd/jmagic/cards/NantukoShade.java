package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Nantuko Shade")
@Types({Type.CREATURE})
@SubTypes({SubType.SHADE, SubType.INSECT})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TORMENT, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class NantukoShade extends Card
{
	public NantukoShade(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (B): Nantuko Shade gets +1/+1 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.ShadePump(state, "Nantuko Shade"));
	}
}
