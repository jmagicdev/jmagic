package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Accorder Paladin")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class AccorderPaladin extends Card
{
	public AccorderPaladin(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Battle cry (Whenever this creature attacks, each other attacking
		// creature gets +1/+0 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.BattleCry(state));
	}
}
