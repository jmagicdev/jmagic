package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bloodghast")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.VAMPIRE})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Bloodghast extends Card
{
	public static final class HasteSometimes extends StaticAbility
	{
		public HasteSometimes(GameState state)
		{
			super(state, "Bloodghast has haste as long as an opponent has 10 or less life.");

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Haste.class));

			SetGenerator lifeTotals = LifeTotalOf.instance(OpponentsOf.instance(You.instance()));
			SetGenerator opponentHasTenOrLess = Intersect.instance(lifeTotals, Between.instance(null, 10));
			this.canApply = Both.instance(this.canApply, opponentHasTenOrLess);
		}
	}

	public static final class WorstIchoridEver extends EventTriggeredAbility
	{
		public WorstIchoridEver(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, you may return Bloodghast from your graveyard to the battlefield.");
			this.addPattern(landfall());
			this.triggersFromGraveyard();

			EventFactory returnToBattlefield = new EventFactory(EventType.MOVE_OBJECTS, "Return Bloodghast from your graveyard to the battlefield");
			returnToBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnToBattlefield.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			returnToBattlefield.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			returnToBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());

			this.addEffect(youMay(returnToBattlefield, "You may return Bloodghast from your graveyard to the battlefield."));
		}
	}

	public Bloodghast(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Bloodghast can't block.
		this.addAbility(new org.rnd.jmagic.abilities.CantBlock(state, this.getName()));

		// Bloodghast has haste as long as an opponent has 10 or less life.
		this.addAbility(new HasteSometimes(state));

		// Landfall - Whenever a land enters the battlefield under your control,
		// you may return Bloodghast from your graveyard to the battlefield.
		this.addAbility(new WorstIchoridEver(state));
	}
}
