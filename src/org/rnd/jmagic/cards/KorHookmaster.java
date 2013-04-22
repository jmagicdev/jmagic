package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Kor Hookmaster")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.SOLDIER})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class KorHookmaster extends Card
{
	public static final class Hook extends EventTriggeredAbility
	{
		public Hook(GameState state)
		{
			super(state, "When Kor Hookmaster enters the battlefield, tap target creature an opponent controls. That creature doesn't untap during its controller's next untap step.");
			this.addPattern(whenThisEntersTheBattlefield());
			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target creature an opponent controls");
			this.addEffect(tap(targetedBy(target), "Tap target creature an opponent controls."));

			EventPattern untapping = new UntapDuringControllersUntapStep(targetedBy(target));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(untapping));

			SetGenerator thatPlayersUntap = UntapStepOf.instance(ControllerOf.instance(targetedBy(target)));
			SetGenerator untapStepOver = Intersect.instance(PreviousStep.instance(), thatPlayersUntap);

			this.addEffect(createFloatingEffect(untapStepOver, "That creature doesn't untap during its controller's next untap step.", part));
		}
	}

	public KorHookmaster(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Kor Hookmaster enters the battlefield, tap target creature an
		// opponent controls. That creature doesn't untap during its
		// controller's next untap step.
		this.addAbility(new Hook(state));
	}
}
