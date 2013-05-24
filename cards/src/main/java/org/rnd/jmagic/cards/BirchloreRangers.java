package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Birchlore Rangers")
@Types({Type.CREATURE})
@SubTypes({SubType.DRUID, SubType.ELF})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class BirchloreRangers extends Card
{
	public static final class BirchloreRangersAbility0 extends ActivatedAbility
	{
		public BirchloreRangersAbility0(GameState state)
		{
			super(state, "Tap two untapped Elves you control: Add one mana of any color to your mana pool.");

			// Tap two untapped Elves you control
			SetGenerator choices = Intersect.instance(Untapped.instance(), HasSubType.instance(SubType.ELF), CREATURES_YOU_CONTROL);

			EventFactory tap = new EventFactory(EventType.TAP_CHOICE, "Tap two untapped Elves you control");
			tap.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tap.parameters.put(EventType.Parameter.PLAYER, You.instance());
			tap.parameters.put(EventType.Parameter.CHOICE, choices);
			tap.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			this.addCost(tap);

			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)"));
		}
	}

	public BirchloreRangers(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Tap two untapped Elves you control: Add one mana of any color to your
		// mana pool.
		this.addAbility(new BirchloreRangersAbility0(state));

		// Morph (G) (You may cast this face down as a 2/2 creature for (3).
		// Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(G)"));
	}
}
