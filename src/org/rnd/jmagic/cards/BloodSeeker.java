package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Blood Seeker")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.SHAMAN})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BloodSeeker extends Card
{
	public static final class AntiWarden extends EventTriggeredAbility
	{
		public AntiWarden(GameState state)
		{
			super(state, "Whenever a creature enters the battlefield under an opponent's control, you may have that player lose 1 life.");

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), HasType.instance(Type.CREATURE), OpponentsOf.instance(You.instance()), false));

			this.addEffect(youMay(loseLife(ControllerOf.instance(TriggerZoneChange.instance(This.instance())), 1, "That player loses 1 life."), "You may have that player lose 1 life."));
		}
	}

	public BloodSeeker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new AntiWarden(state));
	}
}
