package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gateway Shade")
@Types({Type.CREATURE})
@SubTypes({SubType.SHADE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class GatewayShade extends Card
{
	public static final class GatewayShadeAbility1 extends ActivatedAbility
	{
		public GatewayShadeAbility1(GameState state)
		{
			super(state, "Tap an untapped Gate you control: Gateway Shade gets +2/+2 until end of turn.");
			// Tap an untapped Gate you control
			EventFactory cost = new EventFactory(EventType.TAP_CHOICE, "Tap an untapped Gate you control");
			cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cost.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cost.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(Untapped.instance(), Intersect.instance(HasSubType.instance(SubType.GATE), ControlledBy.instance(You.instance()))));
			cost.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addCost(cost);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +2, "Gateway Shade gets +2/+2 until end of turn."));
		}
	}

	public GatewayShade(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (B): Gateway Shade gets +1/+1 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.ShadePump(state, this.getName()));

		// Tap an untapped Gate you control: Gateway Shade gets +2/+2 until end
		// of turn.
		this.addAbility(new GatewayShadeAbility1(state));
	}
}
