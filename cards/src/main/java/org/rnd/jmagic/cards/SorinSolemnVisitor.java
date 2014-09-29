package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sorin, Solemn Visitor")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.SORIN})
@ManaCost("2WB")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class SorinSolemnVisitor extends Card
{
	public static final class DelayedEnd extends UntilNextTurn.EventAndBeginTurnTracker
	{
		public DelayedEnd()
		{
			super(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT);
		}
	}

	public static final class SorinSolemnVisitorAbility0 extends LoyaltyAbility
	{
		public SorinSolemnVisitorAbility0(GameState state)
		{
			super(state, +1, "Until your next turn, creatures you control get +1/+0 and gain lifelink.");

			SetGenerator expires = Not.instance(Intersect.instance(This.instance(), UntilNextTurn.instance(DelayedEnd.class)));
			state.ensureTracker(new DelayedEnd());

			EventFactory effect = ptChangeAndAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +0, "Until your next turn, creatures you control get +1/+0 and gain lifelink.", org.rnd.jmagic.abilities.keywords.Lifelink.class);
			effect.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(expires));
			this.addEffect(effect);
		}
	}

	public static final class SorinSolemnVisitorAbility1 extends LoyaltyAbility
	{
		public SorinSolemnVisitorAbility1(GameState state)
		{
			super(state, -2, "Put a 2/2 black Vampire creature token with flying onto the battlefield.");

			CreateTokensFactory vampire = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Vampire creature token with flying onto the battlefield.");
			vampire.setColors(Color.BLACK);
			vampire.setSubTypes(SubType.VAMPIRE);
			vampire.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(vampire.getEventFactory());
		}
	}

	public static final class SacStuff extends EventTriggeredAbility
	{
		public SacStuff(GameState state)
		{
			super(state, "At the beginning of each opponent's upkeep, that player sacrifices a creature.");
			this.addPattern(atTheBeginningOfEachOpponentsUpkeeps());

			SetGenerator thatPlayer = OwnerOf.instance(CurrentStep.instance());
			this.addEffect(sacrifice(thatPlayer, 1, HasType.instance(Type.CREATURE), "That player sacrifices a creature."));
		}
	}

	public static final class SorinSolemnVisitorAbility2 extends LoyaltyAbility
	{
		public SorinSolemnVisitorAbility2(GameState state)
		{
			super(state, -6, "You get an emblem with \"At the beginning of each opponent's upkeep, that player sacrifices a creature.\"");
		}
	}

	public SorinSolemnVisitor(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		// +1: Until your next turn, creatures you control get +1/+0 and gain
		// lifelink.
		this.addAbility(new SorinSolemnVisitorAbility0(state));

		// -2: Put a 2/2 black Vampire creature token with flying onto the
		// battlefield.
		this.addAbility(new SorinSolemnVisitorAbility1(state));

		// -6: You get an emblem with
		// "At the beginning of each opponent's upkeep, that player sacrifices a creature."
		this.addAbility(new SorinSolemnVisitorAbility2(state));
	}
}
