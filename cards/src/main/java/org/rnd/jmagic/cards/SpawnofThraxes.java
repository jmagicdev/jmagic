package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spawn of Thraxes")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("5RR")
@ColorIdentity({Color.RED})
public final class SpawnofThraxes extends Card
{
	public static final class SpawnofThraxesAbility1 extends EventTriggeredAbility
	{
		public SpawnofThraxesAbility1(GameState state)
		{
			super(state, "When Spawn of Thraxes enters the battlefield, it deals damage to target creature or player equal to the number of Mountains you control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			SetGenerator yourMountains = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.MOUNTAIN));
			SetGenerator dmg = Count.instance(yourMountains);
			this.addEffect(permanentDealDamage(dmg, target, "Spawn of Thraxes deals damage to target creature or player equal to the number of Mountains you control."));
		}
	}

	public SpawnofThraxes(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Spawn of Thraxes enters the battlefield, it deals damage to
		// target creature or player equal to the number of Mountains you
		// control.
		this.addAbility(new SpawnofThraxesAbility1(state));
	}
}
