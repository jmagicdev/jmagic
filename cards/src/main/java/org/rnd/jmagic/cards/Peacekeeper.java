package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Peacekeeper")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.WEATHERLIGHT, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class Peacekeeper extends Card
{
	public static final class PeacekeeperAbility0 extends EventTriggeredAbility
	{
		public PeacekeeperAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice Peacekeeper unless you pay (1)(W).");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (1)(W).");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(1)(W)")));
			pay.parameters.put(EventType.Parameter.PLAYER, You.instance());

			this.addEffect(ifThenElse(playerMay(You.instance(), pay, "You may pay (1)(W)."), null, sacrificeThis("Peacekeeper"), "Sacrifice Peacekeeper unless you pay (1)(W)."));
		}
	}

	public static final class PeacekeeperAbility1 extends StaticAbility
	{
		public PeacekeeperAbility1(GameState state)
		{
			super(state, "Creatures can't attack.");

			SetGenerator attackingCreatures = Intersect.instance(CreaturePermanents.instance(), Attacking.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(attackingCreatures));
			this.addEffectPart(part);
		}
	}

	public Peacekeeper(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// At the beginning of your upkeep, sacrifice Peacekeeper unless you pay
		// (1)(W).
		this.addAbility(new PeacekeeperAbility0(state));

		// Creatures can't attack.
		this.addAbility(new PeacekeeperAbility1(state));
	}
}
