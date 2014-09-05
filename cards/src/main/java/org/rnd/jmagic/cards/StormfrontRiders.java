package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Stormfront Riders")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = PlanarChaos.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class StormfrontRiders extends Card
{
	public static final class RescueTwo extends EventTriggeredAbility
	{
		public RescueTwo(GameState state)
		{
			super(state, "When Stormfront Riders enters the battlefield, return two creatures you control to their owner's hand.");
			SetGenerator choices = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Identity.instance(Type.CREATURE)));
			SetGenerator two = numberGenerator(2);

			this.addPattern(whenThisEntersTheBattlefield());

			EventType.ParameterMap bounceParameters = new EventType.ParameterMap();
			bounceParameters.put(EventType.Parameter.CAUSE, This.instance());
			bounceParameters.put(EventType.Parameter.PLAYER, You.instance());
			bounceParameters.put(EventType.Parameter.NUMBER, two);
			bounceParameters.put(EventType.Parameter.CHOICE, choices);
			this.addEffect(new EventFactory(EventType.PUT_INTO_HAND_CHOICE, bounceParameters, "Return two creatures you control to their owner's hand."));
		}
	}

	public static final class MakeASoldier extends EventTriggeredAbility
	{
		public MakeASoldier(GameState state)
		{
			super(state, "Whenever Stormfront Riders or another creature is returned to your hand from the battlefield, put a 1/1 white Soldier creature token onto the battlefield.");
			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator controller = ControllerOf.instance(thisCard);

			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), HandOf.instance(controller), Union.instance(ABILITY_SOURCE_OF_THIS, HasType.instance(Identity.instance(Type.CREATURE))), true));

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Soldier creature token onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.SOLDIER);
			this.addEffect(token.getEventFactory());
		}
	}

	public StormfrontRiders(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new RescueTwo(state));
		this.addAbility(new MakeASoldier(state));
	}
}
