package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Duskmantle Prowler")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.ROGUE})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class DuskmantleProwler extends Card
{
	public DuskmantleProwler(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Haste (This creature can attack and (T) as soon as it comes under
		// your control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Exalted (Whenever a creature you control attacks alone, that creature
		// gets +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
