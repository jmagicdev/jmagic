package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gang of Devils")
@Types({Type.CREATURE})
@SubTypes({SubType.DEVIL})
@ManaCost("5R")
@ColorIdentity({Color.RED})
public final class GangofDevils extends Card
{
	public static final class GangofDevilsAbility0 extends EventTriggeredAbility
	{
		public GangofDevilsAbility0(GameState state)
		{
			super(state, "When Gang of Devils dies, it deals 3 damage divided as you choose among one, two, or three target creatures and/or players.");
			this.addPattern(whenThisDies());

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "one, two, or three target creatures and/or players");
			target.setNumber(1, 3);

			this.setDivision(Union.instance(numberGenerator(3), Identity.instance("damage")));

			EventType.ParameterMap damageParameters = new EventType.ParameterMap();
			damageParameters.put(EventType.Parameter.SOURCE, This.instance());
			damageParameters.put(EventType.Parameter.TAKER, ChosenTargetsFor.instance(Identity.instance(target), This.instance()));
			this.addEffect(new EventFactory(EventType.DISTRIBUTE_DAMAGE, damageParameters, "Gang of Devils deals 3 damage divided as you choose among one, two, or three target creatures and/or players."));
		}
	}

	public GangofDevils(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Gang of Devils dies, it deals 3 damage divided as you choose
		// among one, two, or three target creatures and/or players.
		this.addAbility(new GangofDevilsAbility0(state));
	}
}
