package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Hooting Mandrills")
@Types({Type.CREATURE})
@SubTypes({SubType.APE})
@ManaCost("5G")
@ColorIdentity({Color.GREEN})
public final class HootingMandrills extends Card
{
	public HootingMandrills(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Delve (Each card you exile from your graveyard while casting this
		// spell pays for (1).)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Delve(state));

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
