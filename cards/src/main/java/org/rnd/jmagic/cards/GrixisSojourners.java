package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import static org.rnd.jmagic.Convenience.*;

@Name("Grixis Sojourners")
@Types({Type.CREATURE})
@SubTypes({SubType.OGRE, SubType.ZOMBIE})
@ManaCost("1UBR")
@ColorIdentity({Color.BLUE, Color.BLACK, Color.RED})
public final class GrixisSojourners extends Card
{
	public static final class GrixisSojournerTrigger extends org.rnd.jmagic.abilityTemplates.SojournerTrigger
	{
		public GrixisSojournerTrigger(GameState state)
		{
			super(state, "When you cycle Grixis Sojourners or it's put into a graveyard from the battlefield, you may exile target card from a graveyard.");

			Target target = this.addTarget(InZone.instance(GraveyardOf.instance(Players.instance())), "target card from a graveyard");

			this.addEffect(youMay(exile(targetedBy(target), "Exile target card from a graveyard."), "You may exile target card from a graveyard."));
		}
	}

	public GrixisSojourners(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		this.addAbility(new GrixisSojournerTrigger(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(2)(B)"));
	}
}
