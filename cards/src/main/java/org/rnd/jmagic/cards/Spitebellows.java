package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spitebellows")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("5R")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MORNINGTIDE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Spitebellows extends Card
{
	public static final class SpitebellowsAbility0 extends EventTriggeredAbility
	{
		public SpitebellowsAbility0(GameState state)
		{
			super(state, "When Spitebellows leaves the battlefield, it deals 6 damage to target creature.");
			this.addPattern(whenThisLeavesTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(permanentDealDamage(6, target, "It deals 6 damage to target creature."));
		}
	}

	public Spitebellows(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(1);

		// When Spitebellows leaves the battlefield, it deals 6 damage to target
		// creature.
		this.addAbility(new SpitebellowsAbility0(state));

		// Evoke (1)(R)(R) (You may cast this spell for its evoke cost. If you
		// do, it's sacrificed when it enters the battlefield.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evoke(state, "(1)(R)(R)"));
	}
}
