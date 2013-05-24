package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Triumph of Cruelty")
@Types({Type.ENCHANTMENT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class TriumphofCruelty extends Card
{
	public static final class TriumphofCrueltyAbility0 extends EventTriggeredAbility
	{
		public TriumphofCrueltyAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, target opponent discards a card if you control the creature with the greatest power or tied for the greatest power.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator greatestPower = Maximum.instance(PowerOf.instance(CreaturePermanents.instance()));
			SetGenerator yourBiggest = Maximum.instance(PowerOf.instance(CREATURES_YOU_CONTROL));
			SetGenerator condition = Intersect.instance(greatestPower, yourBiggest);

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			this.addEffect(ifThen(condition, discardCards(target, 1, "Target opponent discards a card"), "Target opponent discards a card if you control the creature with the greatest power or tied for the greatest power."));
		}
	}

	public TriumphofCruelty(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, target opponent discards a card if
		// you control the creature with the greatest power or tied for the
		// greatest power.
		this.addAbility(new TriumphofCrueltyAbility0(state));
	}
}
