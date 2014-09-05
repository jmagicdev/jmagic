package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Moriok Replica")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.WARRIOR})
@ManaCost("3")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class MoriokReplica extends Card
{
	public static final class MoriokReplicaAbility0 extends ActivatedAbility
	{
		public MoriokReplicaAbility0(GameState state)
		{
			super(state, "(1)(B), Sacrifice Moriok Replica: You draw two cards and lose 2 life.");
			this.setManaCost(new ManaPool("(1)(B)"));
			this.addCost(sacrificeThis("Moriok Replica"));
			this.addEffect(drawCards(You.instance(), 2, "You draw two cards"));
			this.addEffect(loseLife(You.instance(), 2, "and lose 2 life."));
		}
	}

	public MoriokReplica(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (1)(B), Sacrifice Moriok Replica: You draw two cards and lose 2 life.
		this.addAbility(new MoriokReplicaAbility0(state));
	}
}
