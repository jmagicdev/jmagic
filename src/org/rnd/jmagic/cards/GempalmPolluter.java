package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gempalm Polluter")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("5B")
@Printings({@Printings.Printed(ex = Expansion.LEGIONS, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class GempalmPolluter extends Card
{
	public static final class CycleTrigger extends EventTriggeredAbility
	{
		public CycleTrigger(GameState state)
		{
			super(state, "When you cycle Gempalm Polluter, you may have target player lose life equal to the number of Zombies on the battlefield.");

			this.canTrigger = NonEmpty.instance();
			this.addPattern(whenYouCycleThis());

			Target target = this.addTarget(Players.instance(), "target player");

			SetGenerator zombies = Intersect.instance(HasSubType.instance(SubType.ZOMBIE), Permanents.instance());
			EventFactory loseLife = loseLife(targetedBy(target), Count.instance(zombies), "Target player loses life equal to the number of Zombies on the battlefield");
			this.addEffect(youMay(loseLife, "You may have target player lose life equal to the number of Zombies on the battlefield."));
		}
	}

	public GempalmPolluter(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(B)(B)"));

		// When you cycle Gempalm Polluter, you may have target player lose life
		// equal to the number of Zombies on the battlefield.
		this.addAbility(new CycleTrigger(state));
	}
}
