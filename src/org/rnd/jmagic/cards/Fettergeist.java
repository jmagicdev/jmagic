package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fettergeist")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Fettergeist extends Card
{
	public static final class FettergeistAbility1 extends EventTriggeredAbility
	{
		public FettergeistAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice Fettergeist unless you pay (1) for each other creature you control.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (1) for each other creature you control.");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("(1)")));
			pay.parameters.put(EventType.Parameter.PLAYER, You.instance());
			pay.parameters.put(EventType.Parameter.NUMBER, Count.instance(RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS)));

			this.addEffect(unless(You.instance(), sacrificeThis("Fettergeist"), pay, "Sacrifice Fettergeist unless you pay (1) for each other creature you control."));
		}
	}

	public Fettergeist(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your upkeep, sacrifice Fettergeist unless you pay
		// (1) for each other creature you control.
		this.addAbility(new FettergeistAbility1(state));
	}
}
