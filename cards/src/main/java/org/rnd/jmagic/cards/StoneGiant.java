package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stone Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.BETA, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class StoneGiant extends Card
{
	public static final class Toss extends ActivatedAbility
	{
		public Toss(GameState state)
		{
			super(state, "(T): Target creature you control with toughness less than Stone Giant's power gains flying until end of turn. Destroy that creature at the beginning of the next end step.");

			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(ControlledBy.instance(You.instance()), HasToughness.instance(Between.instance(Empty.instance(), Subtract.instance(PowerOf.instance(ABILITY_SOURCE_OF_THIS), numberGenerator(1))))), "target creature creature you control with toughness less than Stone Giant's power");

			this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Flying.class, "Target creature you control with toughness less than Stone Giant's power gains flying until end of turn."));

			SetGenerator thatCreature = delayedTriggerContext(targetedBy(target));
			EventFactory destroy = destroy(thatCreature, "Destroy that creature");

			EventFactory destroyLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Destroy that creature at the beginning of the next end step.");
			destroyLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
			destroyLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
			destroyLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(destroy));
			this.addEffect(destroyLater);
		}
	}

	public StoneGiant(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		this.addAbility(new Toss(state));
	}
}
