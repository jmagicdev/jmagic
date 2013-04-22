package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grave Bramble")
@Types({Type.CREATURE})
@SubTypes({SubType.PLANT})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class GraveBramble extends Card
{
	public GraveBramble(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Defender, protection from Zombies
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		SetGenerator zombies = Intersect.instance(HasSubType.instance(SubType.ZOMBIE), Permanents.instance());
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, zombies, "Zombies"));
	}
}
