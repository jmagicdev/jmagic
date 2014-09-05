package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Blightsteel Colossus")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("(12)")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.MYTHIC)})
@ColorIdentity({})
public final class BlightsteelColossus extends Card
{
	public BlightsteelColossus(GameState state)
	{
		super(state);

		this.setPower(11);
		this.setToughness(11);

		// Trample, infect, indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// If Blightsteel Colossus would be put into a graveyard from anywhere,
		// reveal Blightsteel Colossus and shuffle it into its owner's library
		// instead.
		this.addAbility(new org.rnd.jmagic.abilities.ColossusShuffle(state, this.getName()));
	}
}
