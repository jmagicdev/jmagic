package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import static org.rnd.jmagic.Convenience.*;

@Name("Jund Sojourners")
@Types({Type.CREATURE})
@SubTypes({SubType.VIASHINO, SubType.SHAMAN})
@ManaCost("BRG")
@ColorIdentity({Color.BLACK, Color.RED, Color.GREEN})
public final class JundSojourners extends Card
{
	public static final class JundSojournerTrigger extends org.rnd.jmagic.abilityTemplates.SojournerTrigger
	{
		public JundSojournerTrigger(GameState state)
		{
			super(state, "When you cycle Jund Sojourners or it's put into a graveyard from the battlefield, you may have it deal 1 damage to target creature or player.");

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			this.addEffect(youMay(permanentDealDamage(1, targetedBy(target), "Deal 1 damage to target creature or player."), "You may have it deal 1 damage to target creature or player."));
		}
	}

	public JundSojourners(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		this.addAbility(new JundSojournerTrigger(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(2)(R)"));
	}
}
