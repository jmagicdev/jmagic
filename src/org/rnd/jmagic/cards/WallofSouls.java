package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wall of Souls")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.STRONGHOLD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class WallofSouls extends Card
{
	public static final class SoulsHateBeingAttackedApparently extends EventTriggeredAbility
	{
		public SoulsHateBeingAttackedApparently(GameState state)
		{
			super(state, "Whenever Wall of Souls is dealt combat damage, it deals that much damage to target opponent.");

			this.addPattern(whenIsDealtCombatDamage(ABILITY_SOURCE_OF_THIS));

			SetGenerator amount = Count.instance(TriggerDamage.instance(This.instance()));
			Target target = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");
			this.addEffect(permanentDealDamage(amount, targetedBy(target), "Wall of Souls deals that much damage to target opponent."));
		}
	}

	public WallofSouls(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Whenever Wall of Souls is dealt combat damage, it deals that much
		// damage to target opponent.
		this.addAbility(new SoulsHateBeingAttackedApparently(state));
	}
}
