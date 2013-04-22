package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Grizzled Outcasts")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WEREWOLF})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
@BackFace(KrallenhordeWantons.class)
public final class GrizzledOutcasts extends Card
{
	public GrizzledOutcasts(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// At the beginning of each upkeep, if no spells were cast last turn,
		// transform Grizzled Outcasts.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));
	}
}
