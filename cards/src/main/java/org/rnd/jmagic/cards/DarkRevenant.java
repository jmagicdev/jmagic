package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dark Revenant")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class DarkRevenant extends Card
{
	public static final class DarkRevenantAbility1 extends EventTriggeredAbility
	{
		public DarkRevenantAbility1(GameState state)
		{
			super(state, "When Dark Revenant dies, put it on top of its owner's library.");
			this.addPattern(whenThisDies());
			this.addEffect(putOnTopOfLibrary(FutureSelf.instance(ABILITY_SOURCE_OF_THIS), "Put it on top of its owner's library."));
		}
	}

	public DarkRevenant(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Dark Revenant dies, put it on top of its owner's library.
		this.addAbility(new DarkRevenantAbility1(state));
	}
}
