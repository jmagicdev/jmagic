package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Skybreen")
@Types({Type.PLANE})
@SubTypes({SubType.KALDHEIM})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
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

			this.canApply = Planechase.staticAbilityCanApply;
		}
	}

	public static final class RestrictCasts extends StaticAbility
	{
		private static final class SpellsThatShareAColor implements SetPattern
		{
			@Override
			public boolean match(GameState state, Identified thisObject, Set set)
			{
				if(!SetPattern.CASTABLE.match(state, thisObject, set))
					return false;

				java.util.Set<Type> types = java.util.EnumSet.noneOf(Type.class);

				for(Player player: state.players)
				{
					Zone library = player.getLibrary(state);
					if(library.objects.isEmpty())
						continue;
					types.addAll(library.objects.get(0).getTypes());
				}

				for(GameObject object: set.getAll(GameObject.class))
					for(Type type: types)
						if(object.getTypes().contains(type))
							return true;

				return false;
			}

			@Override
			public void freeze(GameState state, Identified thisObject)
			{
				// Nothing to freeze
			}
		}

		public RestrictCasts(GameState state)
		{
			super(state, "Spells that share a card type with the top card of a library can't be cast.");

			SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			prohibitPattern.put(EventType.Parameter.OBJECT, new SpellsThatShareAColor());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);

			this.canApply = Planechase.staticAbilityCanApply;
		}
	}

	public static final class ChaoticIntelligence extends EventTriggeredAbility
	{
		public ChaoticIntelligence(GameState state)
		{
			super(state, "Whenever you roll (C), target player loses life equal to the number of cards in his or her hand.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(loseLife(targetedBy(target), Count.instance(InZone.instance(HandOf.instance(targetedBy(target)))), "Target player loses life equal to the number of cards in his or her hand."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
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
