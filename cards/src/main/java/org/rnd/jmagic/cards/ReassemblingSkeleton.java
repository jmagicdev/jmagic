package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reassembling Skeleton")
@Types({Type.CREATURE})
@SubTypes({SubType.SKELETON, SubType.WARRIOR})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class ReassemblingSkeleton extends Card
{
	public static final class ReassemblingSkeletonAbility0 extends ActivatedAbility
	{
		public ReassemblingSkeletonAbility0(GameState state)
		{
			super(state, "(1)(B): Return Reassembling Skeleton from your graveyard to the battlefield tapped.");
			this.setManaCost(new ManaPool("(1)(B)"));

			EventFactory move = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_TAPPED, "Return Reassembling Skeleton from your graveyard to the battlefield tapped.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			move.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addEffect(move);

			this.activateOnlyFromGraveyard();
		}
	}

	public ReassemblingSkeleton(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (1)(B): Return Reassembling Skeleton from your graveyard to the
		// battlefield tapped.
		this.addAbility(new ReassemblingSkeletonAbility0(state));
	}
}
