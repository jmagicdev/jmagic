package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mindeye Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class MindeyeDrake extends Card
{
	public static final class MindeyeDrakeAbility1 extends EventTriggeredAbility
	{
		public MindeyeDrakeAbility1(GameState state)
		{
			super(state, "When Mindeye Drake dies, target player puts the top five cards of his or her library into his or her graveyard.");
			this.addPattern(whenThisDies());

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 5, "Target player puts the top five cards of his or her library into his or her graveyard."));
		}
	}

	public MindeyeDrake(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Mindeye Drake dies, target player puts the top five cards of his
		// or her library into his or her graveyard.
		this.addAbility(new MindeyeDrakeAbility1(state));
	}
}
