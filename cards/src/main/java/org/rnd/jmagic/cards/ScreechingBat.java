package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Screeching Bat")
@Types({Type.CREATURE})
@SubTypes({SubType.BAT})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
@BackFace(StalkingVampire.class)
public final class ScreechingBat extends Card
{
	public static final class ScreechingBatAbility1 extends EventTriggeredAbility
	{
		public ScreechingBatAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may pay (2)(B)(B). If you do, transform Screeching Bat.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(ifThen(youMayPay("(2)(B)(B)"), transformThis("Screeching Bat"), "You may pay (2)(B)(B). If you do, transform Screeching Bat."));
		}
	}

	public ScreechingBat(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your upkeep, you may pay (2)(B)(B). If you do,
		// transform Screeching Bat.
		this.addAbility(new ScreechingBatAbility1(state));
	}
}
