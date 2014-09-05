package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bloodgift Demon")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class BloodgiftDemon extends Card
{
	public static final class BloodgiftDemonAbility1 extends EventTriggeredAbility
	{
		public BloodgiftDemonAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, target player draws a card and loses 1 life.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			EventFactory draw = drawCards(target, 1, "Target player draws a card");
			EventFactory lose = loseLife(target, 1, "and loses 1 life.");
			this.addEffect(sequence(draw, lose));
		}
	}

	public BloodgiftDemon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your upkeep, target player draws a card and loses
		// 1 life.
		this.addAbility(new BloodgiftDemonAbility1(state));
	}
}
