package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dust Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class DustElemental extends Card
{
	public static final class RescueThree extends EventTriggeredAbility
	{
		public RescueThree(GameState state)
		{
			super(state, "When Dust Elemental enters the battlefield, return three creatures you control to their owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator choices = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.CREATURE));
			SetGenerator three = numberGenerator(3);

			EventType.ParameterMap bounceParameters = new EventType.ParameterMap();
			bounceParameters.put(EventType.Parameter.CAUSE, This.instance());
			bounceParameters.put(EventType.Parameter.PLAYER, You.instance());
			bounceParameters.put(EventType.Parameter.NUMBER, three);
			bounceParameters.put(EventType.Parameter.CHOICE, choices);
			this.addEffect(new EventFactory(EventType.PUT_INTO_HAND_CHOICE, bounceParameters, "Return three creatures you control to their owner's hand."));
		}
	}

	public DustElemental(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Fear(state));
		this.addAbility(new RescueThree(state));
	}
}
