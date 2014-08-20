package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Velis Vel")
@Types({Type.PLANE})
@SubTypes({SubType.LORWYN})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class VelisVel extends Card
{
	public static final class CoatOfArmsEffect extends StaticAbility
	{
		public CoatOfArmsEffect(GameState state)
		{
			super(state, "Each creature gets +1/+1 for each other creature on the battlefield that shares at least one creature type with it.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(CoatofArms.Mafia.COAT_OF_ARMS_EFFECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, CreaturePermanents.instance());
			this.addEffectPart(part);

			this.canApply = Planechase.staticAbilityCanApply;
		}
	}

	public static final class ChangelingChaos extends EventTriggeredAbility
	{
		public ChangelingChaos(GameState state)
		{
			super(state, "Whenever you roll (C), target creature gains all creature types until end of turn.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.fromCollection(SubType.getAllCreatureTypes()));
			this.addEffect(createFloatingEffect("Target creature gains all creature types until end of turn.", part));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public VelisVel(GameState state)
	{
		super(state);

		this.addAbility(new CoatOfArmsEffect(state));

		this.addAbility(new ChangelingChaos(state));
	}
}
