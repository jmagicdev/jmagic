package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Skybreen")
@Types({Type.PLANE})
@SubTypes({SubType.KALDHEIM})
@ColorIdentity({})
public final class Skybreen extends Card
{
	public static final class RevealTopOfLibrary extends StaticAbility
	{
		public RevealTopOfLibrary(GameState state)
		{
			super(state, "Players play with the top card of their libraries revealed.");

			SetGenerator library = LibraryOf.instance(Players.instance());
			SetGenerator topCardOfLibrary = TopCards.instance(1, library);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REVEAL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, topCardOfLibrary);
			this.addEffectPart(part);

			this.canApply = PlanechaseGameRules.staticAbilityCanApply;
		}
	}

	public static final class RestrictCasts extends StaticAbility
	{
		public RestrictCasts(GameState state)
		{
			super(state, "Spells that share a card type with the top card of a library can't be cast.");

			SetGenerator types = TypesOf.instance(TopCards.instance(1, LibraryOf.instance(Players.instance())));
			PlayProhibition prohibitPattern = new PlayProhibition(Players.instance(),//
			(c, data) -> !java.util.Collections.disjoint(c.types, data.getAll(Type.class)), types);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);

			this.canApply = PlanechaseGameRules.staticAbilityCanApply;
		}
	}

	public static final class ChaoticIntelligence extends EventTriggeredAbility
	{
		public ChaoticIntelligence(GameState state)
		{
			super(state, "Whenever you roll (C), target player loses life equal to the number of cards in his or her hand.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(loseLife(targetedBy(target), Count.instance(InZone.instance(HandOf.instance(targetedBy(target)))), "Target player loses life equal to the number of cards in his or her hand."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public Skybreen(GameState state)
	{
		super(state);

		this.addAbility(new RevealTopOfLibrary(state));

		this.addAbility(new RestrictCasts(state));

		this.addAbility(new ChaoticIntelligence(state));
	}
}
