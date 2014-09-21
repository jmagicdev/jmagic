package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Anafenza, the Foremost")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("WBG")
@ColorIdentity({Color.WHITE, Color.BLACK, Color.GREEN})
public final class AnafenzatheForemost extends Card
{
	public static final class AnafenzatheForemostAbility0 extends EventTriggeredAbility
	{
		public AnafenzatheForemostAbility0(GameState state)
		{
			super(state, "Whenever Anafenza, the Foremost attacks, put a +1/+1 counter on another target tapped creature you control.");
			this.addPattern(whenThisAttacks());

			SetGenerator tappedCreatures = Intersect.instance(HasType.instance(Type.CREATURE), Tapped.instance());
			SetGenerator yourTappedCreatures = Intersect.instance(ControlledBy.instance(You.instance()), tappedCreatures);
			SetGenerator target = targetedBy(this.addTarget(yourTappedCreatures, "another target tapped creature you control"));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on another target tapped creature you control."));
		}
	}

	public static final class AnafenzatheForemostAbility1 extends StaticAbility
	{
		public AnafenzatheForemostAbility1(GameState state)
		{
			super(state, "If a creature card would be put into an opponent's graveyard from anywhere, exile it instead.");

			SetGenerator opponentsGraveyard = GraveyardOf.instance(OpponentsOf.instance(You.instance()));
			SetGenerator creatureCards = Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance());
			SimpleZoneChangePattern pattern = new SimpleZoneChangePattern(null, opponentsGraveyard, creatureCards, true);

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(state.game, "If a card would be put into an opponent's graveyard from anywhere, exile it instead");
			replacement.addPattern(pattern);
			replacement.changeDestination(ExileZone.instance());

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public AnafenzatheForemost(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Whenever Anafenza, the Foremost attacks, put a +1/+1 counter on
		// another target tapped creature you control.
		this.addAbility(new AnafenzatheForemostAbility0(state));

		// If a creature card would be put into an opponent's graveyard from
		// anywhere, exile it instead.
		this.addAbility(new AnafenzatheForemostAbility1(state));
	}
}
