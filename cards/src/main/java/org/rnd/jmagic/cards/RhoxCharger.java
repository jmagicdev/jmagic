package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rhox Charger")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.RHINO})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class RhoxCharger extends Card
{
	public RhoxCharger(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
