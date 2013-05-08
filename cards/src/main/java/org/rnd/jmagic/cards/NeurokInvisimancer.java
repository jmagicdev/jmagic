package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Neurok Invisimancer")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class NeurokInvisimancer extends Card
{
	public static final class NeurokInvisimancerAbility0 extends StaticAbility
	{
		public NeurokInvisimancerAbility0(GameState state)
		{
			super(state, "Neurok Invisimancer is unblockable.");
			this.addEffectPart(unblockable(This.instance()));
		}
	}

	public static final class NeurokInvisimancerAbility1 extends EventTriggeredAbility
	{
		public NeurokInvisimancerAbility1(GameState state)
		{
			super(state, "When Neurok Invisimancer enters the battlefield, target creature is unblockable this turn.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(createFloatingEffect("Target creature is unblockable this turn.", unblockable(target)));
		}
	}

	public NeurokInvisimancer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Neurok Invisimancer is unblockable.
		this.addAbility(new NeurokInvisimancerAbility0(state));

		// When Neurok Invisimancer enters the battlefield, target creature is
		// unblockable this turn.
		this.addAbility(new NeurokInvisimancerAbility1(state));
	}
}
