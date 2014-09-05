package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Halimar Excavator")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD, SubType.ALLY})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class HalimarExcavator extends Card
{
	public static final class HalimarExcavatorAbility0 extends EventTriggeredAbility
	{
		public HalimarExcavatorAbility0(GameState state)
		{
			super(state, "Whenever Halimar Excavator or another Ally enters the battlefield under your control, target player puts the top X cards of his or her library into his or her graveyard, where X is the number of Allies you control.");
			this.addPattern(allyTrigger());

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			SetGenerator amount = Count.instance(ALLIES_YOU_CONTROL);
			this.addEffect(millCards(target, amount, "Target player puts the top X cards of his or her library into his or her graveyard, where X is the number of Allies you control."));
		}
	}

	public HalimarExcavator(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Whenever Halimar Excavator or another Ally enters the battlefield
		// under your control, target player puts the top X cards of his or her
		// library into his or her graveyard, where X is the number of Allies
		// you control.
		this.addAbility(new HalimarExcavatorAbility0(state));
	}
}
