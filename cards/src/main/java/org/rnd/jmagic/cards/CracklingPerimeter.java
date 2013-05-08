package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crackling Perimeter")
@Types({Type.ENCHANTMENT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class CracklingPerimeter extends Card
{
	public static final class CracklingPerimeterAbility0 extends ActivatedAbility
	{
		public CracklingPerimeterAbility0(GameState state)
		{
			super(state, "Tap an untapped Gate you control: Crackling Perimeter deals 1 damage to each opponent.");

			// Tap an untapped Gate you control
			EventFactory cost = new EventFactory(EventType.TAP_CHOICE, "Tap an untapped Gate you control");
			cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cost.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cost.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(Untapped.instance(), Intersect.instance(HasSubType.instance(SubType.GATE), ControlledBy.instance(You.instance()))));
			cost.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addCost(cost);

			this.addEffect(permanentDealDamage(1, OpponentsOf.instance(You.instance()), "Crackling Perimeter deals 1 damage to each opponent."));
		}
	}

	public CracklingPerimeter(GameState state)
	{
		super(state);

		// Tap an untapped Gate you control: Crackling Perimeter deals 1 damage
		// to each opponent.
		this.addAbility(new CracklingPerimeterAbility0(state));
	}
}
