package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Executioner's Capsule")
@Types({Type.ARTIFACT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class ExecutionersCapsule extends Card
{
	public static final class Execute extends ActivatedAbility
	{
		public Execute(GameState state)
		{
			super(state, "(1)(B), (T), Sacrifice Executioner's Capsule: Destroy target nonblack creature.");
			this.setManaCost(new ManaPool("1B"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Executioner's Capsule"));

			Target target = this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK)), "target nonblack creature");
			this.addEffect(destroy(targetedBy(target), "Destroy target nonblack creature."));
		}
	}

	public ExecutionersCapsule(GameState state)
	{
		super(state);

		// (1)(B), (T), Sacrifice Executioner's Capsule: Destroy target nonblack
		// creature.
		this.addAbility(new Execute(state));
	}
}
