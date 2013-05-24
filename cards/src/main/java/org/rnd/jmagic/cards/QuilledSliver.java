package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Quilled Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class QuilledSliver extends Card
{
	@Name("\"(T): This permanent deals 1 damage to target attacking or blocking creature.\"")
	public static final class SliverPing extends ActivatedAbility
	{
		public SliverPing(GameState state)
		{
			super(state, "(T): This permanent deals 1 damage to target attacking or blocking creature.");

			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), Union.instance(Attacking.instance(), Blocking.instance())), "target attacking or blocking creature");

			this.addEffect(permanentDealDamage(1, targetedBy(target), "This permanent deals 1 damage to target attacking or blocking creature."));
		}
	}

	public QuilledSliver(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// All Slivers have
		// "(T): This permanent deals 1 damage to target attacking or blocking creature."
		this.addAbility(new org.rnd.jmagic.abilities.AllSliversHave(state, SliverPing.class, "All Slivers have \"(T): This permanent deals 1 damage to target attacking or blocking creature.\""));
	}
}
