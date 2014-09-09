package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Villagers of Estwald")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WEREWOLF})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
@BackFace(HowlpackofEstwald.class)
public final class VillagersofEstwald extends Card
{
	public VillagersofEstwald(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// At the beginning of each upkeep, if no spells were cast last turn,
		// transform Villagers of Estwald.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));
	}
}
