package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Invisible Stalker")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class InvisibleStalker extends Card
{
	public InvisibleStalker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Hexproof (This creature can't be the target of spells or abilities
		// your opponents control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));

		// Invisible Stalker is unblockable.
		this.addAbility(new org.rnd.jmagic.abilities.Unblockable(state, this.getName()));
	}
}
