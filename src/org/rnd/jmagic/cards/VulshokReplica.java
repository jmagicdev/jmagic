package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vulshok Replica")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.BERSERKER})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class VulshokReplica extends Card
{
	public static final class VulshokReplicaAbility0 extends ActivatedAbility
	{
		public VulshokReplicaAbility0(GameState state)
		{
			super(state, "(1)(R), Sacrifice Vulshok Replica: Vulshok Replica deals 3 damage to target player.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.addCost(sacrificeThis("Vulshok Replica"));
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(permanentDealDamage(3, target, "Vulshok Replica deals 3 damage to target player."));
		}
	}

	public VulshokReplica(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// (1)(R), Sacrifice Vulshok Replica: Vulshok Replica deals 3 damage to
		// target player.
		this.addAbility(new VulshokReplicaAbility0(state));
	}
}
