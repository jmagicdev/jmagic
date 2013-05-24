package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pelakka Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("4GGG")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class PelakkaWurm extends Card
{
	public static final class GainLife extends EventTriggeredAbility
	{
		public GainLife(GameState state)
		{
			super(state, "When Pelakka Wurm enters the battlefield, you gain 7 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 7, "You gain 7 life."));
		}
	}

	public static final class DrawACard extends EventTriggeredAbility
	{
		public DrawACard(GameState state)
		{
			super(state, "When Pelakka Wurm dies, draw a card.");
			this.addPattern(whenThisDies());
			this.addEffect(drawACard());
		}
	}

	public PelakkaWurm(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// When Pelakka Wurm enters the battlefield, you gain 7 life.
		this.addAbility(new GainLife(state));

		// When Pelakka Wurm is put into a graveyard from the battlefield, draw
		// a card.
		this.addAbility(new DrawACard(state));
	}
}
