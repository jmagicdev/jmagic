package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Timely Hordemate")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class TimelyHordemate extends Card
{
	public static final class TimelyHordemateAbility0 extends EventTriggeredAbility
	{
		public TimelyHordemateAbility0(GameState state)
		{
			super(state, "When Timely Hordemate enters the battlefield, if you attacked with a creature this turn, return target creature card with converted mana cost 2 or less from your graveyard to the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			state.ensureTracker(new org.rnd.jmagic.engine.trackers.AttackTracker());
			this.interveningIf = Raid.instance();

			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator deadSmallThings = Intersect.instance(HasConvertedManaCost.instance(Between.instance(null, 2)), deadThings);
			SetGenerator target = targetedBy(this.addTarget(deadSmallThings, "target creature card with converted mana cost 2 or less from your graveyard"));
			this.addEffect(putOntoBattlefield(target, "Return target creature card with converted mana cost 2 or less from your graveyard to the battlefield."));
		}
	}

	public TimelyHordemate(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Raid \u2014 When Timely Hordemate enters the battlefield, if you
		// attacked with a creature this turn, return target creature card with
		// converted mana cost 2 or less from your graveyard to the battlefield.
		this.addAbility(new TimelyHordemateAbility0(state));
	}
}
