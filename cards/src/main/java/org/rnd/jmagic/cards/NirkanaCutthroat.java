package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Nirkana Cutthroat")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.VAMPIRE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class NirkanaCutthroat extends Card
{
	public NirkanaCutthroat(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Level up (2)(B) ((2)(B): Put a level counter on this. Level up only
		// as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(2)(B)"));

		// LEVEL 1-2
		// 4/3
		// Deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 1, 2, 4, 3, "Deathtouch", org.rnd.jmagic.abilities.keywords.Deathtouch.class));

		// LEVEL 3+
		// 5/4
		// First strike, deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 3, 5, 4, "First strike, deathtouch", org.rnd.jmagic.abilities.keywords.FirstStrike.class, org.rnd.jmagic.abilities.keywords.Deathtouch.class));
	}
}
