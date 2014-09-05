package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Goldenglow Moth")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Shadowmoor.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class GoldenglowMoth extends Card
{
	public static final class GoldenglowMothAbility1 extends EventTriggeredAbility
	{
		public GoldenglowMothAbility1(GameState state)
		{
			super(state, "Whenever Goldenglow Moth blocks, you may gain 4 life.");
			this.addPattern(whenThisBlocks());
			this.addEffect(youMay(gainLife(You.instance(), 4, "Gain 4 life."), "You may gain 4 life."));
		}
	}

	public GoldenglowMoth(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Goldenglow Moth blocks, you may gain 4 life.
		this.addAbility(new GoldenglowMothAbility1(state));
	}
}
