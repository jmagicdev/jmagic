package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Tidebinder Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.MERFOLK})
@ManaCost("UU")
@ColorIdentity({Color.BLUE})
public final class TidebinderMage extends Card
{
	public static final class TidebinderMageAbility0 extends EventTriggeredAbility
	{
		public TidebinderMageAbility0(GameState state)
		{
			super(state, "When Tidebinder Mage enters the battlefield, tap target red or green creature an opponent controls. That creature doesn't untap during its controller's untap step for as long as you control Tidebinder Mage.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator redOrGreen = HasColor.instance(Color.RED, Color.GREEN);
			SetGenerator creatures = HasType.instance(Type.CREATURE);
			SetGenerator opponentControls = ControlledBy.instance(OpponentsOf.instance(You.instance()));
			SetGenerator legal = Intersect.instance(redOrGreen, creatures, opponentControls);
			SetGenerator target = targetedBy(this.addTarget(legal, "target red or green creature an opponent controls"));
			this.addEffect(tap(target, "Tap target red or green creature an opponent controls."));

			EventPattern untap = new UntapDuringControllersUntapStep(target);

			SetGenerator youDontControlThis = Not.instance(Intersect.instance(ABILITY_SOURCE_OF_THIS, ControlledBy.instance(You.instance())));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(untap));
			this.addEffect(createFloatingEffect(youDontControlThis, "That creature doesn't untap during its controller's untap step for as long as you control Tidebinder Mage.", part));
		}
	}

	public TidebinderMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Tidebinder Mage enters the battlefield, tap target red or green
		// creature an opponent controls. That creature doesn't untap during its
		// controller's untap step for as long as you control Tidebinder Mage.
		this.addAbility(new TidebinderMageAbility0(state));
	}
}
