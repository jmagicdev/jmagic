package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Azorius Arrester")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class AzoriusArrester extends Card
{
	public static final class AzoriusArresterAbility0 extends EventTriggeredAbility
	{
		public AzoriusArresterAbility0(GameState state)
		{
			super(state, "When Azorius Arrester enters the battlefield, detain target creature an opponent controls.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target creature an opponent controls"));

			this.addEffect(detain(target, "Detain target creature an opponent controls."));

			state.ensureTracker(new DetainGenerator.Tracker());
		}
	}

	public AzoriusArrester(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Azorius Arrester enters the battlefield, detain target creature
		// an opponent controls. (Until your next turn, that creature can't
		// attack or block and its activated abilities can't be activated.)
		this.addAbility(new AzoriusArresterAbility0(state));
	}
}
