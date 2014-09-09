package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Flectomancer")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.WIZARD})
@ManaCost("URR")
@ColorIdentity({Color.BLUE, Color.RED})
public final class GoblinFlectomancer extends Card
{
	public static final class GoblinFlectomancerAbility0 extends ActivatedAbility
	{
		public GoblinFlectomancerAbility0(GameState state)
		{
			super(state, "Sacrifice Goblin Flectomancer: You may change the targets of target instant or sorcery spell.");
			this.addCost(sacrificeThis("Goblin Flectomancer"));

			Target instantOrSorcery = this.addTarget(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), Spells.instance()), "target instant or sorcery spell");

			EventFactory changeTargets = new EventFactory(EventType.CHANGE_TARGETS, "Change the targets of target instant or sorcery spell.");
			changeTargets.parameters.put(EventType.Parameter.OBJECT, targetedBy(instantOrSorcery));
			changeTargets.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(youMay(changeTargets, "You may change the targets of target instant or sorcery spell."));
		}
	}

	public GoblinFlectomancer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Sacrifice Goblin Flectomancer: You may change the targets of target
		// instant or sorcery spell.
		this.addAbility(new GoblinFlectomancerAbility0(state));
	}
}
