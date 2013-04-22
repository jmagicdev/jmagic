package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Celestine Reef")
@Types({Type.PLANE})
@SubTypes({SubType.LUVION})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.SPECIAL)})
@ColorIdentity({})
public final class CelestineReef extends Card
{
	public static final class RestrictAttacking extends StaticAbility
	{
		public RestrictAttacking(GameState state)
		{
			super(state, "Creatures without flying or islandwalk can't attack.");

			SetGenerator restriction = RelativeComplement.instance(Attacking.instance(), Union.instance(HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk.class)));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(part);

			this.canApply = Planechase.staticAbilityCanApply;
		}
	}

	public static final class PlatinumChaos extends EventTriggeredAbility
	{
		public PlatinumChaos(GameState state)
		{
			super(state, "Whenever you roll (C), until a player planeswalks, you can't lose the game and your opponents can't win the game.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			SimpleEventPattern loseEvent = new SimpleEventPattern(EventType.LOSE_GAME);
			loseEvent.put(EventType.Parameter.PLAYER, You.instance());

			SimpleEventPattern winEvent = new SimpleEventPattern(EventType.WIN_GAME);
			winEvent.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(winEvent, loseEvent));

			EventFactory factory = new EventFactory(Planechase.CREATE_FCE_UNTIL_A_PLAYER_PLANESWALKS, "You can't lose the game and your opponents can't win the game.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
			this.addEffect(factory);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public CelestineReef(GameState state)
	{
		super(state);

		this.addAbility(new RestrictAttacking(state));

		this.addAbility(new PlatinumChaos(state));
	}
}
