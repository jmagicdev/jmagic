package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Xenagos, God of Revels")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.GOD})
@ManaCost("3RG")
@ColorIdentity({Color.RED, Color.GREEN})
public final class XenagosGodofRevels extends Card
{
	public static final class XenagosGodofRevelsAbility1 extends StaticAbility
	{
		public XenagosGodofRevelsAbility1(GameState state)
		{
			super(state, "As long as your devotion to red and green is less than seven, Xenagos isn't a creature.");

			SetGenerator notEnoughDevotion = Intersect.instance(Between.instance(null, 6), DevotionTo.instance(Color.RED, Color.GREEN));
			this.canApply = Both.instance(this.canApply, notEnoughDevotion);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public static final class XenagosGodofRevelsAbility2 extends EventTriggeredAbility
	{
		public XenagosGodofRevelsAbility2(GameState state)
		{
			super(state, "At the beginning of combat on your turn, another target creature you control gains haste and gets +X/+X until end of turn, where X is that creature's power.");

			SimpleEventPattern beginningOfCombat = new SimpleEventPattern(EventType.BEGIN_STEP);
			beginningOfCombat.put(EventType.Parameter.STEP, BeginningOfCombatStepOf.instance(You.instance()));
			this.addPattern(beginningOfCombat);

			SetGenerator yourCreatures = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.CREATURE));
			SetGenerator anotherCreature = RelativeComplement.instance(yourCreatures, ABILITY_SOURCE_OF_THIS);
			SetGenerator target = targetedBy(this.addTarget(anotherCreature, "another target creature you control"));
			SetGenerator X = PowerOf.instance(target);
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, X, X, "Target creature you control gains haste and gets +X/+X until end of turn, where X is that creature's power.", org.rnd.jmagic.abilities.keywords.Haste.class));

		}
	}

	public XenagosGodofRevels(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// As long as your devotion to red and green is less than seven, Xenagos
		// isn't a creature.
		this.addAbility(new XenagosGodofRevelsAbility1(state));

		// At the beginning of combat on your turn, another target creature you
		// control gains haste and gets +X/+X until end of turn, where X is that
		// creature's power.
		this.addAbility(new XenagosGodofRevelsAbility2(state));
	}
}
