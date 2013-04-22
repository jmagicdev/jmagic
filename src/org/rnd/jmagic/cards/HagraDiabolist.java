package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hagra Diabolist")
@Types({Type.CREATURE})
@SubTypes({SubType.OGRE, SubType.SHAMAN, SubType.ALLY})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class HagraDiabolist extends Card
{
	public static final class AllyLife extends EventTriggeredAbility
	{
		public AllyLife(GameState state)
		{
			super(state, "Whenever Hagra Diabolist or another Ally enters the battlefield under your control, you may have target player lose life equal to the number of Allies you control.");

			this.addPattern(allyTrigger());

			Target target = this.addTarget(Players.instance(), "target player");
			EventFactory lifeFactory = loseLife(targetedBy(target), Count.instance(ALLIES_YOU_CONTROL), "Target player loses life equal to the number of Allies you control");
			this.addEffect(youMay(lifeFactory, "You may have target player lose life equal to the number of Allies you control."));
		}
	}

	public HagraDiabolist(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		this.addAbility(new AllyLife(state));
	}
}
