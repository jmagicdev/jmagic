package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Woodland Sleuth")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SCOUT})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class WoodlandSleuth extends Card
{
	public static final class WoodlandSleuthAbility0 extends EventTriggeredAbility
	{
		public WoodlandSleuthAbility0(GameState state)
		{
			super(state, "When Woodland Sleuth enters the battlefield, if a creature died this turn, return a creature card at random from your graveyard to your hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			this.interveningIf = Morbid.instance();
			state.ensureTracker(new Morbid.Tracker());

			SetGenerator yourYard = GraveyardOf.instance(You.instance());
			EventFactory random = random(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(yourYard)), "Choose a creature card from your graveyard at random.");
			this.addEffect(random);

			this.addEffect(putIntoHand(EffectResult.instance(random), You.instance(), "Return a creature card at random from your graveyard to your hand."));

		}
	}

	public WoodlandSleuth(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Morbid \u2014 When Woodland Sleuth enters the battlefield, if a
		// creature died this turn, return a creature card at random from your
		// graveyard to your hand.
		this.addAbility(new WoodlandSleuthAbility0(state));
	}
}
