package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Deserter's Quarters")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class DesertersQuarters extends Card
{
	public static final class DesertersQuartersAbility0 extends StaticAbility
	{
		public DesertersQuartersAbility0(GameState state)
		{
			super(state, "You may choose not to untap Deserter's Quarters during your untap step.");

			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "You may choose not to untap Deserter's Quarters during your untap step.", new UntapDuringControllersUntapStep(This.instance()));
			replacement.makeOptional(You.instance());

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public static final class DesertersQuartersAbility1 extends ActivatedAbility
	{
		public DesertersQuartersAbility1(GameState state)
		{
			super(state, "(6), (T): Tap target creature. It doesn't untap during its controller's untap step for as long as Deserter's Quarters remains tapped.");
			this.setManaCost(new ManaPool("(6)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(tap(target, "Tap target creature."));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(new UntapDuringControllersUntapStep(target)));

			this.addEffect(createFloatingEffect(Intersect.instance(ABILITY_SOURCE_OF_THIS, Untapped.instance()), "It doesn't untap during its controller's untap step for as long as Deserter's Quarters remains tapped.", part));

		}
	}

	public DesertersQuarters(GameState state)
	{
		super(state);

		// You may choose not to untap Deserter's Quarters during your untap
		// step.
		this.addAbility(new DesertersQuartersAbility0(state));

		// (6), (T): Tap target creature. It doesn't untap during its
		// controller's untap step for as long as Deserter's Quarters remains
		// tapped.
		this.addAbility(new DesertersQuartersAbility1(state));
	}
}
