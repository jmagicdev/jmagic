package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Slayer of the Wicked")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class SlayeroftheWicked extends Card
{
	public static final class SlayeroftheWickedAbility0 extends EventTriggeredAbility
	{
		public SlayeroftheWickedAbility0(GameState state)
		{
			super(state, "When Slayer of the Wicked enters the battlefield, you may destroy target Vampire, Werewolf, or Zombie.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.VAMPIRE, SubType.WEREWOLF, SubType.ZOMBIE)), "target Vampire, Werewolf, or Zombie"));
			this.addEffect(youMay(destroy(target, "Destroy target Vampire, Werewolf, or Zombie.")));
		}
	}

	public SlayeroftheWicked(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// When Slayer of the Wicked enters the battlefield, you may destroy
		// target Vampire, Werewolf, or Zombie.
		this.addAbility(new SlayeroftheWickedAbility0(state));
	}
}
