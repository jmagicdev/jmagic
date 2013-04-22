package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Xantid Swarm")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.SCOURGE, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class XantidSwarm extends Card
{
	public static final class XantidSwarmAbility1 extends EventTriggeredAbility
	{
		public XantidSwarmAbility1(GameState state)
		{
			super(state, "Whenever Xantid Swarm attacks, defending player can't cast spells this turn.");
			this.addPattern(whenThisAttacks());

			SetGenerator defendingPlayer = Intersect.instance(Players.instance(), DefendingPlayersThisTurnOf.instance(ABILITY_SOURCE_OF_THIS));

			SimpleEventPattern targetCasts = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			targetCasts.put(EventType.Parameter.PLAYER, defendingPlayer);
			targetCasts.put(EventType.Parameter.OBJECT, SetPattern.CASTABLE);

			ContinuousEffect.Part noCasting = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			noCasting.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(targetCasts));
			this.addEffect(createFloatingEffect("Defending player can't cast spells this turn.", noCasting));
		}
	}

	public XantidSwarm(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Xantid Swarm attacks, defending player can't cast spells
		// this turn.
		this.addAbility(new XantidSwarmAbility1(state));

		state.ensureTracker(new SuccessfullyAttacked());
	}
}
