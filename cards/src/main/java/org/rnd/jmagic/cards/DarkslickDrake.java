package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Darkslick Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class DarkslickDrake extends Card
{
	public static final class DarkslickDrakeAbility1 extends EventTriggeredAbility
	{
		public DarkslickDrakeAbility1(GameState state)
		{
			super(state, "When Darkslick Drake dies, draw a card.");
			this.addPattern(whenThisDies());
			this.addEffect(drawACard());
		}
	}

	public DarkslickDrake(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Darkslick Drake is put into a graveyard from the battlefield,
		// draw a card.
		this.addAbility(new DarkslickDrakeAbility1(state));
	}
}
