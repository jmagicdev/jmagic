package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Sage's Row Denizen")
@Types({Type.CREATURE})
@SubTypes({SubType.VEDALKEN, SubType.WIZARD})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class SagesRowDenizen extends Card
{
	public static final class SagesRowDenizenAbility0 extends EventTriggeredAbility
	{
		public SagesRowDenizenAbility0(GameState state)
		{
			super(state, "Whenever another blue creature enters the battlefield under your control, target player puts the top two cards of his or her library into his or her graveyard.");
			SetGenerator blueCreatures = Intersect.instance(HasColor.instance(Color.BLUE), CreaturePermanents.instance());
			SetGenerator otherBlueCreatures = RelativeComplement.instance(blueCreatures, ABILITY_SOURCE_OF_THIS);
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), otherBlueCreatures, You.instance(), false));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 2, "Target player puts the top two cards of his or her library into his or her graveyard."));
		}
	}

	public SagesRowDenizen(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Whenever another blue creature enters the battlefield under your
		// control, target player puts the top two cards of his or her library
		// into his or her graveyard.
		this.addAbility(new SagesRowDenizenAbility0(state));
	}
}
