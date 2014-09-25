package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Heart-Piercer Bow")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@ColorIdentity({})
public final class HeartPiercerBow extends Card
{
	public static final class HeartPiercerBowAbility0 extends EventTriggeredAbility
	{
		public HeartPiercerBowAbility0(GameState state)
		{
			super(state, "Whenever equipped creature attacks, Heart-Piercer Bow deals 1 damage to target creature defending player controls.");

			SetGenerator equippedCreature = EquippedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addPattern(whenXAttacks(equippedCreature));

			SetGenerator defendingPlayer = DefendingPlayer.instance(equippedCreature);
			SetGenerator theirCreatures = Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(defendingPlayer));
			SetGenerator target = targetedBy(this.addTarget(theirCreatures, "target creature defending player controls"));
			this.addEffect(permanentDealDamage(1, target, "Heart-Piercer Bow deals 1 damage to target creature defending player controls."));
		}
	}

	public HeartPiercerBow(GameState state)
	{
		super(state);

		// Whenever equipped creature attacks, Heart-Piercer Bow deals 1 damage
		// to target creature defending player controls.
		this.addAbility(new HeartPiercerBowAbility0(state));

		// Equip (1)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
