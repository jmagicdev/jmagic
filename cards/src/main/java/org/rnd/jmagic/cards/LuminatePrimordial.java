package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Luminate Primordial")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("5WW")
@ColorIdentity({Color.WHITE})
public final class LuminatePrimordial extends Card
{
	public static final class LuminatePrimordialAbility1 extends EventTriggeredAbility
	{
		public LuminatePrimordialAbility1(GameState state)
		{
			super(state, "When Luminate Primordial enters the battlefield, for each opponent, exile up to one target creature that player controls and that player gains life equal to its power.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator legalTargets = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			Target t = new SylvanPrimordial.SylvanTarget(legalTargets, "up to one target creature each opponent controls");
			this.addTarget(t);

			DynamicEvaluation eachTarget = DynamicEvaluation.instance();

			EventFactory exile = exile(eachTarget, "Exile up to one target creature that player controls");
			EventFactory gainLife = gainLife(ControllerOf.instance(eachTarget), PowerOf.instance(eachTarget), "and that player gains life equal to its power");

			EventFactory effect = new EventFactory(FOR_EACH, "For each opponent, exile up to one target creature that player controls and that player gains life equal to its power.");
			effect.parameters.put(EventType.Parameter.OBJECT, targetedBy(t));
			effect.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachTarget));
			effect.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sequence(exile, gainLife)));
			this.addEffect(effect);
		}
	}

	public LuminatePrimordial(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(7);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// When Luminate Primordial enters the battlefield, for each opponent,
		// exile up to one target creature that player controls and that player
		// gains life equal to its power.
		this.addAbility(new LuminatePrimordialAbility1(state));
	}
}
