package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Vampire Nighthawk")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.SHAMAN})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class VampireNighthawk extends Card
{
	public VampireNighthawk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
	}
}
