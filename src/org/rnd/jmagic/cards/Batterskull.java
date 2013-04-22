package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Batterskull")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.MYTHIC)})
@ColorIdentity({})
public final class Batterskull extends Card
{
	public static final class BatterskullAbility1 extends StaticAbility
	{
		public BatterskullAbility1(GameState state)
		{
			super(state, "Equipped creature gets +4/+4 and has vigilance and lifelink.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(equippedCreature, +4, +4));
			this.addEffectPart(addAbilityToObject(equippedCreature, org.rnd.jmagic.abilities.keywords.Vigilance.class, org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}

	public static final class BatterskullAbility2 extends ActivatedAbility
	{
		public BatterskullAbility2(GameState state)
		{
			super(state, "(3): Return Batterskull to its owner's hand.");
			this.setManaCost(new ManaPool("(3)"));
			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return Batterskull to its owner's hand."));
		}
	}

	public Batterskull(GameState state)
	{
		super(state);

		// Living weapon (When this Equipment enters the battlefield, put a 0/0
		// black Germ creature token onto the battlefield, then attach this to
		// it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LivingWeapon(state));

		// Equipped creature gets +4/+4 and has vigilance and lifelink.
		this.addAbility(new BatterskullAbility1(state));

		// (3): Return Batterskull to its owner's hand.
		this.addAbility(new BatterskullAbility2(state));

		// Equip (5)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(5)"));
	}
}
