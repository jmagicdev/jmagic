package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Kazandu Blademaster")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.ALLY, SubType.HUMAN})
@ManaCost("WW")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class KazanduBlademaster extends Card
{
	public KazanduBlademaster(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		this.addAbility(new org.rnd.jmagic.abilities.ZendikarAllyCounter(state, this.getName()));
	}
}
