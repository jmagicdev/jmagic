package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mortarpod")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class Mortarpod extends Card
{
	public static final class MortarpodAbility1 extends StaticAbility
	{
		public static final class SacPing extends ActivatedAbility
		{
			public SacPing(GameState state)
			{
				super(state, "Sacrifice this creature: This creature deals 1 damage to target creature or player.");
				this.addCost(sacrificeThis("this creature"));
				Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
				this.addEffect(permanentDealDamage(1, targetedBy(target), "This creature deals 1 damage to target creature or player."));
			}
		}

		public MortarpodAbility1(GameState state)
		{
			super(state, "Equipped creature gets +0/+1 and has \"Sacrifice this creature: This creature deals 1 damage to target creature or player.\"");
			SetGenerator equippedCreature = EquippedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(equippedCreature, +0, +1));
			this.addEffectPart(addAbilityToObject(equippedCreature, SacPing.class));
		}
	}

	public Mortarpod(GameState state)
	{
		super(state);

		// Living weapon (When this Equipment enters the battlefield, put a 0/0
		// black Germ creature token onto the battlefield, then attach this to
		// it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LivingWeapon(state));

		// Equipped creature gets +0/+1 and has
		// "Sacrifice this creature: This creature deals 1 damage to target creature or player."
		this.addAbility(new MortarpodAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
