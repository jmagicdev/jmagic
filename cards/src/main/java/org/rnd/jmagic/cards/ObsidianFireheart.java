package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Obsidian Fireheart")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("1RRR")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class ObsidianFireheart extends Card
{
	public static final class TheLandContinuesToBurn extends EventTriggeredAbility
	{
		public TheLandContinuesToBurn(GameState state)
		{
			super(state, "At the beginning of your upkeep, this land deals 1 damage to you.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(permanentDealDamage(1, You.instance(), "This land deals 1 damage to you."));
		}
	}

	public static final class BlazeCounters extends ActivatedAbility
	{
		public BlazeCounters(GameState state)
		{
			super(state, "(1)(R)(R): Put a blaze counter on target land without a blaze counter on it. For as long as that land has a blaze counter on it, it has \"At the beginning of your upkeep, this land deals 1 damage to you.\"");
			this.setManaCost(new ManaPool("1RR"));

			SetGenerator hasBlazeCounters = HasCounterOfType.instance(Counter.CounterType.BLAZE);
			SetGenerator landsWithoutCounters = RelativeComplement.instance(LandPermanents.instance(), hasBlazeCounters);
			Target target = this.addTarget(landsWithoutCounters, "target land without a blaze counter on it");
			this.addEffect(putCounters(1, Counter.CounterType.BLAZE, targetedBy(target), "Put a blaze counter on target land without a blaze counter on it."));

			ContinuousEffect.Part part = addAbilityToObject(targetedBy(target), TheLandContinuesToBurn.class);
			SetGenerator thatLandHasABlazeCounter = Intersect.instance(targetedBy(target), hasBlazeCounters);

			this.addEffect(createFloatingEffect(Not.instance(thatLandHasABlazeCounter), "For as long as that land has a blaze counter on it, it has \"At the beginning of your upkeep, this land deals 1 damage to you.\"", part));
		}
	}

	public ObsidianFireheart(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// (1)(R)(R): Put a blaze counter on target land without a blaze counter
		// on it. For as long as that land has a blaze counter on it, it has
		// "At the beginning of your upkeep, this land deals 1 damage to you."
		this.addAbility(new BlazeCounters(state));
	}
}
