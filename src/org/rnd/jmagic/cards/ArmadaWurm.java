package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Armada Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("2GGWW")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class ArmadaWurm extends Card
{
	public static final class ArmadaWurmAbility1 extends EventTriggeredAbility
	{
		public ArmadaWurmAbility1(GameState state)
		{
			super(state, "When Armada Wurm enters the battlefield, put a 5/5 green Wurm creature token with trample onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory factory = new CreateTokensFactory(1, 5, 5, "Put a 5/5 green Wurm creature token with trample onto the battlefield.");
			factory.setColors(Color.GREEN);
			factory.setSubTypes(SubType.WURM);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Trample.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public ArmadaWurm(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// When Armada Wurm enters the battlefield, put a 5/5 green Wurm
		// creature token with trample onto the battlefield.
		this.addAbility(new ArmadaWurmAbility1(state));
	}
}
