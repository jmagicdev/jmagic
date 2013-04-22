package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Blightsteel Colossus")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("(12)")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.MYTHIC)})
@ColorIdentity({})
public final class BlightsteelColossus extends Card
{
	public BlightsteelColossus(GameState state)
	{
		super(state);

		this.setPower(11);
		this.setToughness(11);

		// Trample, infect
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// Blightsteel Colossus is indestructible.
		this.addAbility(new org.rnd.jmagic.abilities.Indestructible(state, this.getName()));

		// If Blightsteel Colossus would be put into a graveyard from anywhere,
		// reveal Blightsteel Colossus and shuffle it into its owner's library
		// instead.
		this.addAbility(new org.rnd.jmagic.abilities.ColossusShuffle(state, this.getName()));
	}
}
