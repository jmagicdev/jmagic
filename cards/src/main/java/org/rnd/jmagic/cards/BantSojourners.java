package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Bant Sojourners")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("1GWU")
@ColorIdentity({Color.WHITE, Color.BLUE, Color.GREEN})
public final class BantSojourners extends Card
{
	public static final class BantSojournerTrigger extends org.rnd.jmagic.abilityTemplates.SojournerTrigger
	{
		public BantSojournerTrigger(GameState state)
		{
			super(state, "When you cycle Bant Sojourners or it's put into a graveyard from the battlefield, you may put a 1/1 white Soldier creature token onto the battlefield.");

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Soldier creature token onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.SOLDIER);
			this.addEffect(youMay(token.getEventFactory(), "You may put a 1/1 white Soldier creature token onto the battlefield."));
		}
	}

	public BantSojourners(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		this.addAbility(new BantSojournerTrigger(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(2)(W)"));
	}
}
