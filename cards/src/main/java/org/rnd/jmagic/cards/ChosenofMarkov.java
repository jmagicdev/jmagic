package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chosen of Markov")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
@BackFace(MarkovsServant.class)
public final class ChosenofMarkov extends Card
{
	public static final class ChosenofMarkovAbility0 extends ActivatedAbility
	{
		public ChosenofMarkovAbility0(GameState state)
		{
			super(state, "(T), Tap an untapped Vampire you control: Transform Chosen of Markov.");
			this.costsTap = true;

			// Tap an untapped Vampire you control
			EventFactory cost = new EventFactory(EventType.TAP_CHOICE, "Tap an untapped Vampire you control");
			cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cost.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cost.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(Untapped.instance(), Intersect.instance(HasSubType.instance(SubType.VAMPIRE), ControlledBy.instance(You.instance()))));
			cost.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addCost(cost);

			this.addEffect(transform(ABILITY_SOURCE_OF_THIS, "Transform Chosen of Markov."));
		}
	}

	public ChosenofMarkov(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (T), Tap an untapped Vampire you control: Transform Chosen of Markov.
		this.addAbility(new ChosenofMarkovAbility0(state));
	}
}
