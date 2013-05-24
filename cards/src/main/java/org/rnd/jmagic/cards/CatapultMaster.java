package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Catapult Master")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class CatapultMaster extends Card
{
	public static final class CatapultMasterAbility0 extends ActivatedAbility
	{
		public CatapultMasterAbility0(GameState state)
		{
			super(state, "Tap five untapped Soldiers you control: Exile target creature.");

			// Tap five untapped Soldiers you control
			EventFactory factory = new EventFactory(EventType.TAP_CHOICE, "Tap five untapped Soldiers you control");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.SOLDIER)), Untapped.instance()));
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(5));
			this.addCost(factory);

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(exile(targetedBy(target), "Exile target creature."));
		}
	}

	public CatapultMaster(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Tap five untapped Soldiers you control: Exile target creature.
		this.addAbility(new CatapultMasterAbility0(state));
	}
}
