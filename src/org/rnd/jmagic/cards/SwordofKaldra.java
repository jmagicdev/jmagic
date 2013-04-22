package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sword of Kaldra")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.RARE), @Printings.Printed(ex = Expansion.PROMO, r = Rarity.RARE)})
@ColorIdentity({})
public final class SwordofKaldra extends Card
{
	public static final class DamageKillsHard extends EventTriggeredAbility
	{
		public DamageKillsHard(GameState state)
		{
			super(state, "Whenever equipped creature deals damage to a creature, exile that creature.");
			this.addPattern(whenDealsCombatDamageToACreature(EquippedBy.instance(ABILITY_SOURCE_OF_THIS)));
			this.addEffect(exile(TakerOfDamage.instance(TriggerDamage.instance(This.instance())), "Exile that creature."));
		}
	}

	public SwordofKaldra(GameState state)
	{
		super(state);

		// Equipped creature gets +5/+5.
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, EquippedBy.instance(This.instance()), "Equipped creature", +5, +5, false));

		// Whenever equipped creature deals damage to a creature, exile that
		// creature.
		this.addAbility(new DamageKillsHard(state));

		// Equip (4)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(4)"));
	}
}
