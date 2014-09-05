package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sylvan Caryatid")
@Types({Type.CREATURE})
@SubTypes({SubType.PLANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.THEROS, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class SylvanCaryatid extends Card
{
	public SylvanCaryatid(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Defender, hexproof
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));

		// (T): Add one mana of any color to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForAnyColor(state));
	}
}
