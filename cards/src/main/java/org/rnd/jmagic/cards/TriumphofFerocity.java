package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Triumph of Ferocity")
@Types({Type.ENCHANTMENT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class TriumphofFerocity extends Card
{
	public static final class TriumphofFerocityAbility0 extends EventTriggeredAbility
	{
		public TriumphofFerocityAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, draw a card if you control the creature with the greatest power or tied for the greatest power.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator greatestPower = Maximum.instance(PowerOf.instance(CreaturePermanents.instance()));
			SetGenerator yourBiggest = Maximum.instance(PowerOf.instance(CREATURES_YOU_CONTROL));
			SetGenerator condition = Intersect.instance(greatestPower, yourBiggest);

			this.addEffect(ifThen(condition, drawCards(You.instance(), 1, "Draw a card"), "Draw a card if you control the creature with the greatest power or tied for the greatest power."));
		}
	}

	public TriumphofFerocity(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, draw a card if you control the
		// creature with the greatest power or tied for the greatest power.
		this.addAbility(new TriumphofFerocityAbility0(state));
	}
}
