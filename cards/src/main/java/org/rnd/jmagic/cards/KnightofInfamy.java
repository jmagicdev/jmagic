package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Knight of Infamy")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class KnightofInfamy extends Card
{
	public KnightofInfamy(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Protection from white (This creature can't be blocked, targeted,
		// dealt damage, or enchanted by anything white.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromWhite(state));

		// Exalted (Whenever a creature you control attacks alone, that creature
		// gets +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
