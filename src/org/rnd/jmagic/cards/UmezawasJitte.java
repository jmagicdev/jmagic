package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Umezawa's Jitte")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.BETRAYERS_OF_KAMIGAWA, r = Rarity.RARE)})
@ColorIdentity({})
public final class UmezawasJitte extends Card
{
	// Whenever equipped creature deals combat damage,
	public static final class DamageTrigger extends EventTriggeredAbility
	{
		public DamageTrigger(GameState state)
		{
			super(state, "Whenever equipped creature deals combat damage, put two charge counters on Umezawa's Jitte.");

			this.addPattern(whenDealsCombatDamage(EquippedBy.instance(ABILITY_SOURCE_OF_THIS)));

			// put two charge counters on Umezawa's Jitte.
			this.addEffect(putCounters(2, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put two charge counters on Umezawa's Jitte."));
		}
	}

	public static final class SwissArmyKnife extends ActivatedAbility
	{
		public SwissArmyKnife(GameState state)
		{
			super(state, "Remove a charge counter from Umezawa's Jitte: Choose one \u2014 Equipped creature gets +2/+2 until end of turn; or target creature gets -1/-1 until end of turn; or you gain 2 life.");

			this.addCost(removeCountersFromThis(1, Counter.CounterType.CHARGE, "Umezawa's Jitte"));

			this.addEffect(1, ptChangeUntilEndOfTurn(EquippedBy.instance(ABILITY_SOURCE_OF_THIS), +2, +2, "Equipped creature gets +2/+2 until end of turn"));

			Target target = this.addTarget(2, CreaturePermanents.instance(), "target creature");
			this.addEffect(2, ptChangeUntilEndOfTurn(targetedBy(target), -1, -1, "target creature gets -1/-1 until end of turn"));

			this.addEffect(3, gainLife(You.instance(), 2, "you gain 2 life."));
		}
	}

	public UmezawasJitte(GameState state)
	{
		super(state);

		this.addAbility(new DamageTrigger(state));
		this.addAbility(new SwissArmyKnife(state));

		// Equip {2}
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
