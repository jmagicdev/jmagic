package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bramblesnap")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class Bramblesnap extends Card
{
	public static final class BramblesnapAbility1 extends ActivatedAbility
	{
		public BramblesnapAbility1(GameState state)
		{
			super(state, "Tap an untapped creature you control: Bramblesnap gets +1/+1 until end of turn.");

			// Tap an untapped creature you control
			EventFactory cost = new EventFactory(EventType.TAP_CHOICE, "Tap an untapped creature you control");
			cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cost.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cost.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(Untapped.instance(), CREATURES_YOU_CONTROL));
			cost.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addCost(cost);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +1, "Bramblesnap gets +1/+1 until end of turn."));
		}
	}

	public Bramblesnap(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Tap an untapped creature you control: Bramblesnap gets +1/+1 until
		// end of turn.
		this.addAbility(new BramblesnapAbility1(state));
	}
}
