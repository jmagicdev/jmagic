package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Stonehorn Dignitary")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.RHINO})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class StonehornDignitary extends Card
{
	public static final class StonehornDignitaryAbility0 extends EventTriggeredAbility
	{
		public StonehornDignitaryAbility0(GameState state)
		{
			super(state, "When Stonehorn Dignitary enters the battlefield, target opponent skips his or her next combat phase.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			SetGenerator combat = CombatPhaseOf.instance(target);

			SimpleEventPattern nextCombatPhase = new SimpleEventPattern(EventType.BEGIN_PHASE);
			nextCombatPhase.put(EventType.Parameter.PHASE, combat);

			EventReplacementEffect skip = new EventReplacementEffect(state.game, "That player skips his or her next combat phase", nextCombatPhase);
			// skip.addEffect(nothing)

			EventFactory factory = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "That player skips his or her next combat phase.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(replacementEffectPart(skip)));
			factory.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
			factory.parameters.put(EventType.Parameter.USES, numberGenerator(1));
			this.addEffect(factory);
		}
	}

	public StonehornDignitary(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// When Stonehorn Dignitary enters the battlefield, target opponent
		// skips his or her next combat phase.
		this.addAbility(new StonehornDignitaryAbility0(state));
	}
}
