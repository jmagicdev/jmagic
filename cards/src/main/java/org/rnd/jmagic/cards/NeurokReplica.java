package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Neurok Replica")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.WIZARD})
@ManaCost("3")
@ColorIdentity({Color.BLUE})
public final class NeurokReplica extends Card
{
	public static final class NeurokReplicaAbility0 extends ActivatedAbility
	{
		public NeurokReplicaAbility0(GameState state)
		{
			super(state, "(1)(U), Sacrifice Neurok Replica: Return target creature to its owner's hand.");
			this.setManaCost(new ManaPool("(1)(U)"));
			this.addCost(sacrificeThis("Neurok Replica"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(bounce(target, "Return target creature to its owner's hand."));
		}
	}

	public NeurokReplica(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// (1)(U), Sacrifice Neurok Replica: Return target creature to its
		// owner's hand.
		this.addAbility(new NeurokReplicaAbility0(state));
	}
}
