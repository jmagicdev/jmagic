package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleEventPattern;

@Name("Might Makes Right")
@Types({Type.ENCHANTMENT})
@ManaCost("5R")
@ColorIdentity({Color.RED})
public final class MightMakesRight extends Card
{
	public static final class MightMakesRightAbility0 extends EventTriggeredAbility
	{
		public MightMakesRightAbility0(GameState state)
		{
			super(state, "At the beginning of combat on your turn, if you control each creature on the battlefield with the greatest power, gain control of target creature an opponent controls until end of turn. Untap that creature. It gains haste until end of turn.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, BeginningOfCombatStepOf.instance(You.instance()));
			this.addPattern(pattern);

			SetGenerator greatestPower = Maximum.instance(PowerOf.instance(CreaturePermanents.instance()));
			SetGenerator biggestCreatures = Intersect.instance(HasPower.instance(greatestPower), Permanents.instance());
			SetGenerator othersHaveSome = RelativeComplement.instance(biggestCreatures, ControlledBy.instance(You.instance()));
			this.interveningIf = Not.instance(othersHaveSome);

			SetGenerator opponentsCreatures = Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			SetGenerator target = targetedBy(this.addTarget(opponentsCreatures, "target creature an opponent controls"));

			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(createFloatingEffect("Gain control of target creature an opponent controls until end of turn.", controlPart));
			this.addEffect(untap(target, "Untap that creature."));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Haste.class, "It gains haste until end of turn."));
		}
	}

	public MightMakesRight(GameState state)
	{
		super(state);

		// At the beginning of combat on your turn, if you control each creature
		// on the battlefield with the greatest power, gain control of target
		// creature an opponent controls until end of turn. Untap that creature.
		// It gains haste until end of turn. (It can attack and (T) this turn.)
		this.addAbility(new MightMakesRightAbility0(state));
	}
}
