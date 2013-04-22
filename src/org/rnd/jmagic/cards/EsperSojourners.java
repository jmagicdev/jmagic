package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

import static org.rnd.jmagic.Convenience.*;

@Name("Esper Sojourners")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.VEDALKEN, SubType.WIZARD})
@ManaCost("WUB")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.BLACK})
public final class EsperSojourners extends Card
{
	public static final class EsperSojournerTrigger extends org.rnd.jmagic.abilityTemplates.SojournerTrigger
	{
		public EsperSojournerTrigger(GameState state)
		{
			super(state, "When you cycle Esper Sojourners or it's put into a graveyard from the battlefield, you may tap or untap target permanent.");

			Target target = this.addTarget(Permanents.instance(), "target permanent");

			this.addEffect(youMay(tapOrUntap(targetedBy(target), "target permanent"), "You may tap or untap target permanent."));
		}
	}

	public EsperSojourners(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		this.addAbility(new EsperSojournerTrigger(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(2)(U)"));
	}
}
