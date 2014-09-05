package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Azorius Justiciar")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class AzoriusJusticiar extends Card
{
	public static final class AzoriusJusticiarAbility0 extends EventTriggeredAbility
	{
		public AzoriusJusticiarAbility0(GameState state)
		{
			super(state, "When Azorius Justiciar enters the battlefield, detain up to two target creatures your opponents control.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "up to two target creatures your opponents control");
			target.setNumber(0, 2);

			this.addEffect(detain(targetedBy(target), "Detain up to two target creatures your opponents control."));

			state.ensureTracker(new DetainGenerator.Tracker());
		}
	}

	public AzoriusJusticiar(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Azorius Justiciar enters the battlefield, detain up to two
		// target creatures your opponents control. (Until your next turn, those
		// creatures can't attack or block and their activated abilities can't
		// be activated.)
		this.addAbility(new AzoriusJusticiarAbility0(state));
	}
}
