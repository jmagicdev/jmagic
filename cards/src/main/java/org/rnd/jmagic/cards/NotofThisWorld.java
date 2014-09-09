package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Not of This World")
@Types({Type.TRIBAL, Type.INSTANT})
@SubTypes({SubType.ELDRAZI})
@ManaCost("7")
@ColorIdentity({})
public final class NotofThisWorld extends Card
{
	public static final class NotofThisWorldAbility1 extends StaticAbility
	{
		public NotofThisWorldAbility1(GameState state)
		{
			super(state, "Not of This World costs (7) less to cast if it targets a spell or ability that targets a creature you control with power 7 or greater.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("7")));
			this.addEffectPart(part);

			SetGenerator targetOfThis = ChosenTargetsFor.instance(AllTargetsOf.instance(This.instance()), This.instance());
			SetGenerator targetedByThis = ExtractTargets.instance(targetOfThis);
			SetGenerator targetOfTarget = ChosenTargetsFor.instance(AllTargetsOf.instance(targetedByThis), targetedByThis);
			SetGenerator targetedByTarget = ExtractTargets.instance(targetOfTarget);
			SetGenerator yourBigGuys = Intersect.instance(CREATURES_YOU_CONTROL, HasPower.instance(Between.instance(7, null)));
			this.canApply = Intersect.instance(targetedByTarget, yourBigGuys);
		}
	}

	public NotofThisWorld(GameState state)
	{
		super(state);

		// Counter target spell or ability that targets a permanent you control.
		SetGenerator targetingYourStuff = HasTarget.instance(ControlledBy.instance(You.instance()));
		SetGenerator target = targetedBy(this.addTarget(targetingYourStuff, "target spell or ability that targets a permanent you control"));
		this.addEffect(counter(target, "Counter target spell or ability that targets a permanent you control."));

		// Not of This World costs (7) less to cast if it targets a spell or
		// ability that targets a creature you control with power 7 or greater.
		this.addAbility(new NotofThisWorldAbility1(state));
	}
}
