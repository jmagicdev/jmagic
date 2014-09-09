package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sylvok Replica")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.SHAMAN})
@ManaCost("3")
@ColorIdentity({Color.GREEN})
public final class SylvokReplica extends Card
{
	public static final class SylvokReplicaAbility0 extends ActivatedAbility
	{
		public SylvokReplicaAbility0(GameState state)
		{
			super(state, "(G), Sacrifice Sylvok Replica: Destroy target artifact or enchantment.");
			this.setManaCost(new ManaPool("(G)"));
			this.addCost(sacrificeThis("Sylvok Replica"));
			SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment"));
			this.addEffect(destroy(target, "Destroy target artifact or enchantment."));
		}
	}

	public SylvokReplica(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// (G), Sacrifice Sylvok Replica: Destroy target artifact or
		// enchantment.
		this.addAbility(new SylvokReplicaAbility0(state));
	}
}
