package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Razormane Masticore")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.MASTICORE})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.RARE)})
@ColorIdentity({})
public final class RazormaneMasticore extends Card
{
	public static final class UpkeepTrigger extends EventTriggeredAbility
	{
		public UpkeepTrigger(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice Razormane Masticore unless you discard a card.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory sacrifice = sacrificeThis("Razormane Masticore");
			EventFactory discard = discardCards(You.instance(), 1, "Discard a card");
			this.addEffect(unless(You.instance(), sacrifice, discard, "Sacrifice Razormane Masticore unless you discard a card."));
		}
	}

	public static final class DrawStepTrigger extends EventTriggeredAbility
	{
		public DrawStepTrigger(GameState state)
		{
			super(state, "At the beginning of your draw step, you may have Razormane Masticore deal 3 damage to target creature.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, DrawStepOf.instance(ControllerOf.instance(thisCard)));
			this.addPattern(pattern);

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(youMay(permanentDealDamage(3, targetedBy(target), "Razormane Masticore deals 3 damage to target creature"), "You may have Razormane Masticore deal 3 damage to target creature."));
		}
	}

	public RazormaneMasticore(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new UpkeepTrigger(state));
		this.addAbility(new DrawStepTrigger(state));
	}
}
