package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Emeria, the Sky Ruin")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class EmeriatheSkyRuin extends Card
{
	public static final class LateGameInevitability extends EventTriggeredAbility
	{
		public LateGameInevitability(GameState state)
		{
			super(state, "At the beginning of your upkeep, if you control seven or more Plains, you may return target creature card from your graveyard to the battlefield.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator yourPlains = Intersect.instance(HasSubType.instance(SubType.PLAINS), org.rnd.jmagic.engine.generators.ControlledBy.instance(You.instance()));
			this.interveningIf = Intersect.instance(Count.instance(yourPlains), Between.instance(7, null));

			Target target = this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), "target creature card from your graveyard");

			EventFactory returnCreature = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return target creature card from your graveyard to the battlefield.");
			returnCreature.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnCreature.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			returnCreature.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));

			this.addEffect(youMay(returnCreature, "You may return target creature card from your graveyard to the battlefield."));
		}
	}

	public EmeriatheSkyRuin(GameState state)
	{
		super(state);

		// Emeria, the Sky Ruin enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// At the beginning of your upkeep, if you control seven or more Plains,
		// you may return target creature card from your graveyard to the
		// battlefield.
		this.addAbility(new LateGameInevitability(state));

		// (T): Add (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForW(state));
	}
}
