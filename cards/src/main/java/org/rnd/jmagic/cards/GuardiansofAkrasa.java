package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Guardians of Akrasa")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class GuardiansofAkrasa extends Card
{
	public GuardiansofAkrasa(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
