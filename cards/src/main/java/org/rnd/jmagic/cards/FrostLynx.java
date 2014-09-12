package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Frost Lynx")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.ELEMENTAL})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class FrostLynx extends Card
{
	public static final class FrostLynxAbility0 extends EventTriggeredAbility
	{
		public FrostLynxAbility0(GameState state)
		{
			super(state, "When Frost Lynx enters the battlefield, tap target creature an opponent controls. That creature doesn't untap during its controller's next untap step.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator opponentsCreature = Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(OpponentsOf.instance(You.instance())));
			SetGenerator target = targetedBy(this.addTarget(opponentsCreature, "target creature an opponent controls"));

			EventFactory factory = new EventFactory(EventType.TAP_HARD, "Tap target creature an opponent controls. It doesn't untap during its controller's next untap step.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(factory);
		}
	}

	public FrostLynx(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Frost Lynx enters the battlefield, tap target creature an
		// opponent controls. That creature doesn't untap during its
		// controller's next untap step.
		this.addAbility(new FrostLynxAbility0(state));
	}
}
