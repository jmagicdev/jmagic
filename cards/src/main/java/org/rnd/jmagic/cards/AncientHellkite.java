package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ancient Hellkite")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("4RRR")
@ColorIdentity({Color.RED})
public final class AncientHellkite extends Card
{
	public static final class AncientHellkiteAbility1 extends ActivatedAbility
	{
		public AncientHellkiteAbility1(GameState state)
		{
			super(state, "(R): Ancient Hellkite deals 1 damage to target creature defending player controls. Activate this ability only if Ancient Hellkite is attacking.");
			this.setManaCost(new ManaPool("(R)"));
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS))), "target creature defending player controls"));
			this.addEffect(permanentDealDamage(1, target, "Ancient Hellkite deals 1 damage to target creature defending player controls."));
			this.addActivateRestriction(Not.instance(Intersect.instance(Attacking.instance(), ABILITY_SOURCE_OF_THIS)));
		}
	}

	public AncientHellkite(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (R): Ancient Hellkite deals 1 damage to target creature defending
		// player controls. Activate this ability only if Ancient Hellkite is
		// attacking.
		this.addAbility(new AncientHellkiteAbility1(state));
	}
}
