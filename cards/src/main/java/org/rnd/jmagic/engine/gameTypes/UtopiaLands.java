package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Utopia lands")
@Description("Basic lands can tap for mana of any color")
public class UtopiaLands extends GameType.SimpleGameTypeRule
{
	public static final ContinuousEffectType UTOPIA_REMOVE_INTRINSICS = new ContinuousEffectType("UTOPIA_REMOVE_INTRINSICS")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public void apply(GameState state, ContinuousEffect effect, java.util.Map<Parameter, Set> parameters)
		{
			ContinuousEffectType.REMOVE_ABILITY_FROM_OBJECT.apply(state, effect, parameters);
		}

		@Override
		public Layer layer()
		{
			return Layer.RULE_CHANGE;
		}

	};

	public static final ContinuousEffectType UTOPIA_ADD_ABILITY = new ContinuousEffectType("UTOPIA_ADD_ABILITY")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public void apply(GameState state, ContinuousEffect effect, java.util.Map<Parameter, Set> parameters)
		{
			ContinuousEffectType.ADD_ABILITY_TO_OBJECT.apply(state, effect, parameters);
		}

		@Override
		public Layer layer()
		{
			return Layer.RULE_CHANGE;
		}

	};

	@Override
	public void modifyGameState(GameState physicalState)
	{
		Intersect basicLandsInPlay = Intersect.instance(LandPermanents.instance(), HasSuperType.instance(SuperType.BASIC));

		ContinuousEffect.Part part1 = new ContinuousEffect.Part(UTOPIA_REMOVE_INTRINSICS);
		part1.parameters.put(ContinuousEffectType.Parameter.OBJECT, basicLandsInPlay);
		part1.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(Game.IntrinsicManaAbility.class));

		ContinuousEffect.Part part2 = new ContinuousEffect.Part(UTOPIA_ADD_ABILITY);
		part2.parameters.put(ContinuousEffectType.Parameter.OBJECT, basicLandsInPlay);
		part2.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(org.rnd.jmagic.abilities.TapForAnyColor.class)));

		EventFactory factory = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Basic lands have \"(T): Add one mana of any color to your mana pool.\"");
		factory.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(part1, part2));
		factory.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));

		factory.createEvent(physicalState.game, null).perform(null, true);
	}
}
