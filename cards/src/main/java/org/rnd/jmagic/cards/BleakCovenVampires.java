package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bleak Coven Vampires")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.WARRIOR})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class BleakCovenVampires extends Card
{
	public static final class BleakCovenVampiresAbility0 extends EventTriggeredAbility
	{
		public BleakCovenVampiresAbility0(GameState state)
		{
			super(state, "Metalcraft \u2014 When Bleak Coven Vampires enters the battlefield, if you control three or more artifacts, target player loses 4 life and you gain 4 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = Metalcraft.instance();
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			this.addEffect(loseLife(target, 4, "Target player loses 4 life"));
			this.addEffect(gainLife(You.instance(), 4, "and you gain 4 life."));
		}
	}

	public BleakCovenVampires(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Metalcraft \u2014 When Bleak Coven Vampires enters the battlefield,
		// if you control three or more artifacts, target player loses 4 life
		// and you gain 4 life.
		this.addAbility(new BleakCovenVampiresAbility0(state));
	}
}
