package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vampire Hexmage")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.SHAMAN})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class VampireHexmage extends Card
{
	public static final class TriggerDarkDepths extends ActivatedAbility
	{
		public TriggerDarkDepths(GameState state)
		{
			super(state, "Sacrifice Vampire Hexmage: Remove all counters from target permanent.");
			this.addCost(sacrificeThis("Vampire Hexmage"));

			Target target = this.addTarget(Permanents.instance(), "target permanent");

			EventType.ParameterMap counterParameters = new EventType.ParameterMap();
			counterParameters.put(EventType.Parameter.CAUSE, This.instance());
			counterParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(new EventFactory(EventType.REMOVE_ALL_COUNTERS, counterParameters, "Remove all counters from target permanent."));
		}
	}

	public VampireHexmage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// Sacrifice Vampire Hexmage: Remove all counters from target permanent.
		this.addAbility(new TriggerDarkDepths(state));
	}
}
