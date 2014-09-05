package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Whitemane Lion")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = PlanarChaos.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class WhitemaneLion extends Card
{
	public static final class RescueKitty extends EventTriggeredAbility
	{
		public RescueKitty(GameState state)
		{
			super(state, "When Whitemane Lion enters the battlefield, return a creature you control to its owner's hand.");

			this.addPattern(whenThisEntersTheBattlefield());

			EventType.ParameterMap bounceParameters = new EventType.ParameterMap();
			bounceParameters.put(EventType.Parameter.CAUSE, This.instance());
			bounceParameters.put(EventType.Parameter.PLAYER, You.instance());
			bounceParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			bounceParameters.put(EventType.Parameter.CHOICE, Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.CREATURE)));
			this.addEffect(new EventFactory(EventType.PUT_INTO_HAND_CHOICE, bounceParameters, "Return a creature you control to its owner's hand."));
		}
	}

	public WhitemaneLion(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		this.addAbility(new RescueKitty(state));
	}
}
