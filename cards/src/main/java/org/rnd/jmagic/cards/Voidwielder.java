package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Voidwielder")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class Voidwielder extends Card
{
	public static final class VoidwielderAbility0 extends EventTriggeredAbility
	{
		public VoidwielderAbility0(GameState state)
		{
			super(state, "When Voidwielder enters the battlefield, you may return target creature to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(youMay(bounce(target, "Return target creature to its owner's hand"), "You may return target creature to its owner's hand."));
		}
	}

	public Voidwielder(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// When Voidwielder enters the battlefield, you may return target
		// creature to its owner's hand.
		this.addAbility(new VoidwielderAbility0(state));
	}
}
