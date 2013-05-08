package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Master Transmuter")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.ARTIFICER, SubType.HUMAN})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class MasterTransmuter extends Card
{
	public static final class DeserveHate extends ActivatedAbility
	{
		public DeserveHate(GameState state)
		{
			super(state, "(U), (T), Return an artifact you control to its owner's hand: You may put an artifact card from your hand onto the battlefield.");

			this.setManaCost(new ManaPool("U"));
			this.costsTap = true;

			EventFactory cost = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "Return an artifact you control to its owner's hand");
			cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cost.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cost.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			cost.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.ARTIFACT)));
			this.addCost(cost);

			EventFactory moveFactory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put an artifact card from your hand onto the battlefield");
			moveFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			moveFactory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			moveFactory.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(InZone.instance(HandOf.instance(You.instance())), HasType.instance(Type.ARTIFACT)));

			this.addEffect(youMay(moveFactory, "You may put an artifact card from your hand onto the battlefield."));
		}
	}

	public MasterTransmuter(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		this.addAbility(new DeserveHate(state));
	}
}
