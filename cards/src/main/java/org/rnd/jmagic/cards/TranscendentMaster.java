package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Transcendent Master")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.AVATAR, SubType.HUMAN})
@ManaCost("1WW")
@ColorIdentity({Color.WHITE})
public final class TranscendentMaster extends Card
{
	public TranscendentMaster(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Level up (1)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(1)"));

		// LEVEL 6-11
		// 6/6
		// Lifelink
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 6, 11, 6, 6, "Lifelink", org.rnd.jmagic.abilities.keywords.Lifelink.class));

		// LEVEL 12+
		// 9/9
		// Lifelink
		// Transcendent Master is indestructible.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 12, 9, 9, "Lifelink, indestructible.", org.rnd.jmagic.abilities.keywords.Lifelink.class, org.rnd.jmagic.abilities.keywords.Indestructible.class));
	}
}
