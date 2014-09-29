package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wingmate Roc")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("3WW")
@ColorIdentity({Color.WHITE})
public final class WingmateRoc extends Card
{
	public static final class WingmateRocAbility1 extends EventTriggeredAbility
	{
		public WingmateRocAbility1(GameState state)
		{
			super(state, "Raid \u2014 When Wingmate Roc enters the battlefield, if you attacked with a creature this turn, put a 3/4 white Bird creature token with flying onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			state.ensureTracker(new org.rnd.jmagic.engine.trackers.AttackTracker());
			this.interveningIf = Raid.instance();

			CreateTokensFactory bird = new CreateTokensFactory(1, 3, 4, "Put a 3/4 white Bird creature token with flying onto the battlefield.");
			bird.setColors(Color.WHITE);
			bird.setSubTypes(SubType.BIRD);
			bird.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(bird.getEventFactory());
		}
	}

	public static final class WingmateRocAbility2 extends EventTriggeredAbility
	{
		public WingmateRocAbility2(GameState state)
		{
			super(state, "Whenever Wingmate Roc attacks, you gain 1 life for each attacking creature.");
			this.addPattern(whenThisAttacks());

			this.addEffect(gainLife(You.instance(), Count.instance(Attacking.instance()), "You gain 1 life for each attacking creature."));
		}
	}

	public WingmateRoc(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Raid \u2014 When Wingmate Roc enters the battlefield, if you attacked
		// with a creature this turn, put a 3/4 white Bird creature token with
		// flying onto the battlefield.
		this.addAbility(new WingmateRocAbility1(state));

		// Whenever Wingmate Roc attacks, you gain 1 life for each attacking
		// creature.
		this.addAbility(new WingmateRocAbility2(state));
	}
}
