package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Heritage Druid")
@Types({Type.CREATURE})
@SubTypes({SubType.DRUID, SubType.ELF})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.MORNINGTIDE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class HeritageDruid extends Card
{
	public static final class HeritageDruidAbility0 extends ActivatedAbility
	{
		public HeritageDruidAbility0(GameState state)
		{
			super(state, "Tap three untapped Elves you control: Add (G)(G)(G) to your mana pool.");
			// Tap three untapped Elves you control
			EventFactory factory = new EventFactory(EventType.TAP_CHOICE, "Tap three untapped Elves you control");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.ELF)), Untapped.instance()));
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			this.addCost(factory);

			this.addEffect(addManaToYourManaPoolFromAbility("(G)(G)(G)"));
		}
	}

	public HeritageDruid(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Tap three untapped Elves you control: Add (G)(G)(G) to your mana
		// pool.
		this.addAbility(new HeritageDruidAbility0(state));
	}
}
