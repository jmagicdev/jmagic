package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Knight of Glory")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class KnightofGlory extends Card
{
	public KnightofGlory(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Protection from black (This creature can't be blocked, targeted,
		// dealt damage, or enchanted by anything black.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromBlack(state));

		// Exalted (Whenever a creature you control attacks alone, that creature
		// gets +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
